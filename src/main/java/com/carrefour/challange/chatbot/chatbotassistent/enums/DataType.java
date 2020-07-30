package com.carrefour.challange.chatbot.chatbotassistent.enums;

public enum DataType {
	
	BUY_NUMBER("Código da Compra"),
	CREDIT_CARD("Cartão de Crédito"),
	PRODUCT_CODE("Código do Produto"),
	PHONE("Telefone de Contato");
	
	private String description;
	
	private DataType(String description) {
		this.description = description;
	}

}
