package com.carrefour.challange.chatbot.chatbotassistent.telegram;

import java.io.IOException;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.carrefour.challange.chatbot.chatbotassistent.dialogflow.DialogFlowAgent;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.enums.CategoryRequest;
import com.carrefour.challange.chatbot.chatbotassistent.enums.TypeProblem;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;
import com.carrefour.challange.chatbot.chatbotassistent.strategies.CreditCardProblemStrategy;
import com.carrefour.challange.chatbot.chatbotassistent.strategies.RequestProblemStrategy;
import com.google.cloud.dialogflow.v2.QueryResult;

import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

@Component
public class AssistenteCarrefourBot extends TelegramLongPollingBot {

	private final String WELCOME_INTENT = "welcome_intent";
	private final String FEEDBACK_OPINIONS_INTENT = "feedback_opinions_intent";
	private final String REQUEST_INTENT = "request_intent";
	private final String REQUEST_DATA_INTENT = "request_data_intent";
	private final String CREDIT_CARD_INTENT = "credit_card_intent";
	private final String CREDIT_CARD_DATA_INTENT = "credit_card_data_intent";

	private Attendance attendance;

	@Autowired
	private TelegramProperties properties;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private DialogFlowAgent dialogFlowAgent;

	@Override
	public void onUpdateReceived(Update update) {
		String text = update.getMessage().getText();
		System.out.println(text);
		String response = "";

		try {
			QueryResult result = dialogFlowAgent.sendMessage(text);
			response = result.getFulfillmentText();

			switch (result.getIntent().getDisplayName()) {
			case WELCOME_INTENT:
				attendance = new Attendance();
				response = new StringBuilder(result.getFulfillmentText()).append("<b>").append(attendance.getProtocol())
						.append("</b>").append(" em que posso ajudá-lo?").toString();
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
				break;
			case CREDIT_CARD_INTENT:
				attendance.setTypeProblem(TypeProblem.PROBLEM);
				attendance.setCategory(CategoryRequest.CREDIT_CARD);
				attendance.setGeneralDescription(text);
				break;
			case CREDIT_CARD_DATA_INTENT:
				attendance.registerData(text, new CreditCardProblemStrategy());
				attendanceService.save(attendance);
				break;

			default:
				break;
			} 

			this.sendMessage(update.getMessage().getChatId(), response);

		} catch (IOException | TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Long chatId, String text) throws TelegramApiException {
		SendMessage message = new SendMessage().setChatId(chatId).setText(text).enableHtml(true);
		execute(message);
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
