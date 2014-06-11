package io.akka.playground.actors.planning.achitecture;

import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.ArchitectureResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.exceptions.PlaygroundActorException;

public class ArchitectureWorkerActor extends PlaygroundUntypedActor {

	@Override
	public void postRestart(Throwable reason) throws Exception {
		super.postRestart(reason);

		if (reason instanceof PlaygroundActorException) {
			this.onReceive(((PlaygroundActorException) reason).getActorMessage());
		}
	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		OnReceiveStrategies.crunchHeavyNumbers();
		
		getSender().tell(message.spawn(getSender().path().name(), new ArchitectureResponsePayload()), getSelf());

		// throw new PlaygroundActorException("Something bad happend!",
		// message);

	}
}