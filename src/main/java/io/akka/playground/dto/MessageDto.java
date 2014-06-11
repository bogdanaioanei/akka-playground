package io.akka.playground.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class MessageDto {

	@NotEmpty
	private String message;

	@NotEmpty
	private String sender;

	@NotEmpty
	private String receiver;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
