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
import com.carrefour.challange.chatbot.chatbotassistent.enums.TypeProblem;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;
import com.google.cloud.dialogflow.v2.QueryResult;

import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

@Component
public class AssistenteCarrefourBot extends TelegramLongPollingBot {
	
	private final String WELCOME_INTENT = "welcome_intent";
	private final String FEEDBACK_INTENT = "feedback_intent";
	private final String FEEDBACK_OPINIONS_INTENT = "feedback_opinions_intent";
	
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

		String response="";
		
		try {
			QueryResult result = dialogFlowAgent.sendMessage(text);
			response = result.getFulfillmentText();
			if(ObjectUtils.isEmpty(attendance) && 
					result
					.getIntent()
					.getDisplayName()
					.equals(WELCOME_INTENT)){
				
				attendance = new Attendance();
				response = new StringBuilder(result.getFulfillmentText())
								.append("<b>")
								.append(attendance.getProtocol())
								.append("</b>")
								.toString();
			} if (result
					.getIntent()
					.getDisplayName()
					.equals(FEEDBACK_OPINIONS_INTENT)) {
				
				attendance.setTypeProblem(TypeProblem.FEEDBACK);
				attendance.setGeneralDescription(text);
				System.out.println("Attendence type problem: " + attendance.getTypeProblem());
				System.out.println("Attendence description: " + attendance.getGeneralDescription());
				response = result.getFulfillmentText();
				
				attendanceService.save(attendance);
				
			}
			
			
			SendMessage message = new SendMessage() 
					 .setChatId(update.getMessage().getChatId())
					 .setText(response).enableHtml(true);
			
			execute(message);
			
		} catch (IOException | TelegramApiException e) {
			e.printStackTrace();
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
