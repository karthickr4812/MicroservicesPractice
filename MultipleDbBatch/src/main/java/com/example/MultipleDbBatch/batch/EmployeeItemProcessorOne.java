package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.BaseClientEmployee;
import com.example.MultipleDbBatch.model.client.ClientEmployeeOne;
import com.example.MultipleDbBatch.model.user.Employee;

public class EmployeeItemProcessorOne implements ItemProcessor<Employee, ClientEmployeeOne>
{

	@Override
	public ClientEmployeeOne process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		BaseClientEmployee clientEmployee = new ClientEmployeeOne();
		if(employee.getSalary() >= 50000 && employee.getSalary() <= 60000)
			clientEmployee=BaseClientEmployee.convert(employee,clientEmployee);
		else
			return null;
		System.out.println("Converting (" + employee + ") into (" + clientEmployee + ")");
		return (ClientEmployeeOne) clientEmployee;
	}
}
