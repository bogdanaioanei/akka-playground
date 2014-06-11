package io.akka.playground.actors;

import io.akka.playground.actors.estimation.EstimationMasterActor;
import io.akka.playground.actors.implementation.ImplementationMasterActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.EstimationRequestPayload;
import io.akka.playground.actors.messages.payload.EstimationResponsePayload;
import io.akka.playground.actors.messages.payload.ImplementationRequestPayload;
import io.akka.playground.actors.messages.payload.ImplementationResponsePayload;
import io.akka.playground.actors.messages.payload.PlanningRequestPayload;
import io.akka.playground.actors.messages.payload.PlanningResponsePayload;
import io.akka.playground.actors.planning.PlanningMasterActor;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class MasterActor extends PlaygroundUntypedActor {

	private static int counter = 0;
	private boolean started = false;

	private Date start = null;
	private Date finished = null;

	public MasterActor() {
		initEstimationMaster();
		initPlanningMaster();
		initImplementationMaster();
	}

	private void initEstimationMaster() {
		ActorFactory.initMasterActor(getContext(), EstimationMasterActor.class, ActorSystemConstants.ESTIMATION_MASTER);
	}

	private void initPlanningMaster() {
		ActorFactory.initMasterActor(getContext(), PlanningMasterActor.class, ActorSystemConstants.PLANNING_MASTER);
	}

	private void initImplementationMaster() {
		ActorFactory.initMasterActor(getContext(), ImplementationMasterActor.class,
				ActorSystemConstants.IMPLEMENTATION_MASTER);
	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		Object payload = message.getPayload();

		if (payload instanceof EstimationRequestPayload) {

			if (!started) {
				started = true;
				start = DateTime.now().toDate();

				log.info("Started at: " + start.toString());
			}
			OnReceiveStrategies.tellToChildMaster(message, this);

		} else if (payload instanceof EstimationResponsePayload) {
			log.debug("received result from estimation ...");
			ActorMessage plannigMessage = new ActorMessage(ActorSystemConstants.PLANNING_MASTER,
					new PlanningRequestPayload());

			OnReceiveStrategies.tellToChildMaster(plannigMessage, this);
		} else if (payload instanceof PlanningResponsePayload) {

			log.debug("received result from planning ...");

			ActorMessage implementationMessage = new ActorMessage(ActorSystemConstants.IMPLEMENTATION_MASTER,
					new ImplementationRequestPayload());

			OnReceiveStrategies.tellToChildMaster(implementationMessage, this);

		} else if (payload instanceof ImplementationResponsePayload) {
			counter++;

			if (counter % ActorSystemConstants.TEST_NUMBER == 0) {
				finished = DateTime.now().toDate();

				long startLong = start.getTime();
				long finishedLong = finished.getTime();

				// Period period = new Period(start, finished);
				log.info("received result from implementation ...counter: " + counter);
				log.info(Seconds.secondsBetween(new DateTime(start), new DateTime(finished)).getSeconds() + " seconds.");
				// log.info("Time elapsed: " + period.getSeconds());

				if (counter == ActorSystemConstants.TEST_NUMBER) {
					counter = 0;
					started = false;
				}
			}
		}
	}
}
