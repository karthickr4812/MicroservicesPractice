package com.karthick.dbdemo.model.employee;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Table(name="PROJECT")
public class Project implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String projectCode;
	
	private String role;
	
	private int durationInMonth;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;
	
	private int currentProject;
	
	@Transient
	@JsonDeserialize
	@JsonSerialize
	private Integer empId;
	
	@ManyToOne
	@JoinColumn(name="employee_id")
	@JsonIgnore
	private Employee employee;

	public Project() {}
			
	public Project(Integer id, String projectCode, String role, int durationInMonth, LocalDate startDate,
			LocalDate endDate, int currentProject, Integer empId, Employee employee) {
		super();
		this.id = id;
		this.projectCode = projectCode;
		this.role = role;
		this.durationInMonth = durationInMonth;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentProject = currentProject;
		this.empId = empId;
		this.employee = employee;
	}

	public Project(Integer id, String projectCode, String role, int durationInMonth, LocalDate startDate,
			LocalDate endDate, int currentProject, Integer empId) {
		super();
		this.id = id;
		this.projectCode = projectCode;
		this.role = role;
		this.durationInMonth = durationInMonth;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentProject = currentProject;
		this.empId = empId;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getDurationInMonth() {
		return durationInMonth;
	}
	public void setDurationInMonth(int durationInMonth) {
		this.durationInMonth = durationInMonth;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(int currentProject) {
		this.currentProject = currentProject;
	}
	
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", projectCode=" + projectCode + ", role=" + role + ", durationInMonth="
				+ durationInMonth + ", startDate=" + startDate + ", endDate=" + endDate + ", currentProject="
				+ currentProject + ", empId=" + empId + ", employee=" + employee + "]";
	}

	
	
}
