package com.example.MultipleDbBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
		exclude = { DataSourceAutoConfiguration.class, 
					HibernateJpaAutoConfiguration.class,
					DataSourceTransactionManagerAutoConfiguration.class })
@ComponentScan({"com.example.MultipleDbBatch"})
@EntityScan({"com.example.MultipleDbBatch.model.user" , "com.example.MultipleDbBatch.model.client"})
@EnableTransactionManagement
@EnableScheduling
public class MultipleDbBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDbBatchApplication.class, args);
	}

}
