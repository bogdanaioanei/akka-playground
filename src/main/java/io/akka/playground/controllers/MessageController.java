package io.akka.playground.controllers;

import io.akka.playground.dto.MessageDto;
import io.akka.playground.entities.Message;
import io.akka.playground.services.MessageService;
import io.akka.playground.services.ProcessingService;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Controller
public class MessageController {

	@Autowired
	private ProcessingService processingService;

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/messages/send", method = RequestMethod.POST)
	public void sendMessages(@Valid List<MessageDto> messages, BindingResult bindingResult, HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return;
		}

		//TODO: implement convert mechanism from MessageDto to ActorMessage
		//processingService.processMessages(messages);
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public List<MessageDto> getMessages() {
		List<Message> messages = messageService.getMessages();

		return Lists.transform(messages, messageToMessageDto());

	}

	private Function<Message, MessageDto> messageToMessageDto() {

		return new Function<Message, MessageDto>() {

			@Override
			public MessageDto apply(Message message) {
				MessageDto messageDto = new MessageDto();
				messageDto.setMessage(message.getMessage());
				messageDto.setSender(message.getSender());
				messageDto.setReceiver(message.getReceiver());

				return messageDto;
			}

		};
	}
}
