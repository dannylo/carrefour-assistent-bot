package com.carrefour.challange.chatbot.chatbotassistent.dialogflow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

@Component
public class DialogFlowAgent {
	
	@Autowired
	private DialogFlowProperties props;
	private String sessionId = UUID.randomUUID().toString();
	
	public QueryResult sendMessage(String text) throws IOException {
		TextInput.Builder textInput = TextInput
					.newBuilder()
					.setText(text)
					.setLanguageCode(props.getLanguageCode());
		
		QueryInput input = QueryInput
				.newBuilder()
				.setText(textInput)
				.build();
		
		DetectIntentResponse response = getNewSession()
				.detectIntent(getSessionName(), input);
		
		return response.getQueryResult();
	}
	
	private SessionsClient getNewSession() throws IOException {
		return SessionsClient.create(this.getCredentials());
	}
	
	private SessionName getSessionName() {
		SessionName session = SessionName.of(props.getProjectId(), sessionId);
		System.out.println("Session Path: " + session.toString());
		return session;
	}
	
	private SessionsSettings getCredentials() throws IOException {
	    InputStream stream = new FileInputStream(props.getSettingsPath());
	    GoogleCredentials credentials = GoogleCredentials.fromStream(stream);

	    SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
	    SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

	    return sessionsSettings;
	}
	
	
	

}
