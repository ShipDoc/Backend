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
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;

public class ChatConfig {
    private SessionsClient client;
    private String project;
    private static final Logger logger = LoggerFactory.getLogger(ChatConfig.class);

    private static final Map<String, String> symptomMap = Map.of(
            "어깨", "Symptoms-Orthopedics",
            "목", "Symptoms-Otolaryngology",
            "피부", "Symptoms-Dermatology",
            "감기", "Symptoms-InternalMedicine",
            "허리", "Symptoms-Neurosurgery",
            "시력",  "Symptoms-Ophthalmology",
            "두통", "Symptoms-InternalMedicine",
            "인후통", "Symptoms-Otolaryngology",
            "메스꺼", "Symptoms-InternalMedicine",
            "복통", "Symptoms-InternalMedicine"
            //"몸살", "Symptoms-InternalMedicine"
            //"설사", "Symptoms-InternalMedicine",
            //"기침", "Symptoms-InternalMedicine",
            //"발열", "Symptoms-InternalMedicine",
            //"관절통", "Symptoms-Neurosurgery"// 필요한 다른 매핑 추가
    );
    private static final Map<String, String> symptomMap2 = Map.of(
            "관절", "Symptoms-Orthopedics",
            "기침", "Symptoms-Otolaryngology",
            "발진", "Symptoms-Dermatology",
            "설사", "Symptoms-InternalMedicine",
            "관절통", "Symptoms-Neurosurgery",
            "발열",  "Symptoms-InternalMedicine",
            "구토", "Symptoms-InternalMedicine",
            "어지럼증", "Symptoms-Neurosurgery",
            "몸살", "Symptoms-InternalMedicine",
            "콧물", "Symptoms-InternalMedicine"
            //"몸살", "Symptoms-InternalMedicine"
            //"설사", "Symptoms-InternalMedicine",
            //"기침", "Symptoms-InternalMedicine",
            //"발열", "Symptoms-InternalMedicine",
            //"관절통", "Symptoms-Neurosurgery"// 필요한 다른 매핑 추가
    );

    public ChatConfig(String credentialFile, String project) throws FileNotFoundException, IOException {
        this.project = project;
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialFile));
        SessionsSettings settings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        client = SessionsClient.create(settings);
    }

    public String request(String sessionId, String message) {
        QueryInput queryInput = QueryInput.newBuilder()
                .setText(
                        TextInput.newBuilder()
                                .setText(message)
                                .setLanguageCode("ko")
                                .build())
                .build();

        SessionName session = SessionName.of(project, sessionId);
        DetectIntentResponse actualResponse = client.detectIntent(session, queryInput);

        QueryResult queryResult = actualResponse.getQueryResult();

        String fulfillmentText = queryResult.getFulfillmentText();
        Struct parameters = queryResult.getParameters();

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Fulfillment Text: ").append(fulfillmentText).append("\n");

        for (Map.Entry<String, String> entry : symptomMap.entrySet()) {
            String keyword = entry.getKey();
            String fieldName = entry.getValue();
            if (message.contains(keyword) && parameters.containsFields(fieldName)) {
                ListValue listValue = parameters.getFieldsOrThrow(fieldName).getListValue();
                for (Value value : listValue.getValuesList()) {
                    responseBuilder.append(fieldName).append(": ").append(value.getStringValue()).append("\n");
                }
            }
            else{
                for (Map.Entry<String, String> entry2 : symptomMap2.entrySet()) {
                    String keyword2 = entry2.getKey();
                    String fieldName2 = entry2.getValue();
                    if (message.contains(keyword2) && parameters.containsFields(fieldName2)) {
                        ListValue listValue = parameters.getFieldsOrThrow(fieldName2).getListValue();
                        for (Value value : listValue.getValuesList()) {
                            responseBuilder.append(fieldName2).append(": ").append(value.getStringValue()).append("\n");
                        }
                    }
            }}
        }

        logger.info("Response: " + responseBuilder.toString());
        return fulfillmentText;
    }
}