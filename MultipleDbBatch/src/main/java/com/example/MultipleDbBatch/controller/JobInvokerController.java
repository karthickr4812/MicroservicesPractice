package com.example.MultipleDbBatch.controller;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobInvokerController{

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("importEmployeeJob")
	Job importEmployeeJob;
	
	@Autowired
	@Qualifier("inserttoMultipleClient")
	Job inserttoMultipleClient;

	@GetMapping("/runBatch")
	@Scheduled(cron = "0 * 14 ? * *")
	public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException
	{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("source", "Spring Boot")
				.addParameter("time", new JobParameter(System.currentTimeMillis()))
				.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(importEmployeeJob, jobParameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());
        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
        return jobExecution.getStatus();
	}
	
	@GetMapping("/runBatchMultiple")
	@Scheduled(cron = "0 * 14 ? * *")
	public BatchStatus inserttoMultipleClient() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException
	{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("source", "Spring Boot")
				.addParameter("time", new JobParameter(System.currentTimeMillis()))
				.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(inserttoMultipleClient, jobParameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());
        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
        return jobExecution.getStatus();
	}
}
