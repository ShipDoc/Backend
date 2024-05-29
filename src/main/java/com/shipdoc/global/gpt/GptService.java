package com.shipdoc.global.gpt;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipdoc.global.enums.statuscode.ErrorStatus;
import com.shipdoc.global.exception.GeneralException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {
	private final ObjectMapper objectMapper;

	@Value("${gpt.api-key}")
	private String apiKey;

	@Value("${gpt.api-url}")
	private String apiUrl;

	public String gptChatCompletion(String diagnosis) {
		HttpClient client = HttpClient.newHttpClient();

		String prompt =
			"병에 대한 키워드를 주면, 다음과 같은 예시로 도움이 되는 정보를 알려줘. 예시는 \"코감기\"라는 키워드가 주어졌을 때 원하는 답변 형식이야. markdown 형식 말고 plain-text 형식으로 알려줘. 추천 내용은 3줄 정도로 알려줘"
				+ "<예시>"
				+ "코감기” 에 걸리셨군요?\n"
				+ "\n"
				+ "단백질, 비타민, 미네랄, 무기질 등이 풍부한 식품을 섭취하는 것이 좋습니다. \n"
				+ "달걀, 콩, 두부 등 단백질이 풍부한 음식과, 비타민이 풍부한 과일, 채소, \n"
				+ "그리고 미네랄이 풍부한 감자, 고구마, 현미 등을 충분히 섭취해주는 것이 좋아요.";

		Map<String, Object> requestBody = Map.of(
			"model", "gpt-4o",
			"messages", new Object[] {
				Map.of("role", "system", "content", prompt),
				Map.of("role", "user", "content", diagnosis)
			}
		);
		try {
			String requestBodyJson;
			requestBodyJson = objectMapper.writeValueAsString(requestBody);

			HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(apiUrl))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + apiKey)
				.POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			JsonNode jsonNode = objectMapper.readTree(response.body().toString());
			String content = jsonNode.path("choices").get(0).path("message").path("content").asText();
			return content;
		} catch (Exception e) {
			log.error("GPT 호출 중 오류가 발생했습니다.");
			throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
		}
	}

}
