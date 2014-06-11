package io.akka.playground.actors.messages.payload;

public class TestingResponsePayload {

	public static String POSITIVE_OUTCOME = "positiveOutcome";
	public static String NEGATIVE_OUTCOME = "negativeOutcome";

	private String testOutcome;

	public TestingResponsePayload(String outcome) {
		this.testOutcome = outcome;
	}

	public String getTestOutcome() {
		return testOutcome;
	}

	public void setTestOutcome(String testOutcome) {
		this.testOutcome = testOutcome;
	}
}
