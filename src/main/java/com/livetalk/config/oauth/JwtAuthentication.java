package com.livetalk.config.oauth;

import org.springframework.security.core.Authentication;

public interface JwtAuthentication {
	
	public Authentication getAuthentication(String authToken);
}
