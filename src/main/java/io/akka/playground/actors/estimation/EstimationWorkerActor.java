package io.akka.playground.actors.estimation;

import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.EstimationResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;

public class EstimationWorkerActor extends PlaygroundUntypedActor {

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		OnReceiveStrategies.crunchHeavyNumbers();
		
		getSender().tell(new ActorMessage(getSender().path().name(), new EstimationResponsePayload()), getSelf());		
	}
}
