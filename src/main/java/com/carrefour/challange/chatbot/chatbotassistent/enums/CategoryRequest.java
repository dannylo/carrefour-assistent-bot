package com.carrefour.challange.chatbot.chatbotassistent.enums;


public enum CategoryRequest  {

	BUY(1,"Compra/Pedido"),
	CREDIT_CARD(2, "Cartão de Crédito");
	
	private int code;
	private String description;
	
	private CategoryRequest(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	
	
}
