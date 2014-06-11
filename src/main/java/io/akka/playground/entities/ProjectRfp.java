package io.akka.playground.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project_rfps")
public class ProjectRfp {

	@Id
	@GeneratedValue
	private Long id;
	
	private String projectName;
	private String clientName;
	private Integer rfpDocumentSize;
	private Integer uncertainty;
	private Integer complexity;
	private Integer volume;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
