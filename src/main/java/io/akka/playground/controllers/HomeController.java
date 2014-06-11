package io.akka.playground.controllers;

import io.akka.playground.actors.messages.ActorMessage;
import io.akka.playground.actors.messages.payload.EstimationRequestPayload;
import io.akka.playground.constants.ActorSystemConstants;
import io.akka.playground.services.ProcessingService;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@Autowired
	private ProcessingService processingService;

	@RequestMapping(value = "/{number}", method = RequestMethod.GET)
	public String home(@PathVariable String number, Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		sendDummyActorMessages(Integer.valueOf(number));

		return "home";
	}

	private void sendDummyActorMessages(int number) {
		ActorSystemConstants.TEST_NUMBER = number;
		ActorMessage estimationMessage = new ActorMessage(ActorSystemConstants.ESTIMATION_MASTER, new EstimationRequestPayload());

		for (int i = 0; i < number; i++) {
			processingService.process(estimationMessage);
		}

	}

}
