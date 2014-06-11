package io.akka.playground.actors.planning.ba;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.BusinessAnalysisRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

public class BusinessAnalysisMasterActor extends PlaygroundUntypedActor {

	public BusinessAnalysisMasterActor() {
		initBusinessAnalysisWorker();
	}

	private void initBusinessAnalysisWorker() {
		ActorFactory
				.initWorkerActor(getContext(), BusinessAnalysisWorkerActor.class,
						ActorSystemConstants.BUSINESS_ANALYSIS_WORKER,
						ActorSystemConstants.NUMBER_OF_BUSINESS_ANALYSIS_WORKERS);

	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		if (message.getPayload() instanceof BusinessAnalysisRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.BUSINESS_ANALYSIS_WORKER);
		}
		else {
			unhandled(message);
		}

	}

}
