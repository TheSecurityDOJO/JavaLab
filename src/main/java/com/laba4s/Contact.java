package com.laba4s;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class Contact {

	private String email;
	private String content;

	@JsonTypeInfo(use = Id.CLASS)
	public Object details;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public Contact(String email, String content, Object details) {
		super();
		this.email = email;
		this.content = content;
		this.details = details;
	}

	public Contact() {
		super();
	}

}
