package io.akka.playground.services;

import io.akka.playground.entities.Message;
import io.akka.playground.enums.MessageStatus;

import java.util.List;

public interface MessageService {

	List<Message> getMessagesByStatus(MessageStatus messageStatus);
	List<Message> getMessages();
}
