package io.akka.playground.actors.implementation.test;

import java.util.Random;

import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.TestingResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;

public class TestingWorkerActor extends PlaygroundUntypedActor {

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		OnReceiveStrategies.crunchHeavyNumbers();
		
		getSender().tell(message.spawn(getSender().path().name(), new TestingResponsePayload(getRandomOutcome())),
				getSelf());

	}

	private String getRandomOutcome() {
		Random rand = new Random();
		int randomInt = rand.nextInt(10);

		if (randomInt % 2 == 0) {
			return TestingResponsePayload.POSITIVE_OUTCOME;
		} else {
			return TestingResponsePayload.NEGATIVE_OUTCOME;
		}
	}
}