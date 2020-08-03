package com.carrefour.challange.chatbot.chatbotassistent.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Evaluation;
import com.carrefour.challange.chatbot.chatbotassistent.repository.EvaluationRepository;

@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository repository;

	public List<Evaluation> listAll(){
		return repository.findAll();
	}
	
	public double average() {
		return this.listAll()
				.stream()
				.mapToInt(m -> m.getSatisfactionValue())
				.average()
				.getAsDouble();
	}
}
