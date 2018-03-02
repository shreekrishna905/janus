package com.livetalk.user.utils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Message {

	private Long id;
	
	private String creator;
	
	private Long parent;
	
	private String uuid;
	
	private String text;

	@JsonDeserialize(using=DateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EE, MMM dd, yyyy, hh:mm a")
	private Date date;
	
	private String recipient;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getParent() {
	 return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
 		this.text = text;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
