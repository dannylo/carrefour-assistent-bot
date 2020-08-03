package com.carrefour.challange.chatbot.chatbotassistent.amqp.messages;

public class ItemDataDTO {

	private String dataType;
	private String value;
	
	public ItemDataDTO(String dataType, String value) {
		this.dataType = dataType;
		this.value = value;
	}
	
	public ItemDataDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
