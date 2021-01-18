package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.ClientEmployee;
import com.example.MultipleDbBatch.model.mongo.EmployeeMongo;
import com.example.MultipleDbBatch.model.user.Employee;

/**
 * Intermediate processor to do the operations after the reading the data from the CSV file and
 * before writing the data into SQL.
 */
public class MongoItemProcessor implements ItemProcessor<Employee, EmployeeMongo>
{

	@Override
	public EmployeeMongo process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		EmployeeMongo employeeMongo=convert(employee);
		System.out.println("Converting (" + employee + ") into (" + employeeMongo + ")");
		return employeeMongo;
	}
	
	public EmployeeMongo convert(Employee employee) {
		EmployeeMongo employeeMongo = new EmployeeMongo();
		employeeMongo.setId(employee.getId());
		employeeMongo.setEmployeeId(employee.getEmployeeId());
		employeeMongo.setEmployeeName(employee.getEmployeeName());
		employeeMongo.setRole(employee.getRole());
		employeeMongo.setPhoneNumber(employee.getPhoneNumber());
		employeeMongo.setSalary(employee.getSalary());
		employeeMongo.setLocation(employee.getLocation());
		return employeeMongo;
	}
}
