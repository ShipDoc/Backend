package com.shipdoc.global.sms;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import com.shipdoc.global.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NurigoSmsServiceImpl implements SmsSentService{
	private final DefaultMessageService defaultMessageService;
	private final SchedulerService schedulerService;

	@Value("${sms.nurigo.sender-number}")
	private String senderNumber;

	public NurigoSmsServiceImpl(@Value("${sms.nurigo.api-key}") String apiKey, @Value("${sms.nurigo.api-secret}") String apiSecret, SchedulerService schedulerService ) {
		this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
		this.schedulerService = schedulerService;
	}

	/**
	 * 단일 메세지 전송
	 */

	@Async
	@Override
	public void sendMessage(String receiverNumber, String messageText) {
		Message message = new Message();
		// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
		message.setFrom(senderNumber);
		message.setTo(receiverNumber);
		message.setText(messageText); // 한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지

		SingleMessageSentResponse response = this.defaultMessageService.sendOne(
			new SingleMessageSendingRequest(message));
		log.info(response.toString());
	}




	/**
	 * 메세지 예약 발송
	 */
	@Override
	public String sendScheduledMessage(String receiverNumber, String messageText, LocalDateTime sendMessageTime) {
		ZonedDateTime zonedDateTime = sendMessageTime.atZone(ZoneId.systemDefault());
		Instant instant = zonedDateTime.toInstant();
		Date scheduledTime = Date.from(instant);

		String taskId = UUID.randomUUID().toString();
		schedulerService.scheduleTask(taskId, () -> sendMessage(receiverNumber, messageText), scheduledTime);
		return taskId;
	}

	/**
	 * 메세지 예약 발송 취소
	 */
	@Override
	public void cancelScheduledMessage(String taskId){
		schedulerService.cancelTask(taskId);
	}

}
