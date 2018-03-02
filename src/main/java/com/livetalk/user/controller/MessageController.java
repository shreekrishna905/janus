package com.livetalk.user.controller;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.livetalk.user.dao.UserDAO;
import com.livetalk.user.modal.UserMessage;
import com.livetalk.user.utils.Message;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

	@Autowired
    private CamelContext camelContext;
	
	@Autowired
	private UserDAO userDAO;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
	
    
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Set<String>> getUsers() {
    	Set<String> userList = sessionRegistry.getAllPrincipals().stream()
			      .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
			      .map(Object::toString)
			      .collect(Collectors.toSet());
      return new ResponseEntity<Set<String>>(userList, HttpStatus.OK);
    }
    
	@PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<List<UserMessage>> getMessages(@RequestParam("creator") String creator, @RequestParam("recipient") String recipient) {
    	List<UserMessage> messages = userDAO.findAllMessage(creator, recipient);
    	return new ResponseEntity<List<UserMessage>>(messages, HttpStatus.OK);
    }
    
	/**
     * Receives the messages from clients and sends them to ActiveMQ.
     * 
     * @param message the message to send, encapsulated in a wrapper
     */
    
	@PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
    @RequestMapping(value = "/send-message", method = RequestMethod.POST)
    public ResponseEntity<Message> sendMessage(@RequestBody Message message, Principal principal) throws Exception {
    	UserMessage userMessage = new UserMessage(message.getText(), userDAO.findByEmail(principal.getName()), message.getDate(), (message.getParent()!=null) ? message.getParent():new Long(0), userDAO.findByEmail(message.getRecipient()));
		userMessage = userDAO.saveMessage(userMessage);
		message.setId(userMessage.getId());
		message.setCreator(principal.getName());
	    camelContext.createProducerTemplate().sendBody("activemq:rt_messages", message);
	    return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    
    
    
    
}
