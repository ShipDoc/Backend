package com.shipdoc.global.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.global.response.ApiResponse;
import com.shipdoc.global.security.jwt.JwtLoginResponseDto;
import com.shipdoc.global.security.jwt.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
/**
 * JWT 로그인 필터 Success Handler
 */
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;
	private final MemberRepository memberRepository;

	/**
	 * 인증에 성공하면 Access Token과 Refresh Token을 생성한 후 반환
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		UserDetails principal = (UserDetails)authentication.getPrincipal();
		String loginId = principal.getUsername();
		log.info("Jwt Login Success :: Login ID = {}", loginId);
		Member member = memberRepository.findByLoginId(loginId).get();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(
			ApiResponse.onSuccess(JwtLoginResponseDto.builder().userName(member.getPatientList().get(0).getName()).validToken(true))));
		loginSuccess(response, loginId);
	}

	private void loginSuccess(HttpServletResponse response, String loginId) {
		String accessToken = jwtService.createAccessToken(loginId);
		String refreshToken = jwtService.createRefreshToken();
		response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
		response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtService.updateRefreshToken(loginId, refreshToken);
	}
}
