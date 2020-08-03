package com.carrefour.challange.chatbot.chatbotassistent;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;

import com.google.api.client.util.Value;

@SpringBootApplication
public class ChatbotAssistentApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(ChatbotAssistentApplication.class, args);
	}

	@Bean
	public Queue attendanceBuyMessageToApi() {
		return new Queue("attendanceBuyMessageToApi", true);
	}

	@Bean
	public Queue attendanceBuyMessageToAttendant() {
		return new Queue("attendanceBuyMessageToAttendant", true);
	}

	@Bean
	public Queue attendanceCardMessageToAttendant() {
		return new Queue("attendanceCardMessageToAttendant", true);
	}

	@Bean
	public Queue attendanceCardMessageToApi() {
		return new Queue("attendanceCardMessageToApi", true);
	}

}
