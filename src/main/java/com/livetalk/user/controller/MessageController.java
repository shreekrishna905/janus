package com.livetalk.user.controller;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.livetalk.user.modal.MessageDTO;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

	@Autowired
    private CamelContext camelContext;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
	
    /**
     * Receives the messages from clients and sends them to ActiveMQ.
     * 
     * @param message the message to send, encapsulated in a wrapper
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json")
    public void sendMessage(@RequestBody MessageDTO message, Principal currentUser) {
        // send any message sent by clients to a queue called rt_messages
        message.from = currentUser.getName();
        camelContext.createProducerTemplate().sendBody("activemq:rt_messages", message);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Set<String>> getUsers() {
    	Set<String> userList = sessionRegistry.getAllPrincipals().stream()
			      .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
			      .map(Object::toString)
			      .collect(Collectors.toSet());
      return new ResponseEntity<Set<String>>(userList, HttpStatus.OK);
    }
    
}
