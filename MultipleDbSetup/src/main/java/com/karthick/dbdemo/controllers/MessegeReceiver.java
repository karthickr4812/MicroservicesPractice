package com.karthick.dbdemo.controllers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthick.dbdemo.model.employee.Project;
import com.karthick.dbdemo.services.EmployeeService;

@Component
public class MessegeReceiver {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	MessageConverter converter;

	private static final String MESSAGE_QUEUE_1 = "message_queue_1";
	private static final String MESSAGE_QUEUE_2 = "message_queue_2";

	
	@JmsListener(destination = MESSAGE_QUEUE_1)
	public void receiveMessage(final Message message)
			throws JMSException
	{
		Object obj = converter.fromMessage(message);
		System.out.println("obj = " + obj);
		//MessageHeaders headers = message.getHeaders();
		//System.out.println("headers = " + headers);
		//Object obj = message.getPayload();
		ObjectMapper mapper = new ObjectMapper();
		Project project = mapper.convertValue(obj,Project.class);
		System.out.println("Project = " + project);
		System.out.println(employeeService.addProjectforEmployee(project));
	}

	
	@JmsListener(destination = MESSAGE_QUEUE_2)
	public void receiveMessage1(final Message message)
			throws JMSException
	{
		Object obj = converter.fromMessage(message);
		System.out.println("obj = " + obj);
		ObjectMapper mapper = new ObjectMapper();
		Project project = mapper.convertValue(obj,Project.class);
		System.out.println("Project = " + project);
		String result = employeeService.addProjectforEmployee(project);
		jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator()
		{
			public javax.jms.Message createMessage(Session session) throws JMSException
			{ 	
				 Message messege = converter.toMessage(result, session);
				 return messege;
			}
		});
	}
}
