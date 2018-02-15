package com.livetalk.user.modal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@SequenceGenerator(name="roles_id_seq", sequenceName="roles_id_seq",allocationSize=1)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="roles_id_seq")
	private Long id;
	
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	private List<User> users;

	public Role(){
		
	}

	public Role(String name){
		this.name = "ROLE_" + name;
		this.users = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
