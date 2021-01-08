package com.karthick.dbdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
		exclude = { DataSourceAutoConfiguration.class, 
					HibernateJpaAutoConfiguration.class,
					DataSourceTransactionManagerAutoConfiguration.class })
@ComponentScan({"com.karthick.dbdemo"})
@EntityScan({"com.karthick.dbdemo.model.user" , "com.karthick.dbdemo.model.employee","com.karthick.dbdemo.model.UserEntity"})
@EnableTransactionManagement
@EnableScheduling
//@EnableEurekaClient
public class MultipleDbSetupApplication {
	
	private static final Logger log = LoggerFactory.getLogger(MultipleDbSetupApplication.class);

	public static void main(String[] args) {
		log.info("Main method starts");
		SpringApplication.run(MultipleDbSetupApplication.class, args);
		log.info("Main method ends");
	}

}
