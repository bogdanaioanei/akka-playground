package io.akka.playground.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import akka.routing.DefaultResizer;
import akka.routing.RoundRobinPool;

public final class ActorFactory {

	private ActorFactory() {

	}

	public static ActorRef initMasterActor(UntypedActorContext context, Class<?> clazz, String name) {
		return context.actorOf(Props.create(clazz), name);
	}

	public static ActorRef initWorkerActor(UntypedActorContext context, Class<?> clazz, String name, int nrWorkers) {
		DefaultResizer resizer = new DefaultResizer(nrWorkers, nrWorkers * 10);
		return context.actorOf(new RoundRobinPool(nrWorkers).withResizer(resizer).props(Props.create(clazz)), name);
	}

}
