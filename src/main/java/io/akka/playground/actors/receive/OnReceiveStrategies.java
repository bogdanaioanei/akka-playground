package io.akka.playground.actors.receive;

import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.constants.ActorSystemConstants;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public final class OnReceiveStrategies {

	private OnReceiveStrategies() {
	}

	public static void tellToChildMaster(ActorMessage message, UntypedActor actor) {

		// get the child master actor that the message is destined to
		ActorRef childActor = actor.getContext().getChild(message.getDestinedActorName());

		if (childActor != null) {
			childActor.tell(message, actor.getSelf());
		} else {
			actor.unhandled(message);
		}
	}

	public static void tellToChildWorker(ActorMessage message, UntypedActor actor, String workerName) {
		actor.getContext().getChild(workerName).tell(message, actor.getSelf());
	}

	public static void forwardToChildWorker(ActorMessage message, UntypedActor actor, String workerName) {
		actor.getContext().getChild(workerName).forward(message, actor.getContext());
	}

	public static void tellToParent(ActorMessage message, UntypedActor actor) {
		actor.getContext().parent().tell(message, actor.getSelf());
	}

	public static void crunchHeavyNumbers() {
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 100000; j++) {
				int result = Math.abs(i * 10 - 100000) * Math.abs(2352353 / 8);
				result = (result * 2) / 3;
			}
		}
	}
}
