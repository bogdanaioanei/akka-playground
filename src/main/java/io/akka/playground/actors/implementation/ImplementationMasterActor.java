package io.akka.playground.actors.implementation;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.implementation.dev.DevelopmentMasterActor;
import io.akka.playground.actors.implementation.test.TestingMasterActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.DevelopmentRequestPayload;
import io.akka.playground.actors.messages.payload.DevelopmentResponsePayload;
import io.akka.playground.actors.messages.payload.ImplementationRequestPayload;
import io.akka.playground.actors.messages.payload.ImplementationResponsePayload;
import io.akka.playground.actors.messages.payload.TestingRequestPayload;
import io.akka.playground.actors.messages.payload.TestingResponsePayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

import java.util.HashMap;
import java.util.Map;

public class ImplementationMasterActor extends PlaygroundUntypedActor {

	/*
	 * Using this map to keep the received DevelopmentRequestPayload in case we
	 * need to resend them
	 */
	private Map<String, DevelopmentRequestPayload> devRequestPayloads;

	public ImplementationMasterActor() {
		initDevelopmentMaster();
		initTestingMaster();

		devRequestPayloads = new HashMap<>();
	}

	private void initDevelopmentMaster() {
		ActorFactory.initMasterActor(getContext(), DevelopmentMasterActor.class,
				ActorSystemConstants.DEVELOPMENT_MASTER);

	}

	private void initTestingMaster() {
		ActorFactory.initMasterActor(getContext(), TestingMasterActor.class, ActorSystemConstants.TESTING_MASTER);
	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		Object payload = message.getPayload();

		if (payload instanceof ImplementationRequestPayload) {

			ActorMessage devMessage = message.spawn(ActorSystemConstants.DEVELOPMENT_MASTER,
					new DevelopmentRequestPayload());

			devRequestPayloads.put(devMessage.getMessageId(), (DevelopmentRequestPayload) devMessage.getPayload());

			OnReceiveStrategies.tellToChildMaster(devMessage, this);

		} else if (payload instanceof DevelopmentResponsePayload) {

			ActorMessage testMessage = message.spawn(ActorSystemConstants.TESTING_MASTER, new TestingRequestPayload());
			OnReceiveStrategies.tellToChildMaster(testMessage, this);

		} else if (payload instanceof TestingResponsePayload) {

			TestingResponsePayload testRespPayload = (TestingResponsePayload) payload;

			if (testRespPayload.getTestOutcome().equals(TestingResponsePayload.POSITIVE_OUTCOME)) {
				// development task done
				log.debug("we have a " + TestingResponsePayload.POSITIVE_OUTCOME);

				devRequestPayloads.remove(message.getMessageId());

				OnReceiveStrategies.tellToParent(
						message.spawn(getContext().parent().path().name(), new ImplementationResponsePayload()), this);

			} else if (testRespPayload.getTestOutcome().equals(TestingResponsePayload.NEGATIVE_OUTCOME)) {
				// send back to development
				log.debug("we have a " + TestingResponsePayload.NEGATIVE_OUTCOME);

				DevelopmentRequestPayload devPayload = devRequestPayloads.get(message.getMessageId());
				ActorMessage resentDevMessage = message.spawn(ActorSystemConstants.DEVELOPMENT_MASTER, devPayload);

				OnReceiveStrategies.tellToChildMaster(resentDevMessage, this);
			}
		}
	}
}
