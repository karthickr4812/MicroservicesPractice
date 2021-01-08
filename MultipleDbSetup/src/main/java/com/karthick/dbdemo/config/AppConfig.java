package com.karthick.dbdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.karthick.dbdemo")
@Import({ MessagingConfiguration.class })
public class AppConfig {

}
