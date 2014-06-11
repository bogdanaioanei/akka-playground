package io.akka.playground.services.impl;

import io.akka.playground.actors.MasterActor;
import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.constants.ActorSystemConstants;
import io.akka.playground.services.ProcessingService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

@Service
public class ProcessingServiceImpl implements ProcessingService {

	private ActorSystem actorSystem;
	private ActorRef masterActor;
	
	@PostConstruct
	public void initActorSystem() {
		actorSystem = ActorSystem.create("AkkaPlaygroundActorSystem", ConfigFactory.load());
		
		masterActor = actorSystem.actorOf(Props.create(MasterActor.class), ActorSystemConstants.MASTER_ACTOR);
	}

	@PreDestroy
	public void shutdownActorSystem() {
		actorSystem.shutdown();
	}

	@Override
	public void process(ActorMessage message) {
		masterActor.tell(message, ActorRef.noSender());
	}

}
