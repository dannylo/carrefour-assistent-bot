package com.carrefour.challange.chatbot.chatbotassistent.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;
import com.carrefour.challange.chatbot.chatbotassistent.repository.AttendenceRepository;

@Service
public class AttendanceService {

	@Autowired
	private AttendenceRepository repository;

	public Attendance save(Attendance attendance) {
		return this.repository.save(attendance);
	}
	
	public List<Attendance> getAll(){
		return this.repository.findAll();
	}
	
	}
