package io.akka.playground.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.akka.playground.entities.Message;
import io.akka.playground.enums.MessageStatus;
import io.akka.playground.repositories.MessageRepository;
import io.akka.playground.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public List<Message> getMessagesByStatus(MessageStatus messageStatus) {
		return messageRepository.findByMessageStatus(messageStatus);
	}

	@Override
	public List<Message> getMessages() {
		return messageRepository.findAll();
	}

}
