package io.akka.playground.actors.planning.pm;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.ProjectManagementRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

public class ProjectManagementMasterActor extends PlaygroundUntypedActor {

	public ProjectManagementMasterActor() {
		initProjectManagementWorker();
	}

	private void initProjectManagementWorker() {
		ActorFactory.initWorkerActor(getContext(), ProjectManagementWorkerActor.class,
				ActorSystemConstants.PROJECT_MANAGEMENT_WORKER,
				ActorSystemConstants.NUMBER_OF_PROJECT_MANAGEMENT_WORKERS);

	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		if (message.getPayload() instanceof ProjectManagementRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.PROJECT_MANAGEMENT_WORKER);
		} else {
			unhandled(message);
		}
	}

}
