package com.example.MultipleDbBatch.batch;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.MultipleDbBatch.model.client.ClientEmployee;
import com.example.MultipleDbBatch.repository.client.ClientEmployeeRepository;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport
{
	@Autowired
	private final ClientEmployeeRepository clientEmployeeRepository;

	@Autowired
	public JobCompletionNotificationListener(ClientEmployeeRepository clientEmployeeRepository)
	{
		this.clientEmployeeRepository = clientEmployeeRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution)
	{
		if (jobExecution.getStatus() == BatchStatus.COMPLETED)
		{
			System.out.println(" -------- JOB FINISHED ------------------ ");
			List<ClientEmployee> employeeList = clientEmployeeRepository.findAll();
			System.out.println("Found <" + employeeList.size() + "> in the database.");
		}
	}
}
