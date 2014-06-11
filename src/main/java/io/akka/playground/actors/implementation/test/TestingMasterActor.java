package io.akka.playground.actors.implementation.test;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.TestingRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

public class TestingMasterActor extends PlaygroundUntypedActor {

	public TestingMasterActor(){
		initTestingWorker();
	}
	
	private void initTestingWorker() {
		ActorFactory.initWorkerActor(getContext(), TestingWorkerActor.class,
				ActorSystemConstants.TESTING_WORKER, ActorSystemConstants.NUMBER_OF_TESTING_WORKERS);
		
	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		if (message.getPayload() instanceof TestingRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.TESTING_WORKER);
		} else {
			unhandled(message);
		}
	}
	

}
