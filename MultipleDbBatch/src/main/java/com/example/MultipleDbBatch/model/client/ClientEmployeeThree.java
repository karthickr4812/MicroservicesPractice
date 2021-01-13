package com.example.MultipleDbBatch.model.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_EMPLOYEE_THREE")
public class ClientEmployeeThree extends BaseClientEmployee
{
	public ClientEmployeeThree() {}
	
	public ClientEmployeeThree(Integer id, Integer employeeId, String employeeName, String role, Long phoneNumber, Long salary,
			String location) {
		super(id, employeeId, employeeName, role, phoneNumber, salary, location);
	}

}
