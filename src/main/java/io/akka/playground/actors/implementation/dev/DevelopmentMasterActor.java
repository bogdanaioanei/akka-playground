package io.akka.playground.actors.implementation.dev;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.DevelopmentRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

public class DevelopmentMasterActor extends PlaygroundUntypedActor {

	public DevelopmentMasterActor(){
		initDevelopmentWorker();
	}
	
	private void initDevelopmentWorker() {
		ActorFactory.initWorkerActor(getContext(), DevelopmentWorkerActor.class,
				ActorSystemConstants.DEVELOPMENT_WORKER, ActorSystemConstants.NUMBER_OF_DEVELOPMENT_WORKERS);
		
	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());
		
		if (message.getPayload() instanceof DevelopmentRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.DEVELOPMENT_WORKER);
		} else {
			unhandled(message);
		}
	}
}
