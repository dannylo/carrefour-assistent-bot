package com.carrefour.challange.chatbot.chatbotassistent.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;

@RestController
@RequestMapping("/attendences")
public class AttendanceController {

	@Autowired
	private AttendanceService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<Attendance>> getAll(){
		return ResponseEntity.ok().body(service.getAll());
	}
	
	@GetMapping("/today")
	public ResponseEntity<?> getTodayAttendances(){
		return ResponseEntity.ok().body("Estamos trabalhando nisso, calma aê :D");
	}
}
