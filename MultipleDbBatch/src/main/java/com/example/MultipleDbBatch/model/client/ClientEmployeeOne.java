package com.example.MultipleDbBatch.model.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_EMPLOYEE_ONE")
public class ClientEmployeeOne extends BaseClientEmployee
{
	public ClientEmployeeOne() {}
	
	public ClientEmployeeOne(Integer id, Integer employeeId, String employeeName, String role, Long phoneNumber, Long salary,
			String location) {
		super(id, employeeId, employeeName, role, phoneNumber, salary, location);
	}

}
