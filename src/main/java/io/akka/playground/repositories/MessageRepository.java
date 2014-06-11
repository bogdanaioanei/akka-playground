package io.akka.playground.repositories;

import java.util.List;

import io.akka.playground.entities.Message;
import io.akka.playground.enums.MessageStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByMessageStatus(MessageStatus messageStatus);
}
