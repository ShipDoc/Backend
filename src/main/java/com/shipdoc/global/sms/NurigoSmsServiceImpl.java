package com.shipdoc.global.sms;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NurigoSmsServiceImpl implements SmsSentService{
	private final DefaultMessageService defaultMessageService;

	@Value("${sms.nurigo.sender-number}")
	private String senderNumber;

	public NurigoSmsServiceImpl(@Value("${sms.nurigo.api-key}") String apiKey, @Value("${sms.nurigo.api-secret}") String apiSecret ) {
		this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}

	/**
	 * 단일 메세지 전송
	 */

	@Async
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
	@Async
	public void sendScheduledMessage(String receiverNumber, String messageText, String time) {
		Message message = new Message();
		// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
		message.setFrom("발신번호 입력");
		message.setTo("수신번호 입력");
		message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다.");

		try {
			// 과거 시간으로 예약 발송을 진행할 경우 즉시 발송처리 됩니다.
			LocalDateTime localDateTime = LocalDateTime.parse("2022-11-26 00:00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(localDateTime);
			Instant instant = localDateTime.toInstant(zoneOffset);

			MultipleDetailMessageSentResponse response = this.defaultMessageService.send(message, instant);

			System.out.println(response);

		} catch (NurigoMessageNotReceivedException exception) {
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

}
