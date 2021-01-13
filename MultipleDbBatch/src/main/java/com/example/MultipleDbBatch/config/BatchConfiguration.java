package com.example.MultipleDbBatch.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.MultipleDbBatch.batch.EmployeeItemProcessor;
import com.example.MultipleDbBatch.batch.JobCompletionNotificationListener;
import com.example.MultipleDbBatch.model.client.ClientEmployee;
import com.example.MultipleDbBatch.model.user.Employee;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration
{
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("EmployeeEntityManagerFactory") 
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	@Qualifier("clientEmployeeEntityManagerFactory") 
	EntityManagerFactory clientEntityManagerFactory;
	
	@Autowired
	@Qualifier("clientEmployeeTransactionManager")
	PlatformTransactionManager jpaTransactionManager;

	@Bean
	public Job importEmployeeJob(JobCompletionNotificationListener listener)
	{
		return jobBuilderFactory.get("importEmployeeJob").incrementer(new RunIdIncrementer())
				.listener(listener).flow(step()).end().build();
	}

	@Bean
	public Step step()
	{
		return stepBuilderFactory.get("step1")
				.transactionManager(jpaTransactionManager)
				.<Employee, ClientEmployee>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public JpaCursorItemReader<Employee> reader()
	{
		System.out.println("-----------Inside reader() method--------");
		JpaCursorItemReader<Employee> reader = new JpaCursorItemReader<Employee>();
		reader.setName("EmployeeReader");
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("Select E from Employee E");
		reader.setMaxItemCount(100);
		return reader;
	}

	/**
	 * Intermediate processor to do the operations after the reading the data from the CSV file and
	 * before writing the data into SQL.
	 */
	@Bean
	public EmployeeItemProcessor processor()
	{
		System.out.println("-----------Inside  processor() method--------");
		return new EmployeeItemProcessor();
	}

	/**
	 * The writer() method is used to write a data into the SQL.
	 */
	@Bean
	public JpaItemWriter<ClientEmployee> writer()
	{
		System.out.println("-----------Inside writer() method--------");
		JpaItemWriter<ClientEmployee> writer = new JpaItemWriter<ClientEmployee>();
		writer.setEntityManagerFactory(clientEntityManagerFactory);
		return writer;
	}

	
}
