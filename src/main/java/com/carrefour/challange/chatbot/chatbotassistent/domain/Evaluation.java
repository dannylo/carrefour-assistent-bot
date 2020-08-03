package com.carrefour.challange.chatbot.chatbotassistent.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representa uma avaliação realizada ao término de cada atendimento.
 * */
@Entity
public class Evaluation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String protocolAttendance;
	
	private int satisfactionValue;

	public Long getId() {
		return id;
	}

	public String getProtocolAttendance() {
		return protocolAttendance;
	}

	public void setProtocolAttendance(String protocolAttendance) {
		this.protocolAttendance = protocolAttendance;
	}

	public int getSatisfactionValue() {
		return satisfactionValue;
	}

	public void setSatisfactionValue(int satisfactionValue) {
		this.satisfactionValue = satisfactionValue;
	}
	
	

}
