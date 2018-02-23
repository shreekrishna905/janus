package com.livetalk.user.security.provider;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.livetalk.user.dao.UserDAO;
import com.livetalk.user.modal.User;


@Service("userDetails")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(email);		
		if(user==null){
			 throw new UsernameNotFoundException("User " + email + " not found");	
		}
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
	                 true, true, true, true, getGrantedAuthorities(user));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
        							.map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

}
