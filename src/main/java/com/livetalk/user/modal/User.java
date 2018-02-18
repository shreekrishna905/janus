package com.livetalk.user.modal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="users")
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

	@Id
	@SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq",allocationSize=1)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="users_id_seq")
	private Long id;
	
	private String password;
	
	@JsonProperty("last_name")
	@Column(name="last_name")
	private String lastName;
	
	@JsonProperty("first_name")
	@Column(name="first_name")
	private String firstName;
	
	private String email;
	
	private String about;
	
	private boolean enabled =true;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
	private List<Role> roles;
	
	@OneToMany(mappedBy="creator")
    private Set<UserMessage> creators;
	
	@OneToMany(mappedBy="recipient")
    private Set<UserMessage> recipients;
	
	
	public User(){
		this.roles = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	//Helper Class To Add Role
	public void addRole(Role role){
		role.getUsers().add(this);
		this.roles.add(role);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Set<UserMessage> getCreators() {
		return creators;
	}

	public void setCreators(Set<UserMessage> creators) {
		this.creators = creators;
	}

	public Set<UserMessage> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<UserMessage> recipients) {
		this.recipients = recipients;
	}

}
