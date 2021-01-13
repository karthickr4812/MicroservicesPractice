package com.example.MultipleDbBatch.batch;

import org.springframework.batch.item.ItemProcessor;

import com.example.MultipleDbBatch.model.client.BaseClientEmployee;
import com.example.MultipleDbBatch.model.client.ClientEmployeeFour;
import com.example.MultipleDbBatch.model.user.Employee;

public class EmployeeItemProcessorFour implements ItemProcessor<Employee, ClientEmployeeFour>
{

	@Override
	public ClientEmployeeFour process(final Employee employee) throws Exception
	{
		System.out.println("-----------Inside process(final Employee employee) method--------");
		BaseClientEmployee clientEmployee = new ClientEmployeeFour();
		if(employee.getSalary() >= 80001)
			clientEmployee=BaseClientEmployee.convert(employee,clientEmployee);
		else
			return null;
		System.out.println("Converting (" + employee + ") into (" + clientEmployee + ")");
		return (ClientEmployeeFour) clientEmployee;
	}
}
