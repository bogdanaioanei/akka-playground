package io.akka.playground.constants;

public final class ActorSystemConstants {

	
	public static int TEST_NUMBER = 200000;
	/*
	 * Master actor name
	 */
	public static final String MASTER_ACTOR = "CEO";
	
	/*
	 * Children of MASTER_ACTOR names
	 * */
	public static final String ESTIMATION_MASTER = "estimationMaster"; 
	public static final String PLANNING_MASTER = "planningMaster";
	public static final String IMPLEMENTATION_MASTER = "implementationMaster";
	
	/*
	 * Children of PLANNING_MASTER names
	 * */
	public static final String PROJECT_MANAGEMENT_MASTER = "projectManagementMaster";
	public static final String BUSINESS_ANALYSIS_MASTER = "businessAnalysisMaster";
	public static final String ARCHITECTURE_MASTER = "architectureMaster";
	
	/*
	 * Children of IMPLEMENTATION_MASTER names
	 * */
	public static final String DEVELOPMENT_MASTER = "developmentMaster";
	public static final String TESTING_MASTER = "testingMaster";
	
	/*
	 * Child of ESTIMATION_MASTER
	 * */
	public static final String ESTIMATION_WORKER = "estimationWorkerRouter";
	
	/*
	 * Child of PROJECT_MANAGEMENT_MASTER
	 * */
	public static final String PROJECT_MANAGEMENT_WORKER = "projectManagementWorkerRouter";
	
	/*
	 * Child of BUSINESS_ANALYSIS_MASTER
	 * */
	public static final String BUSINESS_ANALYSIS_WORKER = "businessAnalysisWorkerRouter";
	
	/*
	 * Child of ARCHITECTURE_MASTER
	 * */
	public static final String ARCHITECTURE_WORKER = "architectureWorkerRouter";
	
	/*
	 * Child of DEVELOPMENT_MASTER
	 * */
	public static final String DEVELOPMENT_WORKER = "developmentWorkerRouter";
	
	/*
	 * Child of TESTING_MASTER
	 * */
	public static final String TESTING_WORKER = "testingWorkerRouter";
	
	
	
	public static final int NUMBER_OF_ESTIMATION_WORKERS = 5;
	public static final int NUMBER_OF_PROJECT_MANAGEMENT_WORKERS = 5;
	public static final int NUMBER_OF_BUSINESS_ANALYSIS_WORKERS = 5;
	public static final int NUMBER_OF_ARCHITECTURE_WORKERS = 5;
	public static final int NUMBER_OF_DEVELOPMENT_WORKERS = 5;
	public static final int NUMBER_OF_TESTING_WORKERS = 5;
	
	private ActorSystemConstants() {
		
	}
}
