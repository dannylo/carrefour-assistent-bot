package com.carrefour.challange.chatbot.chatbotassistent.amqp.recievers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.carrefour.challange.chatbot.chatbotassistent.amqp.messages.Message;
import com.carrefour.challange.chatbot.chatbotassistent.telegram.AssistenteCarrefourBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RabbitListener(queues = {"${queue.attendance.buy.toApi}"})
@Component
public class AttendanceBuyQueueReciever {	

	private ObjectMapper mapper = new ObjectMapper();
	
	private final String ATTACHED = "ATTACHED";
	
	private final String FINISHED = "FINISHED";
	
	@Autowired
	private AssistenteCarrefourBot bot;

	private void verifyAttendanceAttached(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("Olá, me chamo ").append(message.getAttendant())
			.append(" estou analisando os dados que você nos passou e já resolvo seu problema.");
		if(message.getMessageDescription().equals(ATTACHED)) {
			message.setMessageDescription(builder.toString());
		}
	}
	
	private void verifyFinishedAttendance(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("O nosso atendente finalizou sua solicitação. Esperamos"
				+ "que o seu problema tenha sido solucionado e não volte a correr. Para "
				+ "melhorarmos cada vez mais nossos serviços você poderia avaliar esse atendimento?"
				+ "Se sim, em nível de satisfação, nos dê uma nota de 0 a 10.");
		if(message.getMessageDescription().equals(FINISHED)) {
			message.setMessageDescription(builder.toString());
		}
		
	}
	
	@RabbitHandler
	public void listen(@Payload String jsonMessage) {
		Message message;
		System.out.println(jsonMessage);
		try {
			message = mapper.readValue(jsonMessage, Message.class);
			verifyAttendanceAttached(message);
			verifyFinishedAttendance(message);
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
