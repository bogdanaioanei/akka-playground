package io.akka.playground.actors;

import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.exceptions.UnacceptableMessageException;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public abstract class PlaygroundUntypedActor extends UntypedActor {
	
	protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void onReceive(Object message) {
		if (message instanceof ActorMessage) {
			receiveActorMessage((ActorMessage) message);
		} else {
			throw new UnacceptableMessageException();
		}
	}

	@Override
	public void unhandled(Object message) {
		log.debug("unhandled message in " + getSelf().path().name() + ": " + message.toString());
		super.unhandled(message);
	}

	public abstract void receiveActorMessage(ActorMessage message);
}
