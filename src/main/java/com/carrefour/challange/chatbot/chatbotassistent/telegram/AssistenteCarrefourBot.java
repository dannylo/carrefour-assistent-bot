package com.carrefour.challange.chatbot.chatbotassistent.telegram;

import java.io.IOException;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.carrefour.challange.chatbot.chatbotassistent.amqp.messages.Message;
import com.carrefour.challange.chatbot.chatbotassistent.amqp.senders.AttendanceBuyQueueSender;
import com.carrefour.challange.chatbot.chatbotassistent.amqp.senders.AttendanceCardQueueSender;
import com.carrefour.challange.chatbot.chatbotassistent.dialogflow.DialogFlowAgent;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Evaluation;
import com.carrefour.challange.chatbot.chatbotassistent.enums.AttendanceStatus;
import com.carrefour.challange.chatbot.chatbotassistent.enums.CategoryRequest;
import com.carrefour.challange.chatbot.chatbotassistent.enums.TypeProblem;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;
import com.carrefour.challange.chatbot.chatbotassistent.strategies.CreditCardProblemStrategy;
import com.carrefour.challange.chatbot.chatbotassistent.strategies.RequestProblemStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.dialogflow.v2.QueryResult;

import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

/**
 * Componente responsável por controlar as interações com o chatbot, 
 * Deve intermediar o diálogo entre usuário, inteligência articial e sistema
 * do atendente para possibilitar a otimização de comunicação de forma transparente.
 * 
 * */
@Component
public class AssistenteCarrefourBot extends TelegramLongPollingBot {

	private final String WELCOME_INTENT = "welcome_intent";
	private final String FEEDBACK_OPINIONS_INTENT = "feedback_opinions_intent";
	private final String REQUEST_INTENT = "request_intent";
	private final String REQUEST_DATA_INTENT = "request_intent_data";
	private final String CREDIT_CARD_INTENT = "credit_card_intent";
	private final String CREDIT_CARD_DATA_INTENT = "credit_card_data_intent";

	private boolean endConversationWithDialogFlow = false;

	private Attendance attendance;

	@Autowired
	private TelegramProperties properties;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private DialogFlowAgent dialogFlowAgent;

	@Autowired
	private AttendanceBuyQueueSender queueBuySender;

	@Autowired
	private AttendanceCardQueueSender queueCardSender;

	@Override
	public void onUpdateReceived(Update update) {
		String text = update.getMessage().getText();
		String response = "";
		try {
			if (!endConversationWithDialogFlow) {
				response = processDialogFlowIntents(update, text);
			} else {

				attendance = attendanceService.getByProtocol(this.attendance.getProtocol());
				if(attendance != null && 
						attendance.getStatus() == AttendanceStatus.FINISHED) {
					int valueSatisfaction = Integer.parseInt(text.trim());
					Evaluation evaluation = new Evaluation();
					evaluation.setProtocolAttendance(attendance.getProtocol());
					evaluation.setSatisfactionValue(valueSatisfaction);
					attendance.setEvaluation(evaluation);
					attendanceService.save(attendance);
					sendMessage(update.getMessage().getChatId(), "Agradecemos sua avaliação. Tenha um ótimo dia!");
					this.endConversationWithDialogFlow = false;
				}
				System.out.println(text);
				this.sendMessageToQueues(this.createMessageWithText(attendance, update.getMessage().getChatId(), text));
			}
			
			sendMessage(update.getMessage().getChatId(), response);

		} catch (IOException | TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessageToQueues(Message message) throws JsonProcessingException {
		if (attendance.getCategory() == CategoryRequest.BUY)
			queueBuySender.send(message);
		else
			queueCardSender.send(message);
	}

	private String processDialogFlowIntents(Update update, String text) throws IOException, JsonProcessingException {
		String response;
		QueryResult result = dialogFlowAgent.sendMessage(text);
		response = result.getFulfillmentText();
		
		switch (result.getIntent().getDisplayName()) {
			case WELCOME_INTENT:
				attendance = new Attendance();
				response = new StringBuilder(result.getFulfillmentText()).append("<b>")
						.append(attendance.getProtocol()).append("</b>").append(" em que posso ajudá-lo?")
						.toString();
				break;
			case FEEDBACK_OPINIONS_INTENT:
				attendance.setTypeProblem(TypeProblem.FEEDBACK);
				attendance.setGeneralDescription(text);
				response = result.getFulfillmentText();
				attendanceService.save(attendance);
				break;
			case REQUEST_INTENT:
				attendance.setTypeProblem(TypeProblem.PROBLEM);
				attendance.setCategory(CategoryRequest.BUY);
				attendance.setGeneralDescription(text);
				break;
			case REQUEST_DATA_INTENT:
				attendance.registerData(text, new RequestProblemStrategy());
				attendanceService.save(attendance);
				this.queueBuySender.send(this.createMessage(attendance, update.getMessage().getChatId()));
				endConversationWithDialogFlow = true;
				break;
			case CREDIT_CARD_INTENT:
				attendance.setTypeProblem(TypeProblem.PROBLEM);
				attendance.setCategory(CategoryRequest.CREDIT_CARD);
				attendance.setGeneralDescription(text);
				break;
			case CREDIT_CARD_DATA_INTENT:
				attendance.registerData(text, new CreditCardProblemStrategy());
				attendanceService.save(attendance);
				this.queueCardSender.send(this.createMessage(attendance, update.getMessage().getChatId()));
				endConversationWithDialogFlow = true;
				break;

			default:
				break;
		}
		return response;
	}

	private Message createMessage(Attendance attendance, Long chatId) {
		return new Message(attendance.getProtocol(), attendance.getGeneralDescription(), chatId, attendance.getDatas());
	}

	private Message createMessageWithText(Attendance attendance, Long chatId, String text) {
		Message message = new Message(attendance.getProtocol(), attendance.getGeneralDescription(), chatId,
				attendance.getDatas());
		message.setMessageDescription(text);
		return message;
	}

	public void sendMessage(Long chatId, String text) throws TelegramApiException {
		if (!text.isBlank()) {
			SendMessage message = new SendMessage().setChatId(chatId).setText(text).enableHtml(true);
			execute(message);
		}
	}

	@Override
	public String getBotUsername() {
		return properties.getBotName();
	}

	@Override
	public String getBotToken() {
		return properties.getToken();
	}

}
