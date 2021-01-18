package com.example.MultipleDbBatch.config.batch;

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

import com.example.MultipleDbBatch.batch.EmployeeItemProcessorFour;
import com.example.MultipleDbBatch.batch.EmployeeItemProcessorOne;
import com.example.MultipleDbBatch.batch.EmployeeItemProcessorThree;
import com.example.MultipleDbBatch.batch.EmployeeItemProcessorTwo;
import com.example.MultipleDbBatch.model.client.ClientEmployeeFour;
import com.example.MultipleDbBatch.model.client.ClientEmployeeOne;
import com.example.MultipleDbBatch.model.client.ClientEmployeeThree;
import com.example.MultipleDbBatch.model.client.ClientEmployeeTwo;
import com.example.MultipleDbBatch.model.user.Employee;

@Configuration
@EnableBatchProcessing
public class MultiStepConfiguration
{
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("EmployeeEntityManagerFactory") 
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	@Qualifier("clientEmployeeTransactionManager")
	PlatformTransactionManager jpaTransactionManager;
	
	@Autowired
	@Qualifier("clientEmployeeEntityManagerFactory") 
	EntityManagerFactory clientEntityManagerFactory;

	@Bean
	public Job inserttoMultipleClient()
	{
		return jobBuilderFactory.get("inserttoMultipleClient")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.next(step2())
				.next(step3())
				.next(step4())
				.end()
				.build();
	}

	@Bean
	public Step step1()
	{	
		return stepBuilderFactory.get("step1")
				.transactionManager(jpaTransactionManager)
				.<Employee, ClientEmployeeOne>chunk(10)
				.reader(readerforMutiple())
				.processor(processorOne())
				.writer(writerOne())
				.build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.transactionManager(jpaTransactionManager)
				.<Employee, ClientEmployeeTwo>chunk(10)
				.reader(readerforMutiple())
				.processor(processorTwo())
				.writer(writerTwo())
				.build();
	}
	
	@Bean
	public Step step3()
	{	
		return stepBuilderFactory.get("step1")
				.transactionManager(jpaTransactionManager)
				.<Employee, ClientEmployeeThree>chunk(10)
				.reader(readerforMutiple())
				.processor(processorThree())
				.writer(writerThree())
				.build();
	}
	
	@Bean
	public Step step4() {
		return stepBuilderFactory.get("step2")
				.transactionManager(jpaTransactionManager)
				.<Employee, ClientEmployeeFour>chunk(10)
				.reader(readerforMutiple())
				.processor(processorFour())
				.writer(writerFour())
				.build();
	}

	@Bean
	public JpaCursorItemReader<Employee> readerforMutiple()
	{
		System.out.println("-----------Inside reader() method--------");
		JpaCursorItemReader<Employee> reader = new JpaCursorItemReader<Employee>();
		reader.setName("EmployeeReader");
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setQueryString("Select E from Employee E");
		reader.setMaxItemCount(100);
		return reader;
	}

	@Bean
	public EmployeeItemProcessorOne processorOne()
	{
		System.out.println("-----------Inside  processorOne() method--------");
		return new EmployeeItemProcessorOne();
	}
	
	@Bean
	public EmployeeItemProcessorTwo processorTwo()
	{
		System.out.println("-----------Inside  processorTwo() method--------");
		return new EmployeeItemProcessorTwo();
	}
	
	@Bean
	public EmployeeItemProcessorThree processorThree()
	{
		System.out.println("-----------Inside  processorThree() method--------");
		return new EmployeeItemProcessorThree();
	}
	
	@Bean
	public EmployeeItemProcessorFour processorFour()
	{
		System.out.println("-----------Inside  processorFour() method--------");
		return new EmployeeItemProcessorFour();
	}

	@Bean
	public JpaItemWriter<ClientEmployeeOne> writerOne()
	{
		System.out.println("-----------Inside writerOne() method--------");
		JpaItemWriter<ClientEmployeeOne> writer = new JpaItemWriter<ClientEmployeeOne>();
		writer.setEntityManagerFactory(clientEntityManagerFactory);
		return writer;
	}
	
	@Bean
	public JpaItemWriter<ClientEmployeeTwo> writerTwo()
	{
		System.out.println("-----------Inside writerTwo() method--------");
		JpaItemWriter<ClientEmployeeTwo> writer = new JpaItemWriter<ClientEmployeeTwo>();
		writer.setEntityManagerFactory(clientEntityManagerFactory);
		return writer;
	}
	
	@Bean
	public JpaItemWriter<ClientEmployeeThree> writerThree()
	{
		System.out.println("-----------Inside writerThree() method--------");
		JpaItemWriter<ClientEmployeeThree> writer = new JpaItemWriter<ClientEmployeeThree>();
		writer.setEntityManagerFactory(clientEntityManagerFactory);
		return writer;
	}
	
	@Bean
	public JpaItemWriter<ClientEmployeeFour> writerFour()
	{
		System.out.println("-----------Inside writerFour() method--------");
		JpaItemWriter<ClientEmployeeFour> writer = new JpaItemWriter<ClientEmployeeFour>();
		writer.setEntityManagerFactory(clientEntityManagerFactory);
		return writer;
	}

	
}
