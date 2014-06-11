package io.akka.playground.actors.planning.pm;

import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.ProjectManagementResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;

public class ProjectManagementWorkerActor extends PlaygroundUntypedActor {

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		OnReceiveStrategies.crunchHeavyNumbers();
		getSender().tell(message.spawn(getSender().path().name(), new ProjectManagementResponsePayload()), getSelf());
	}
}