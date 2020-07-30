package com.carrefour.challange.chatbot.chatbotassistent.telegram;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.carrefour.challange.chatbot.chatbotassistent.dialogflow.DialogFlowAgent;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.google.cloud.dialogflow.v2.QueryResult;


@Component
public class AssistenteCarrefourBot extends TelegramLongPollingBot {
	
	@Autowired
	private TelegramProperties properties;
	
	@Autowired
	private DialogFlowAgent dialogFlowAgent;

	@Override
	public void onUpdateReceived(Update update) {
		String text = update.getMessage().getText();
		System.out.println(text);
		
		try {
			QueryResult result = dialogFlowAgent.sendMessage(text);
			String response = result.getFulfillmentText();
			
			Attendance attendance = new Attendance();
			
//			StringBuilder welcomePhrase = new StringBuilder("Olá, bem vindo ao <b>Assistente Carrefour</b>, seu protocolo de atendimento é ");
//			welcomePhrase.append("<b>").append(attendance.getProtocol()).append("</b> em que posso ajudá-lo?");
//			
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
