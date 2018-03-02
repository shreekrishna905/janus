package com.livetalk.user.dao;

import java.util.List;

import com.livetalk.user.modal.Role;
import com.livetalk.user.modal.User;
import com.livetalk.user.modal.UserMessage;

public interface UserDAO {
	public List<User> findAll();
	public Role findRoleByName(String role);
	public User findByEmail(String email);
	public void saveOrUpdate(User user);
	public UserMessage saveMessage(UserMessage userMessage);
	public boolean isUserExist(String email);
	public List<UserMessage> findAllMessage(String creator, String recipient);
}
