package com.livetalk.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.livetalk.user.modal.Role;
import com.livetalk.user.modal.User;
import com.livetalk.user.modal.UserMessage;

@Service("userDAO")
@Repository
@Transactional
public class UserDAOImpl implements UserDAO {


	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly=true)
	public User findByEmail(String email) {
		List results =  entityManager.createQuery("select u from User u where u.email= :email")
											.setParameter("email", email)
											.getResultList();
		if (results.isEmpty()) {
		    return null; 
		} 
		    return (User) results.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<User> findAll(){
		List<User> users = entityManager
	                .createQuery("SELECT u FROM User u ORDER BY u.firstName ASC")
	                .getResultList();
	        return users;
	}
	
	@Override
	public void saveOrUpdate(User user){
		if(user.getId()==null){
			entityManager.persist(user);	
		} else {
			entityManager.merge(user);
		}
	}

	@Override
	public boolean isUserExist(String email) {
		return findByEmail(email) !=null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public Role findRoleByName(String role) {
		List results =  entityManager.createQuery("select r from Role r where r.name= :role")
				.setParameter("role", role)
				.getResultList();
		if (results.isEmpty()) {
		return null; 
		} 
		return (Role) results.get(0);
	}

	/*@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<UserMessage> findAllMessage(String creator, String recipient) {
		List<UserMessage> messages = entityManager
                .createQuery("from UserMessage u where u.creator.email=:creator and u.recipient.email=:recipient ORDER BY u.createDate DESC")
                .setParameter("creator", creator)
                .setParameter("recipient", recipient)
                .setFirstResult(0).setMaxResults(5)
				.getResultList();
        return messages;
	}*/
	
	@Override
	public String findAllMessage(String creator, String recipient) {
		 StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("find_message");
         storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
         storedProcedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
         storedProcedure.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
         storedProcedure.setParameter(1, creator);
         storedProcedure.setParameter(2, recipient);
         storedProcedure.execute();
        return storedProcedure.getOutputParameterValue(3).toString();
	}

	@Override
	public UserMessage saveMessage(UserMessage userMessage) {
		entityManager.persist(userMessage);	
		return userMessage;
		
	}
	

}
