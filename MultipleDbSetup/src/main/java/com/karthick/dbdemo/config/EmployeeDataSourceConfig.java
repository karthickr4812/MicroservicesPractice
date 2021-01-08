package com.karthick.dbdemo.config;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
		basePackages = {"com.karthick.dbdemo.repository.employee"},
		entityManagerFactoryRef = "employeeEntityManagerFactory",
		transactionManagerRef = "employeeTransactionManager"
		)
public class EmployeeDataSourceConfig {
	@Autowired 
	private Environment env;

	@Bean
	@ConfigurationProperties(prefix="spring.employee.datasource") 
	public DataSourceProperties employeeDataSourceProperties() { 
		return new DataSourceProperties(); 
	}

	@Bean(name = "employeeDataSource")
	public DataSource employeeDataSource() { 
		DataSourceProperties employeeDataSourceProperties = employeeDataSourceProperties(); 
		return DataSourceBuilder.create()
				.driverClassName(employeeDataSourceProperties.getDriverClassName())
				.url(employeeDataSourceProperties.getUrl())
				.username(employeeDataSourceProperties.getUsername())
				.password(employeeDataSourceProperties.getPassword()) .build(); 
	}

	@Bean(name="employeeEntityManagerFactory") 
	public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(employeeDataSource()); 
		factory.setPackagesToScan("com.karthick.dbdemo.model.employee");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPersistenceUnitName("Employee");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		factory.setJpaProperties(jpaProperties);

		return factory;

	}

	/*
	 * @Bean(name = "employeeDataSource")
	 * 
	 * @ConfigurationProperties(prefix = "spring.employee.datasource") public
	 * DataSource dataSource() { return DataSourceBuilder.create().build(); }
	 */


	/*
	 * @Bean(name = "employeeEntityManagerFactory") public
	 * LocalContainerEntityManagerFactoryBean
	 * employeeEntityManagerFactory(EntityManagerFactoryBuilder builder,
	 * 
	 * @Qualifier("employeeDataSource") DataSource dataSource) {
	 * 
	 * LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new
	 * LocalContainerEntityManagerFactoryBean();
	 * entityManagerFactoryBean.setDataSource(dataSource);
	 * entityManagerFactoryBean.setPackagesToScan("com.karthick.dbdemo.model");
	 * //additional config of factory
	 * 
	 * return entityManagerFactoryBean; HashMap<String, Object> properties = new
	 * HashMap<>(); properties.put("hibernate.hbm2ddl.auto", "update");
	 * properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	 * return builder.dataSource(dataSource).properties(properties)
	 * .packages("com.karthick.dbdemo.model").persistenceUnit("Employee").build(); }
	 */
	
	
	@Bean(name="employeeTransactionManager") 
	public PlatformTransactionManager employeeTransactionManager(EntityManagerFactory emf)
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
		dataSourceInitializer.setEnabled(env.getProperty("spring.employee.datasource.initialize", Boolean.class, false)); 
		return dataSourceInitializer; 
	}

}
