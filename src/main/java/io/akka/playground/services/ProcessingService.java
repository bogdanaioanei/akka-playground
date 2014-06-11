package io.akka.playground.services;

import io.akka.playground.actors.messages.ActorMessage;

public interface ProcessingService {

	void process(ActorMessage message);
}
