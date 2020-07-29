package com.carrefour.challange.chatbot.chatbotassistent.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistentController {

	@GetMapping
	public ResponseEntity<?> hello(){
		
		
		return ResponseEntity.ok("Hello, welcome to the Carrefour Assistent API, we're listening the Telegram API o/");
	}
	
}
