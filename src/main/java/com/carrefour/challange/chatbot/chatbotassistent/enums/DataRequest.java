package com.carrefour.challange.chatbot.chatbotassistent.enums;

public enum DataRequest {
	
	CPF("CPF"), NAME("Nome Completo"), REQUEST_NUMBER("N�mero da Compra/Pedido"), ERROR_DESCRIPTION("Descri��o do erro.");
	
	private String description;
	
	private DataRequest(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	

}
