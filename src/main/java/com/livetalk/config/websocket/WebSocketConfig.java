package com.livetalk.config.websocket;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.livetalk.config.oauth.JwtAuthentication;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // use the /topic prefix for outgoing WebSocket communication
        config.enableSimpleBroker("/topic");

        // use the /app prefix for others
        config.setApplicationDestinationPrefixes("/app");
    }
    
    @Autowired
    private JwtAuthentication jwtAuthentication;
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // use the /messaging endpoint (prefixed with /app as configured above) for incoming requests
        registry.addEndpoint("/messaging").setAllowedOrigins("http://localhost:8080").withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                List<String> tokenList = accessor.getNativeHeader("Authorization");
                String token = null;
                if(tokenList != null && tokenList.size() > 0) {
                  token = tokenList.get(0).replaceAll("Bearer", "").trim();
                }
                if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SUBSCRIBE.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand()) ) {
                    Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
                    if(auth==null){
                    	 Authentication user = jwtAuthentication.getAuthentication(token); // access authentication header(s)
                    	 SecurityContextHolder.getContext().setAuthentication(user);
                    	 accessor.setUser(user);
                    } else {
                    	accessor.setUser(auth);
                    }
                }
                return message;
            }
        });
    }
}
