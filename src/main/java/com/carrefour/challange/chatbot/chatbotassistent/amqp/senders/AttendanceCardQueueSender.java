package com.carrefour.challange.chatbot.chatbotassistent.amqp.senders;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrefour.challange.chatbot.chatbotassistent.amqp.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AttendanceCardQueueSender {

	@Autowired
	private RabbitTemplate template;
	
	@Autowired
	private Queue attendanceCardMessageToAttendant;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public void send(Message message) throws JsonProcessingException {
		String json = mapper.writeValueAsString(message);
		template.convertAndSend(attendanceCardMessageToAttendant.getName(), json);
	}
}
