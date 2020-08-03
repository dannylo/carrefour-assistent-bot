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
	
	private final String FINISHED_MESSAGE = "O nosso atendente finalizou sua solicitação. Esperamos"
			+ " que o seu problema tenha sido solucionado e não volte a ocorrer. Para "
			+ "melhorarmos cada vez mais nossos serviços você poderia avaliar esse atendimento?"
			+ " Se sim, em nível de satisfação, nos dê uma nota de 0 a 10 por favor.";
	
	@Autowired
	private AttendanceService service;

	public Message verifyMessageAttached(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append("Olá, me chamo ").append(message.getAttendant())
			.append(" estou analisando os dados que você nos passou e já resolvo seu problema.");
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
