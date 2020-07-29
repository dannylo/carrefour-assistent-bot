package com.carrefour.challange.chatbot.chatbotassistent.enums;

public enum TypeProblem {
	
	FEEDBACK(1, "Feedback"),
	PROBLEM (2, "Problema");
	
	private int code;
	private String description;
	
	private TypeProblem(int code, String description) {
		this.code = code;
		this.description = description;
	}

}
