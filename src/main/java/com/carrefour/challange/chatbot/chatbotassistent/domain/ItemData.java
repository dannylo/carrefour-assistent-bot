package com.carrefour.challange.chatbot.chatbotassistent.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.carrefour.challange.chatbot.chatbotassistent.enums.DataType;

@Entity
public class ItemData {

	@Id
	private Long id;

	private DataType dataType;
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
