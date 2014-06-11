package io.akka.playground.dto;

public class ProjectRfpDto {

	private String projectName;
	private String clientName;
	private Integer rfpDocumentSize;
	private Integer uncertainty;
	private Integer complexity;
	private Integer volume;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getRfpDocumentSize() {
		return rfpDocumentSize;
	}
	public void setRfpDocumentSize(Integer rfpDocumentSize) {
		this.rfpDocumentSize = rfpDocumentSize;
	}
	public Integer getUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(Integer uncertainty) {
		this.uncertainty = uncertainty;
	}
	public Integer getComplexity() {
		return complexity;
	}
	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
}
