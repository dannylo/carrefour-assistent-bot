package com.carrefour.challange.chatbot.chatbotassistent.enums;

public enum DataType {
	
	BUY_NUMBER("C�digo da Compra"),
	CREDIT_CARD("Cart�o de Cr�dito"),
	PRODUCT_CODE("C�digo do Produto"),
	PHONE("Telefone de Contato");
	
	private String description;
	
	private DataType(String description) {
		this.description = description;
	}

}
