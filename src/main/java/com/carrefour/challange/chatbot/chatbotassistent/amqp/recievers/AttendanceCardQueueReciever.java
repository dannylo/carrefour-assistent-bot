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

@RabbitListener(queues = {"${queue.attendance.card.toApi}"})
@Component
public class AttendanceCardQueueReciever {
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private AssistenteCarrefourBot bot;
	
	private final String ATTACHED = "ATTACHED";
	
	private final String FINISHED = "FINISHED";

	private void verifyAttendanceAlocated(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("Ol�, me chamo ").append(message.getAttendant())
				.append(" estou analisando os dados que voc� nos passou e j� resolvo seu problema.");
		if (message.getMessageDescription().equals(ATTACHED)) {
			message.setMessageDescription(builder.toString());
		}

	}
	
	private void verifyFinishedAttendance(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("O nosso atendente finalizou sua solicita��o. Esperamos"
				+ "que o seu problema tenha sido solucionado e n�o volte a correr. Para "
				+ "melhorarmos cada vez mais nossos servi�os voc� poderia avaliar esse atendimento?"
				+ "Se sim, em n�vel de satisfa��o, nos d� uma nota de 0 a 10.");
		if(message.getMessageDescription().equals(FINISHED)) {
			message.setMessageDescription(builder.toString());
		}
		
	}

	@RabbitHandler
	public void listen(@Payload String jsonMessage) {
		Message message;
		try {
			message = mapper.readValue(jsonMessage, Message.class);
			verifyAttendanceAlocated(message);
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
