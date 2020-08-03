package com.carrefour.challange.chatbot.chatbotassistent.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrefour.challange.chatbot.chatbotassistent.amqp.messages.Message;
import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.enums.AttendanceStatus;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;

@Component
public class MessageUtil {
	
	private final String ATTACHED = "ATTACHED";
	private final String FINISHED = "FINISHED";
	
	private final String FINISHED_MESSAGE = "O nosso atendente finalizou sua solicita��o. Esperamos"
			+ " que o seu problema tenha sido solucionado e n�o volte a ocorrer. Para "
			+ "melhorarmos cada vez mais nossos servi�os voc� poderia avaliar esse atendimento?"
			+ " Se sim, em n�vel de satisfa��o, nos d� uma nota de 0 a 10 por favor.";
	
	@Autowired
	private AttendanceService service;

	public Message verifyMessageAttached(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("Ol�, me chamo ").append(message.getAttendant())
			.append(" estou analisando os dados que voc� nos passou e j� resolvo seu problema.");
		if(message.getMessageDescription().equals(ATTACHED)) {
			Attendance attendance = service.getByProtocol(message.getProtocolAttendance());
			attendance.setStatus(AttendanceStatus.ATTACHED);
			service.save(attendance);
			message.setMessageDescription(builder.toString());
		}
		return message;
	}
	
	public Message verifyMessageFinished(Message message) {
		StringBuilder builder = new StringBuilder();
		
		if(message.getMessageDescription().equals(FINISHED)) {
			Attendance attendance = service.getByProtocol(message.getProtocolAttendance());
			attendance.setStatus(AttendanceStatus.FINISHED);
			service.save(attendance);
			message.setMessageDescription(FINISHED_MESSAGE);
		}
		
		return message;
		
	}
	
}
