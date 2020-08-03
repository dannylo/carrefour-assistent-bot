package com.carrefour.challange.chatbot.chatbotassistent.amqp.recievers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.carrefour.challange.chatbot.chatbotassistent.amqp.messages.Message;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.enums.AttendanceStatus;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;
import com.carrefour.challange.chatbot.chatbotassistent.telegram.AssistenteCarrefourBot;
import com.carrefour.challange.chatbot.chatbotassistent.util.MessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RabbitListener(queues = {"${queue.attendance.buy.toApi}"})
@Component
public class AttendanceBuyQueueReciever {	

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private AssistenteCarrefourBot bot;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@RabbitHandler
	public void listen(@Payload String jsonMessage) {
		Message message;
		System.out.println(jsonMessage);
		try {
			message = mapper.readValue(jsonMessage, Message.class);
			message = this.messageUtil.verifyMessageAttached(message);
			message =this.messageUtil.verifyMessageFinished(message);
			try {
				bot.sendMessage(message.getChatIdTelegram(), message.getMessageDescription());
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
}
