package com.carrefour.challange.chatbot.chatbotassistent.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.services.AttendanceService;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

	@Autowired
	private AttendanceService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<Attendance>> getAll(){
		return ResponseEntity.ok().body(service.getAll());
	}
	
	@GetMapping("/{protocol}")
	public ResponseEntity<Attendance> getByProtocol(@PathVariable("protocol") String protocol){
		return ResponseEntity.ok().body(service.getByProtocol(protocol));
	}
	
}
