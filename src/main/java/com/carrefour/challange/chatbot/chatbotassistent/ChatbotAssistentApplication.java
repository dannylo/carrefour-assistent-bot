package com.carrefour.challange.chatbot.chatbotassistent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;

@SpringBootApplication
public class ChatbotAssistentApplication {
	
	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(ChatbotAssistentApplication.class, args);
	}

}
