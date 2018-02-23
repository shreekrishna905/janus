package com.livetalk.config.oauth;

import java.security.PublicKey;

import org.apache.commons.io.IOUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.RsaKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("jwtAuthentication")
public class JwtAuthenticationImpl implements JwtAuthentication {
	
	
	@Autowired
	@Qualifier("userDetails")
	private UserDetailsService userDetailImpl;
	
	
	PublicKey getPublicKey() throws Exception {
		Resource resource = new ClassPathResource("public.txt");
		String publicKeyPEM = IOUtils.toString(resource.getInputStream());
		RsaKeyUtil rsaKeyUtil = new RsaKeyUtil();
	    PublicKey publicKey = rsaKeyUtil.fromPemEncoded(publicKeyPEM);
	    return publicKey;
}
	
	private String getUsernameFromToken( String authToken) {
		String username;
	    try {
	    	 JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	    	            .setRequireExpirationTime()
	    	            .setVerificationKey(getPublicKey())
	    	            .build();
	    	 JwtClaims jwtDecoded = jwtConsumer.processToClaims(authToken);
	    	  username = jwtDecoded.getStringClaimValue("username");

	    } catch (Exception e) {
	        username = null;
	    }
	    return username;
	}
	
	@Override
	public Authentication getAuthentication(String authToken){
		String username = getUsernameFromToken(authToken);
		 UserDetails userDetails = userDetailImpl.loadUserByUsername( username );
         // Create authentication
         TokenBasedAuthentication authentication = new TokenBasedAuthentication( userDetails );
         authentication.setToken( authToken );
         return authentication;
	}
}
