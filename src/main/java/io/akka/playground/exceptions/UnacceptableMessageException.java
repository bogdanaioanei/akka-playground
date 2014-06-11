package io.akka.playground.exceptions;

public class UnacceptableMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnacceptableMessageException() {
		super("This ActorSystem only accepts messages that are instances of the "
				+ "io.akka.playground.actors.messages.ActorMessage class.");
	}
}
