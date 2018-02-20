package com.livetalk.user.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.livetalk.exception.util.UserDuplicationException;
import com.livetalk.user.dao.UserDAO;
import com.livetalk.user.modal.Role;
import com.livetalk.user.modal.User;


@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        			}
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
	
	@PreAuthorize("#oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user) throws UserDuplicationException {
			if (userDAO.isUserExist(user.getEmail())) {
		            throw  new UserDuplicationException("User with username:"+ user.getEmail()+" already exists");
		    }
			Role role = userDAO.findRoleByName("ROLE_USER");
			user.addRole(role);
			// Encode password and set to user password
			String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(encodedPassword);	
	        userDAO.saveOrUpdate(user);
	        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
	@RequestMapping(value = "/users/online", method = RequestMethod.POST)
	public ResponseEntity<List<String>> userList() {
			List<String> userList = sessionRegistry.getAllPrincipals().stream()
				      .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
				      .map(Object::toString)
				      .collect(Collectors.toList());
	        return new ResponseEntity<List<String>>(userList, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/user/check", method = RequestMethod.GET)
	public ResponseEntity<String> checkUser(@RequestParam("email") String email, UriComponentsBuilder ucBuilder){
			if (userDAO.isUserExist(email)) {
		        	return new ResponseEntity<String>("{\"status\":true}", HttpStatus.OK);
		    }
			return new ResponseEntity<String>("{\"status\":false}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<User> getUser(@RequestParam("email") String email){
		 User user = userDAO.findByEmail(email);
	        if (user == null) {
	            System.out.println("User with username " + email + " not found");
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
}
