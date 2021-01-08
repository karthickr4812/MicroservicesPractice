package com.karthick.dbdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	
	@Bean
    public OpenEntityManagerInViewFilter userOpenEntityManagerInViewFilter()
    {
    	OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
    	osivFilter.setEntityManagerFactoryBeanName("userEntityManagerFactory");
    	return osivFilter;
    }
	
	@Bean
    public OpenEntityManagerInViewFilter employeeOpenEntityManagerInViewFilter()
    {
    	OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
    	osivFilter.setEntityManagerFactoryBeanName("employeeEntityManagerFactory");
    	return osivFilter;
    }
}