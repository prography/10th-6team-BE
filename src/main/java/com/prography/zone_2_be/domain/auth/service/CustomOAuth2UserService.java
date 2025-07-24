package com.prography.zone_2_be.domain.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.prography.zone_2_be.domain.user.entity.Role;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

	//TODO: 리팩 토링
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		OAuth2User oauth2User = defaultService.loadUser(userRequest);
		Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());

		if ("naver".equals(registrationId)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> response = (Map<String, Object>)attributes.get("response");
			log.info("response: {}", response);
			response.put("resultcode", response.get("id"));
			attributes = response;
		}

		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		return new DefaultOAuth2User(
			AuthorityUtils.createAuthorityList(String.valueOf(Role.User)),
			attributes,
			userNameAttributeName
		);
	}
}
