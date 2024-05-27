package com.shipdoc.domain.chatbot.config;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.google.protobuf.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Value;

public class ChatConfig {
    private SessionsClient client;

    private String project;

    public ChatConfig (String credentialFile, String project) throws FileNotFoundException, IOException {
        this.project = project;
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialFile));
        SessionsSettings settings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        client = SessionsClient.create(settings);
    }

    public String request(String sessionId, String message) {
        QueryInput queryInput;
        queryInput = QueryInput.newBuilder()
                .setText(
                        TextInput.newBuilder()
                                .setText(message)
                                .setLanguageCode("ko")
                                .build())
                .build();

        // Perform query
        SessionName session = SessionName.of(project, sessionId);
        DetectIntentResponse actualResponse = client.detectIntent(session, queryInput);

        QueryResult queryResult = actualResponse.getQueryResult();

        // Extract entities
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Fulfillment Text: ").append(queryResult.getFulfillmentText()).append("\n");

        Struct parameters = queryResult.getParameters();
            responseBuilder.append(parameters);

        // Assuming "clinic" is the entity name for "이비인 후과"

        System.out.println("Response: " + responseBuilder.toString());
        return actualResponse.getQueryResult().getFulfillmentText();
    }

}