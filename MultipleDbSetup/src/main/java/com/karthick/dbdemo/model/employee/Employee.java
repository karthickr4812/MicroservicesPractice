package com.karthick.dbdemo.model.employee;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Table(name="EMPLOYEE")
public class Employee implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer userId;
	
	private String employeeName;
	
	private String employeeEmail;
	
	private Long phoneNumber;
	
	private Long salary = 1000L;
	
	private String location;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate joiningDate;
	
	@OneToMany(mappedBy = "employee",fetch = FetchType.EAGER)
	private List<Project> project = new ArrayList<>();
	
	public Employee() {}
	
	public Employee(Integer id,Integer userId, String employeeName, String employeeEmail, Long phoneNumber, Long salary, String location,
			LocalDate joiningDate, List<Project> project) {
		super();
		this.id = id;
		this.userId = userId;
		this.employeeName = employeeName;
		this.employeeEmail = employeeEmail;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.location = location;
		this.joiningDate = joiningDate;
		this.project = project;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public List<Project> getProject() {
		return project;
	}
	public void setProject(List<Project> project) {
		this.project = project;
	}
	public Long getSalary() {
		return salary;
	}
	public void setSalary(Long salary) {
		this.salary = salary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userId=" + userId + ", employeeName=" + employeeName + ", employeeEmail="
				+ employeeEmail + ", phoneNumber=" + phoneNumber + ", salary=" + salary + ", location=" + location
				+ ", joiningDate=" + joiningDate + ", project=" + project + "]";
	}
}
