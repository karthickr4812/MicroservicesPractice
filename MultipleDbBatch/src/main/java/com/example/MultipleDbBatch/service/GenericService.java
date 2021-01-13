package com.example.MultipleDbBatch.service;

import com.example.MultipleDbBatch.model.client.ClientEmployeeThree;
import com.example.MultipleDbBatch.model.user.Employee;

public class GenericService {

	public ClientEmployeeThree convert(Employee employee, ClientEmployeeThree clientEmployee) {
		clientEmployee.setId(employee.getId());
		clientEmployee.setEmployeeId(employee.getEmployeeId());
		clientEmployee.setEmployeeName(employee.getEmployeeName());
		clientEmployee.setRole(employee.getRole());
		clientEmployee.setPhoneNumber(employee.getPhoneNumber());
		clientEmployee.setSalary(employee.getSalary());
		clientEmployee.setLocation(employee.getLocation());
		return clientEmployee;
	}
}
