package io.akka.playground.actors.estimation;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.EstimationRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

public class EstimationMasterActor extends PlaygroundUntypedActor {

	public EstimationMasterActor() {
		initEstimationWorker();
	}

	private void initEstimationWorker() {
		ActorFactory.initWorkerActor(getContext(), EstimationWorkerActor.class, ActorSystemConstants.ESTIMATION_WORKER,
				ActorSystemConstants.NUMBER_OF_ESTIMATION_WORKERS);

	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		if (message.getPayload() instanceof EstimationRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.ESTIMATION_WORKER);
			
			//OnReceiveStrategies.tellToChildWorker(message, this, ActorSystemConstants.ESTIMATION_WORKER);
		}
		else {
			unhandled(message);
		}
//		else if (message.getPayload() instanceof EstimationResponsePayload) {
//			OnReceiveStrategies.tellToParent(message, this);
//		}
	}

}
