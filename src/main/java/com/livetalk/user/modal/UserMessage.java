package com.livetalk.user.modal;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="user_message")
public class UserMessage {

	@Id
	@SequenceGenerator(name="user_message_id_seq", sequenceName="user_message_id_seq",allocationSize=1)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_message_id_seq")
	private Long id;
	
	private String message;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private User creator;
	
	@Column(name="create_date")
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name="parent_message_id")
    private UserMessage parent;
	
	@OneToMany(mappedBy="parent")
    private Set<UserMessage> replies;
    
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private User recipient;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public User getCreator() {
		return creator;
	}


	public void setCreator(User creator) {
		this.creator = creator;
	}


	public LocalDateTime getCreateDate() {
		return createDate;
	}


	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}


	public UserMessage getParent() {
		return parent;
	}


	public void setParent(UserMessage parent) {
		this.parent = parent;
	}


	public Set<UserMessage> getReplies() {
		return replies;
	}


	public void setReplies(Set<UserMessage> replies) {
		this.replies = replies;
	}

	public User getRecipient() {
		return recipient;
	}


	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
}
