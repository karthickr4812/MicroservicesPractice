package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.BaseClientEmployee;
import com.example.MultipleDbBatch.model.client.ClientEmployeeTwo;
import com.example.MultipleDbBatch.model.user.Employee;

public class EmployeeItemProcessorTwo implements ItemProcessor<Employee, ClientEmployeeTwo>
{

	@Override
	public ClientEmployeeTwo process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		BaseClientEmployee clientEmployee = new ClientEmployeeTwo();
		if(employee.getSalary() >= 60001 && employee.getSalary() <= 70000)
			clientEmployee=BaseClientEmployee.convert(employee,clientEmployee);
		else
			return null;
		System.out.println("Converting (" + employee + ") into (" + clientEmployee + ")");
		return (ClientEmployeeTwo) clientEmployee;
	}
}
