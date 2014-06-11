package io.akka.playground.actors.planning.ba;

import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.BusinessAnalysisResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;

public class BusinessAnalysisWorkerActor extends PlaygroundUntypedActor {

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		
		OnReceiveStrategies.crunchHeavyNumbers();
		getSender().tell(message.spawn(getSender().path().name(), new BusinessAnalysisResponsePayload()), getSelf());
	}
}
