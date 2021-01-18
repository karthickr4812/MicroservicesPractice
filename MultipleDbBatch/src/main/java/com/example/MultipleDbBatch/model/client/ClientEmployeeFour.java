package com.example.MultipleDbBatch.model.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_EMPLOYEE_FOUR")
public class ClientEmployeeFour extends BaseClientEmployee
{
	public ClientEmployeeFour() {}
	
	public ClientEmployeeFour(Integer id, Integer employeeId, String employeeName, String role, Long phoneNumber, Long salary,
			String location) {
		super(id, employeeId, employeeName, role, phoneNumber, salary, location);
	}

}
