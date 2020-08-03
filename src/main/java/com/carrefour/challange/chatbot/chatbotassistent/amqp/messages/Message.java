package com.carrefour.challange.chatbot.chatbotassistent.amqp.messages;

import java.util.ArrayList;
import java.util.List;

import com.carrefour.challange.chatbot.chatbotassistent.domain.ItemData;

/**
 * Representa uam mensagem a que será trocada pelos elementos que interagem com a solução.
 * */
public class Message {

	private Long id;
	private String protocolAttendance;
	private String descriptionSituation;
	private Long chatIdTelegram;
	private List<ItemDataDTO> datas;
	private String attendant;
	private String messageDescription;
	
	public Message(String protocolAttendance, String descriptionSituation, Long chatIdTelegram, List<ItemData> datasSource) {
		this.attendant = null;
		this.messageDescription = "PENDENT";
		this.protocolAttendance = protocolAttendance;
		this.descriptionSituation = descriptionSituation;
		this.chatIdTelegram = chatIdTelegram;
		this.datas = new ArrayList<>();
		datasSource.forEach(ds -> datas.add(new ItemDataDTO(ds.getDataType(), ds.getValue())));
	}
	
	public Message() {
	}

	public Long getId() {
		return id;
	}

	public String getProtocolAttendance() {
		return protocolAttendance;
	}

	public void setProtocolAttendance(String protocolAttendance) {
		this.protocolAttendance = protocolAttendance;
	}

	public String getDescriptionSituation() {
		return descriptionSituation;
	}

	public void setDescriptionSituation(String descriptionSituation) {
		this.descriptionSituation = descriptionSituation;
	}

	public List<ItemDataDTO> getDatas() {
		return datas;
	}

	public void setDatas(List<ItemDataDTO> datas) {
		this.datas = datas;
	}

	public String getAttendant() {
		return attendant;
	}

	public Long getChatIdTelegram() {
		return chatIdTelegram;
	}

	public void setChatIdTelegram(Long chatIdTelegram) {
		this.chatIdTelegram = chatIdTelegram;
	}

	public void setAttendant(String attendant) {
		this.attendant = attendant;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

}
