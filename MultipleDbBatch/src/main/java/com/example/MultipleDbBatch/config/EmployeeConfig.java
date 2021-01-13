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
import org.springframework.context.annotation.Primary;
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
		basePackages = {"com.example.MultipleDbBatch.repository.user"},
		entityManagerFactoryRef = "EmployeeEntityManagerFactory",
		transactionManagerRef = "EmployeeTransactionManager"
		)
public class EmployeeConfig {
	@Autowired 
	private Environment env;

	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.user.employee") 
	public DataSourceProperties employeeDataSourceProperties() { 
		return new DataSourceProperties(); 
	}

	@Primary
	@Bean(name = "EmployeeDataSource")
	public DataSource employeeDataSource() { 
		DataSourceProperties properties = employeeDataSourceProperties(); 
		return DataSourceBuilder.create()
				.driverClassName(properties.getDriverClassName())
				.url(properties.getUrl())
				.username(properties.getUsername())
				.password(properties.getPassword()) .build(); 
	}

	@Primary
	@Bean(name="EmployeeEntityManagerFactory") 
	public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(employeeDataSource()); 
		factory.setPackagesToScan("com.example.MultipleDbBatch.model.user");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPersistenceUnitName("Employee");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		factory.setJpaProperties(jpaProperties);

		return factory;

	}
	
	@Primary
	@Bean(name="EmployeeTransactionManager") 
	public PlatformTransactionManager employeeTransactionManager(@Qualifier("EmployeeEntityManagerFactory") EntityManagerFactory emf)
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean 
	public DataSourceInitializer employeeDataSourceInitializer() { 
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer(); 
		dataSourceInitializer.setDataSource(employeeDataSource());
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(); 
		databasePopulator.addScript(new	ClassPathResource("employee-data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("spring.user.employee.initialize", Boolean.class, false)); 
		return dataSourceInitializer; 
	}

}
