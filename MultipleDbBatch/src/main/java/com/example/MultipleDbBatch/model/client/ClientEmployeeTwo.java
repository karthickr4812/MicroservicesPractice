package com.example.MultipleDbBatch.model.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_EMPLOYEE_TWO")
public class ClientEmployeeTwo extends BaseClientEmployee
{
	public ClientEmployeeTwo() {}
	
	public ClientEmployeeTwo(Integer id, Integer employeeId, String employeeName, String role, Long phoneNumber, Long salary,
			String location) {
		super(id, employeeId, employeeName, role, phoneNumber, salary, location);
	}

}
