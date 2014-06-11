package io.akka.playground.actors.planning;

import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.ArchitectureRequestPayload;
import io.akka.playground.actors.messages.payload.ArchitectureResponsePayload;
import io.akka.playground.actors.messages.payload.BusinessAnalysisRequestPayload;
import io.akka.playground.actors.messages.payload.BusinessAnalysisResponsePayload;
import io.akka.playground.actors.messages.payload.PlanningRequestPayload;
import io.akka.playground.actors.messages.payload.PlanningResponsePayload;
import io.akka.playground.actors.messages.payload.ProjectManagementRequestPayload;
import io.akka.playground.actors.messages.payload.ProjectManagementResponsePayload;
import io.akka.playground.actors.planning.achitecture.ArchitectureMasterActor;
import io.akka.playground.actors.planning.ba.BusinessAnalysisMasterActor;
import io.akka.playground.actors.planning.pm.ProjectManagementMasterActor;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

import java.util.HashMap;
import java.util.Map;

public class PlanningMasterActor extends PlaygroundUntypedActor {

	/**
	 * Map used to keep track of how many responses are received for a given
	 * ActorMessage. The key is the ActorMessage ID and the value is the number
	 * of responses received.
	 * */
	private Map<String, Integer> messageResponses;

	private Map<String, ProjectManagementResponsePayload> pmResponsePayloads;
	private Map<String, BusinessAnalysisResponsePayload> baResponsePayloads;
	private Map<String, ArchitectureResponsePayload> architectureResponsePayloads;

	

	public PlanningMasterActor() {
		initProjectManagementMaster();
		initBusinessAnalysisMaster();
		initArchitectureMaster();

		messageResponses = new HashMap<>();
		pmResponsePayloads = new HashMap<>();
		baResponsePayloads = new HashMap<>();
		architectureResponsePayloads = new HashMap<>();
	}

	private void initProjectManagementMaster() {
		ActorFactory.initMasterActor(getContext(), ProjectManagementMasterActor.class,
				ActorSystemConstants.PROJECT_MANAGEMENT_MASTER);

	}

	private void initBusinessAnalysisMaster() {
		ActorFactory.initMasterActor(getContext(), BusinessAnalysisMasterActor.class,
				ActorSystemConstants.BUSINESS_ANALYSIS_MASTER);
	}

	private void initArchitectureMaster() {
		ActorFactory.initMasterActor(getContext(), ArchitectureMasterActor.class,
				ActorSystemConstants.ARCHITECTURE_MASTER);

	}

	@Override
	public void receiveActorMessage(ActorMessage message) {

		Object payload = message.getPayload();

		if (payload instanceof PlanningRequestPayload) {
			log.debug("received message in PLANNING_MASTER with id:" + message.getMessageId());
			/*
			 * spawn 3 ActorMessage instances to send to
			 * PROJECT_MANAGEMENT_MASTER, BUSINESS_ANALYSIS_MASTER and
			 * ARCHITECTURE_MASTER
			 */
			ActorMessage pmMessage = message.spawn(ActorSystemConstants.PROJECT_MANAGEMENT_MASTER,
					new ProjectManagementRequestPayload());

			ActorMessage baMessage = message.spawn(ActorSystemConstants.BUSINESS_ANALYSIS_MASTER,
					new BusinessAnalysisRequestPayload());

			ActorMessage architectureMessage = message.spawn(ActorSystemConstants.ARCHITECTURE_MASTER,
					new ArchitectureRequestPayload());

			// Add the messageId in the map and mark that it has no responses
			messageResponses.put(message.getMessageId(), 0);

			// send the smaller messages to the designated master actors
			OnReceiveStrategies.tellToChildMaster(pmMessage, this);
			OnReceiveStrategies.tellToChildMaster(baMessage, this);
			OnReceiveStrategies.tellToChildMaster(architectureMessage, this);
		} else if (payload instanceof ProjectManagementResponsePayload) {
			log.debug("received ProjectManagementResponsePayload in PLANNING_MASTER with id:" + message.getMessageId());
			pmResponsePayloads.put(message.getMessageId(), (ProjectManagementResponsePayload) payload);

			checkPayloadResponses(message);
		} else if (payload instanceof BusinessAnalysisResponsePayload) {
			log.debug("received BusinessAnalysisResponsePayload in PLANNING_MASTER with id:" + message.getMessageId());
			baResponsePayloads.put(message.getMessageId(), (BusinessAnalysisResponsePayload) payload);

			checkPayloadResponses(message);
		} else if (payload instanceof ArchitectureResponsePayload) {
			log.debug("received ArchitectureResponsePayload in PLANNING_MASTER with id:" + message.getMessageId());
			architectureResponsePayloads.put(message.getMessageId(), (ArchitectureResponsePayload) payload);

			checkPayloadResponses(message);
		}

	}

	private void checkPayloadResponses(ActorMessage message) {

		/*
		 * If a message response with a certain ID comes in we increment the
		 * number of response we have for that ID
		 */
		Integer responseCount = messageResponses.get(message.getMessageId());
		responseCount++;

		/*
		 * If we have 3 different response for that ID (meaning that a
		 * ProjectManagementResponsePayload, a BusinessAnalysisResponsePayload
		 * and a ArchitectureResponsePayload came in all with the same ID) we
		 * use the 3 responses to create a PlanningResponsePayload and send it
		 * to the parent
		 */
		if (responseCount == 3) {
			// clear all the values in the maps with that ID
			messageResponses.remove(message.getMessageId());
			ProjectManagementResponsePayload pmResponsePayload = pmResponsePayloads.remove(message.getMessageId());
			BusinessAnalysisResponsePayload baResponsePayload = baResponsePayloads.remove(message.getMessageId());
			ArchitectureResponsePayload parchitectureResponsePayload = architectureResponsePayloads.remove(message
					.getMessageId());

			// maybe do something with the payload responses and aggregate
			// them into the ProjectManagementResponsePayload

			OnReceiveStrategies.tellToParent(new ActorMessage(getContext().parent().path().name(),
					new PlanningResponsePayload()), this);
		} else {
			messageResponses.put(message.getMessageId(), responseCount);
		}
	}

}
