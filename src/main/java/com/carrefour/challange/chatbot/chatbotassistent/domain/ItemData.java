package com.carrefour.challange.chatbot.chatbotassistent.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Dados que são selecionados pela ML do DialogFlow para otimizar o trabalho do atendente de suporte.
 * os dados precisam de uma estratégia para serem extraídos.
 * */
@Entity
public class ItemData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String dataType;
	private String value;
	
	public ItemData() {	}
	
	public ItemData(String dataType, String value){
		this.dataType = dataType;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
