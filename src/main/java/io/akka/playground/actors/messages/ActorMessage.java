package io.akka.playground.actors.messages;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class ActorMessage {

	private String messageId;
	private String destinedActorName;
	private Object payload;

	public ActorMessage(String destinedActorName, Object payload) {
		checkIfNotBlank(destinedActorName);
		checkIfNotNull(payload);
		generateMessageId();

	}

	private ActorMessage(String destinedActorName, Object payload, String messageId) {
		checkIfNotBlank(destinedActorName);
		checkIfNotNull(payload);
		this.messageId = messageId;
	}

	private void checkIfNotBlank(String destinedActorName) {
		if (!StringUtils.isBlank(destinedActorName)) {
			this.destinedActorName = destinedActorName;
		} else {
			throw new IllegalArgumentException("destinedActorName cannot be blank");
		}
	}

	private void checkIfNotNull(Object payload) {
		if (payload != null) {
			this.payload = payload;
		} else {
			throw new IllegalArgumentException("payload cannot be null");
		}
	}

	private void generateMessageId() {
		messageId = RandomStringUtils.randomAlphanumeric(16);
	}

	public String getMessageId() {
		return messageId;
	}

	public String getDestinedActorName() {
		return destinedActorName;
	}

	public Object getPayload() {
		return payload;
	}

	/**
	 * Create a new ActorMessage with the same messageId. Useful for breaking
	 * messages into smaller pieces and handling them in a more finely grained
	 * way. The results can then be aggregated into one result using the
	 * messageId.
	 * */
	public ActorMessage spawn(String destinedActorName, Object payload) {
		return new ActorMessage(destinedActorName, payload, messageId);
	}

	@Override
	public String toString() {
		return "ActorMessage [messageId=" + messageId + ", destinedActorName=" + destinedActorName + ", payload="
				+ payload + "]";
	}

}
