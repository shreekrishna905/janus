package com.livetalk.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebSocketController {

	@Autowired
    private SimpMessagingTemplate messageTemplate;

    /**
     * Listens the /app/messaging endpoint and when a message is received, gets the user information encapsulated within it, and informs all clients
     * listening at the /topic/users endpoint that the user has joined the topic.
     * 
     * @param message the encapsulated STOMP message
     */
    @MessageMapping("/messaging")
    public void messaging( Message<Object> message, @RequestParam("username") String username) {
        // notify all users that a user has joined the topic
        messageTemplate.convertAndSend("/topic/users", username);
    }
}
