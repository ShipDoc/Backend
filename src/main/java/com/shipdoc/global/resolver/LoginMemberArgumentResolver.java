package com.shipdoc.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shipdoc.domain.Member.entity.Member;
import com.shipdoc.domain.Member.exception.MemberNotExistException;
import com.shipdoc.domain.Member.repository.MemberRepository;
import com.shipdoc.global.annotation.LoginMember;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
	private final MemberRepository memberRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotations = parameter.hasParameterAnnotation(LoginMember.class);
		boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
		return hasParameterAnnotations && hasMemberType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request == null) {
			throw new IllegalStateException("올바르지 않은 요청 타입입니다. webRequest : " + webRequest);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Member member = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			return memberRepository.findByLoginId(userDetails.getUsername())
				.orElseThrow(() -> new MemberNotExistException());
		} else {
			log.info("알 수 없는 인증 타입");
			throw new IllegalStateException("지원하지 않는 인증 타입입니다.");
		}
	}
}
