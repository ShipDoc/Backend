package com.shipdoc.global.sms;

public interface SmsSentService {
	void sendMessage(String receiverNumber, String messageText);
	void sendScheduledMessage(String receiverNumber, String messageText, String time);
}
