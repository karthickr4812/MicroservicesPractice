package com.example.MultipleDbBatch.config;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages = {"com.example.MultipleDbBatch.repository.client"},
		entityManagerFactoryRef = "clientEmployeeEntityManagerFactory",
		transactionManagerRef = "clientEmployeeTransactionManager"
		)
public class ClientEmployeeConfig {
	@Autowired 
	private Environment env;

	@Bean
	@ConfigurationProperties(prefix="spring.client.employee") 
	public DataSourceProperties clientEmployeeDataSourceProperties() { 
		return new DataSourceProperties(); 
	}

	@Bean(name = "clientEmployeeDataSource")
	public DataSource clientEmployeeDataSource() { 
		DataSourceProperties properties = clientEmployeeDataSourceProperties(); 
		return DataSourceBuilder.create()
				.driverClassName(properties.getDriverClassName())
				.url(properties.getUrl())
				.username(properties.getUsername())
				.password(properties.getPassword()) .build(); 
	}

	@Bean(name="clientEmployeeEntityManagerFactory") 
	public LocalContainerEntityManagerFactoryBean clientEmployeeEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(clientEmployeeDataSource()); 
		factory.setPackagesToScan("com.example.MultipleDbBatch.model.client");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPersistenceUnitName("ClientEmployee");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		factory.setJpaProperties(jpaProperties);

		return factory;

	}
	
	@Bean(name="clientEmployeeTransactionManager") 
	public PlatformTransactionManager clientEmployeeTransactionManager(@Qualifier("clientEmployeeEntityManagerFactory") EntityManagerFactory emf)
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean 
	public DataSourceInitializer clientEmployeeDataSourceInitializer() { 
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer(); 
		dataSourceInitializer.setDataSource(clientEmployeeDataSource());
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(); 
		databasePopulator.addScript(new	ClassPathResource("client-employee-data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("spring.client.employee.initialize", Boolean.class, false)); 
		return dataSourceInitializer; 
	}

}
