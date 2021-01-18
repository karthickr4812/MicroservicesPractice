package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.ClientEmployee;
import com.example.MultipleDbBatch.model.user.Employee;

/**
 * Intermediate processor to do the operations after the reading the data from the CSV file and
 * before writing the data into SQL.
 */
public class EmployeeItemProcessor implements ItemProcessor<Employee, ClientEmployee>
{

	@Override
	public ClientEmployee process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		ClientEmployee clientEmployee=convert(employee);
		System.out.println("Converting (" + employee + ") into (" + clientEmployee + ")");
		return clientEmployee;
	}
	
	public ClientEmployee convert(Employee employee) {
		ClientEmployee clientEmployee = new ClientEmployee();
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
