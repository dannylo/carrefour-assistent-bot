package com.carrefour.challange.chatbot.chatbotassistent.enums;

public enum DataRequest {
	
	CPF("CPF"), NAME("Nome Completo"), REQUEST_NUMBER("Número da Compra/Pedido"), ERROR_DESCRIPTION("Descrição do erro.");
	
	private String description;
	
	private DataRequest(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	

}
