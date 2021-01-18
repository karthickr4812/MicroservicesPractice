package com.example.MultipleDbBatch.controller;

import java.util.Random;

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

import com.example.MultipleDbBatch.model.mongo.EmployeeMongo;
import com.example.MultipleDbBatch.repository.mongo.EmployeeMongoRepository;

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
	
	@Autowired
	@Qualifier("importEmployeeMongoJob")
	Job importEmployeeMongoJob;
	
	@Autowired
	private EmployeeMongoRepository employeeMongoRepository;

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
	@Scheduled(cron = "0 * 13 ? * *")
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
	
	
	
	@GetMapping("/runMongo")
	@Scheduled(cron = "0 * 14 ? * *")
	public String runBatchMongo() {
		EmployeeMongo employeeMongo=new  EmployeeMongo();
		Random rd= new Random();
		employeeMongo.setId(rd.nextInt(1000));
		employeeMongo.setEmployeeId(rd.nextInt(1000));
		employeeMongo.setEmployeeName("karthick");
		employeeMongoRepository.insert(employeeMongo);
				return "aaa";
	}
	
	@GetMapping("/runBatchMongo")
	@Scheduled(cron = "0 * 15 ? * *")
	public BatchStatus inserttoMongo() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException
	{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("source", "Spring Boot")
				.addParameter("time", new JobParameter(System.currentTimeMillis()))
				.toJobParameters();
        JobExecution jobExecution = jobLauncher.run(importEmployeeMongoJob, jobParameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());
        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
        return jobExecution.getStatus();
	}
}
	

