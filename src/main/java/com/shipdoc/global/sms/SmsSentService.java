package com.shipdoc.global.sms;

import java.time.LocalDateTime;

public interface SmsSentService {
	void sendMessage(String receiverNumber, String messageText);
	String sendScheduledMessage(String receiverNumber, String messageText, LocalDateTime sendMessageTime);

	void cancelScheduledMessage(String taskId);
}
