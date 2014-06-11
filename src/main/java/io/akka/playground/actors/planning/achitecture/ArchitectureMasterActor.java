package io.akka.playground.actors.planning.achitecture;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;
import io.akka.playground.actors.ActorFactory;
import io.akka.playground.actors.PlaygroundUntypedActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.ArchitectureRequestPayload;
import io.akka.playground.actors.receive.OnReceiveStrategies;
import io.akka.playground.constants.ActorSystemConstants;
import io.akka.playground.exceptions.PlaygroundActorException;
import scala.concurrent.duration.Duration;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;

public class ArchitectureMasterActor extends PlaygroundUntypedActor {

	private SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("5 second"),
			new Function<Throwable, Directive>() {
				@Override
				public Directive apply(Throwable t) {
					if (t instanceof ArithmeticException) {

						return resume();
					} else if (t instanceof PlaygroundActorException) {
						return restart();
					} else if (t instanceof IllegalArgumentException) {
						return stop();
					} else {
						return escalate();
					}
				}
			});
	
	public ArchitectureMasterActor() {
		initArchitecureWorker();
	}
	
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	private void initArchitecureWorker() {
		ActorFactory.initWorkerActor(getContext(), ArchitectureWorkerActor.class,
				ActorSystemConstants.ARCHITECTURE_WORKER, ActorSystemConstants.NUMBER_OF_ARCHITECTURE_WORKERS);

	}

	@Override
	public void receiveActorMessage(ActorMessage message) {
		log.debug("received message in " + getSelf().path().name());

		if (message.getPayload() instanceof ArchitectureRequestPayload) {
			OnReceiveStrategies.forwardToChildWorker(message, this, ActorSystemConstants.ARCHITECTURE_WORKER);
		} else {
			unhandled(message);
		}
	}

}
