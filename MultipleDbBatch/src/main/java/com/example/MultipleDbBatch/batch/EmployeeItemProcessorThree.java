package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.BaseClientEmployee;
import com.example.MultipleDbBatch.model.client.ClientEmployeeThree;
import com.example.MultipleDbBatch.model.user.Employee;

public class EmployeeItemProcessorThree implements ItemProcessor<Employee, ClientEmployeeThree>
{

	@Override
	public ClientEmployeeThree process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		BaseClientEmployee clientEmployee = new ClientEmployeeThree();
		if(employee.getSalary() >= 70001 && employee.getSalary() <= 80000)
			clientEmployee=BaseClientEmployee.convert(employee,clientEmployee);
		else
			return null;
		System.out.println("Converting (" + employee + ") into (" + clientEmployee + ")");
		return (ClientEmployeeThree) clientEmployee;
	}
}
