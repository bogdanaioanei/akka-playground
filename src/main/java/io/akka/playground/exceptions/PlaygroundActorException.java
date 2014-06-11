package io.akka.playground.exceptions;

import io.akka.playground.actors.messages.ActorMessage;

public class PlaygroundActorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ActorMessage actorMessage;

	public ActorMessage getActorMessage() {
		return actorMessage;
	}

	public PlaygroundActorException(String message, ActorMessage actorMessage) {
		super(message);
		this.actorMessage = actorMessage;
	}
}
