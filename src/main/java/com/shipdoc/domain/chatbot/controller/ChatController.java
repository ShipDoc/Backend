package com.shipdoc.domain.chatbot.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shipdoc.domain.chatbot.config.ChatConfig;
import com.shipdoc.domain.chatbot.entity.Chat;
import com.shipdoc.domain.chatbot.service.IChatService;
import com.shipdoc.global.response.ApiResponse;

@RestController
@RequestMapping("api/chatbot")
public class ChatController {

	@Autowired
	IChatService chatService;

	@Value("classpath:test-iwdw-e3b736d87722.json")
	private Resource jsonFile;

	@CrossOrigin
	@PostMapping("/msg")
	@ResponseBody
	public ApiResponse<String> sendMessage(@RequestBody Chat msg) throws FileNotFoundException, IOException {
		String jsonFilePath = Paths.get(jsonFile.getURI()).toString();
		File file = new File(jsonFilePath);
		String CREDENTIAL_FILE = file.getAbsolutePath();
		String PROJECT_ID = "test-iwdw";
		ChatConfig client = new ChatConfig(CREDENTIAL_FILE, PROJECT_ID);
		String sessionId = UUID.randomUUID().toString();
		Chat chat = new Chat(msg.getMessageSent(), client.request(sessionId, msg.getMessageSent()));
		chatService.addChat(chat);
		return ApiResponse.onSuccess(client.request(sessionId, msg.getMessageSent()));
	}

}
