package com.karthick.dbdemo.model.employee;

import java.util.List;


public class ProjectEntity 
{
	private String projectId;
	
	private List<Employee> projectEmployeeList;
	
	public ProjectEntity() {}

	public ProjectEntity(String projectId, List<Employee> projectEmployeeList) {
		super();
		this.projectId = projectId;
		this.projectEmployeeList = projectEmployeeList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<Employee> getProjectEmployeeList() {
		return projectEmployeeList;
	}

	public void setProjectEmployeeList(List<Employee> projectEmployeeList) {
		this.projectEmployeeList = projectEmployeeList;
	}

	@Override
	public String toString() {
		return "ProjectEntity [projectId=" + projectId + ", projectEmployeeList=" + projectEmployeeList + "]";
	}
	
}
