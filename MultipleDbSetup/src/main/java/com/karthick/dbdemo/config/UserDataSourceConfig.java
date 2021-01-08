package com.karthick.dbdemo.config;

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
		basePackages = {"com.karthick.dbdemo.repository.user","com.karthick.dbdemo.repository.UserEntity"},
		entityManagerFactoryRef = "userEntityManagerFactory",
		transactionManagerRef = "userTransactionManager"
		)
public class UserDataSourceConfig
{
	@Autowired 
	private Environment env;

	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.user.datasource") 
	public DataSourceProperties userDataSourceProperties() { 
		return new DataSourceProperties(); 
	}

	@Primary
	@Bean public DataSource userDataSource() { 
		DataSourceProperties userDataSourceProperties = userDataSourceProperties(); 
		return DataSourceBuilder.create()
				.driverClassName(userDataSourceProperties.getDriverClassName())
				.url(userDataSourceProperties.getUrl())
				.username(userDataSourceProperties.getUsername())
				.password(userDataSourceProperties.getPassword()) 
				.build(); 
	}


	/*
	 * @Primary
	 * 
	 * @Bean(name = "userDataSource")
	 * 
	 * @ConfigurationProperties(prefix = "spring.user.datasource") public DataSource
	 * dataSource() { return DataSourceBuilder.create().build(); }
	 */

	@Primary

	@Bean(name = "userEntityManagerFactory") 
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(userDataSource()); 
		factory.setPackagesToScan("com.karthick.dbdemo.model.user","com.karthick.dbdemo.model.UserEntity"); 
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		//factory.setPersistenceUnitName("User");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		factory.setJpaProperties(jpaProperties);

		return factory; 
	}
	
	@Primary
	@Bean(name="userTransactionManager") 
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory") EntityManagerFactory emfUser)
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emfUser);
		return transactionManager;
	}


	/*
	 * @Primary
	 * 
	 * @Bean(name = "userEntityManagerFactory") public
	 * LocalContainerEntityManagerFactoryBean
	 * bookEntityManagerFactory(EntityManagerFactoryBuilder builder,
	 * 
	 * @Qualifier("userDataSource") DataSource dataSource) { HashMap<String, Object>
	 * properties = new HashMap<>(); properties.put("hibernate.hbm2ddl.auto",
	 * "update"); properties.put("hibernate.dialect",
	 * "org.hibernate.dialect.H2Dialect"); return
	 * builder.dataSource(dataSource).properties(properties)
	 * .packages("com.karthick.dbdemo.model.user").persistenceUnit("User").build(); }
	 */

	
	
	
	
	/*
	
	
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
	public EntityManagerFactory employeeEntityManagerFactory(@Qualifier("employeeDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource); 
		factory.setPackagesToScan(new String[]{"com.karthick.dbdemo.model.employee"});
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPersistenceUnitName("Employee");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		factory.setJpaProperties(jpaProperties);

		return factory.getObject();

	}
	
	@Bean(name="employeeTransactionManager") 
	public PlatformTransactionManager employeeTransactionManager(@Qualifier("employeeEntityManagerFactory") EntityManagerFactory emfEmployee)
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emfEmployee);
		return transactionManager;
	}
	 */

	@Bean 
	public DataSourceInitializer userDataSourceInitializer()
	{ 
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(userDataSource()); 
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("user-data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("spring.user.datasource.initialize", Boolean.class, false)); 
		return dataSourceInitializer; 
	}
}

