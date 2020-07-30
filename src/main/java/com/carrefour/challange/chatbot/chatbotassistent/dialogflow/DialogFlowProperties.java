package com.carrefour.challange.chatbot.chatbotassistent.dialogflow;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dialogflow")
public class DialogFlowProperties {

	private String projectId;
	private String languageCode;
	private String settingsPath;

	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getSettingsPath() {
		return settingsPath;
	}

	public void setSettingsPath(String settingsPath) {
		this.settingsPath = settingsPath;
	}
	
	

}
