package com.carrefour.challange.chatbot.chatbotassistent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Evaluation;
import com.carrefour.challange.chatbot.chatbotassistent.services.EvaluationService;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
	
	@Autowired
	private EvaluationService service;

	@GetMapping
	public ResponseEntity<List<Evaluation>> listAll(){
		return ResponseEntity.ok().body(this.service.listAll());
	}
	
	@GetMapping("/average")
	public ResponseEntity<Double> getAverage(){
		return ResponseEntity.ok().body(this.service.average());
	}
}
