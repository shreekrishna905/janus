package com.livetalk.user.modal;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name="parent_message_id")
    private UserMessage parent;
	
	@OneToMany(mappedBy="parent")
    private Set<UserMessage> replies;
    
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private User recipient;
	
	public UserMessage(){
		
	}
	
	public UserMessage(Long id){
		this.id = id;
	}
	
	public UserMessage(String message, User creator, Date createDate, Long parent, User recipient) {
		super();
		this.message = message;
		this.creator = creator;
		this.createDate = createDate;
		this.recipient = recipient;
		if(!parent.equals(new Long(0))){
			this.parent = new UserMessage(parent);
		}
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
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
