package com.example.MultipleDbBatch.config.batch;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.MultipleDbBatch.batch.JobCompletionNotificationListener;
import com.example.MultipleDbBatch.batch.MongoItemProcessor;
import com.example.MultipleDbBatch.model.mongo.EmployeeMongo;
import com.example.MultipleDbBatch.model.user.Employee;

@Configuration
@EnableBatchProcessing
public class MongoBatchConfiguration
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
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Bean
	public Job importEmployeeMongoJob(JobCompletionNotificationListener listener)
	{
		return jobBuilderFactory.get("importEmployeeMongoJob").incrementer(new RunIdIncrementer())
				.listener(listener).flow(stepMongo()).end().build();
	}

	@Bean
	public Step stepMongo()
	{
		return stepBuilderFactory.get("stepMongo")
				.transactionManager(jpaTransactionManager)
				.<Employee, EmployeeMongo>chunk(10)
				.reader(readerMongo())
				.processor(processorMongo())
				.writer(writerMongo())
				.build();
	}
	
	@Bean
	public JpaCursorItemReader<Employee> readerMongo()
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
	public MongoItemProcessor processorMongo()
	{
		System.out.println("-----------Inside  processor() method--------");
		return new MongoItemProcessor();
	}

	/**
	 * The writer() method is used to write a data into the SQL.
	 */
	@Bean
	public MongoItemWriter<EmployeeMongo> writerMongo()
	{
		System.out.println("-----------Inside writer() method--------");
		MongoItemWriter<EmployeeMongo> writer = new MongoItemWriter<EmployeeMongo>();
		writer.setCollection("EmployeeMongo");
		writer.setTemplate(mongoTemplate);
		return writer;
	}

	
}
