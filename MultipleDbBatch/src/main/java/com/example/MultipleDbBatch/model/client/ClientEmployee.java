package com.example.MultipleDbBatch.model.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_EMPLOYEE")
public class ClientEmployee implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer employeeId;
	
	private String employeeName;
	
	private String role;
	
	private Long phoneNumber;
	
	private Long salary = 0L;
	
	private String location;
	
	public ClientEmployee() {}
	
	public ClientEmployee(Integer id, Integer employeeId, String employeeName, String role, Long phoneNumber, Long salary,
			String location) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	@Override
	public String toString() {
		return "ClientEmployee [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", role="
				+ role + ", phoneNumber=" + phoneNumber + ", salary=" + salary + ", location=" + location + "]";
	}
}
