package com.example.MultipleDbBatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer 
{
	
	@Bean
    public OpenEntityManagerInViewFilter clientOpenEntityManagerInViewFilter()
    {
    	OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
    	osivFilter.setEntityManagerFactoryBeanName("clientEmployeeEntityManagerFactory");
    	return osivFilter;
    }
	
	@Bean
    public OpenEntityManagerInViewFilter employeeOpenEntityManagerInViewFilter()
    {
    	OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
    	osivFilter.setEntityManagerFactoryBeanName("EmployeeEntityManagerFactory");
    	return osivFilter;
    }
}