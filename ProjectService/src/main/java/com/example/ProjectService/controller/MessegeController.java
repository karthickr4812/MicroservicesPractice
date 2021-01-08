package com.example.ProjectService.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjectService.model.employee.ProjectModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MessegeController {
		

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	MessageConverter converter;
	
	private static final String MESSAGE_QUEUE_1 = "message_queue_1";
	private static final String MESSAGE_QUEUE_2 = "message_queue_2";
	
	@RequestMapping("/insertMsgProject")
	public String createProducts(@RequestBody Object obj) {
		jmsTemplate.convertAndSend(MESSAGE_QUEUE_1, obj);
		System.out.println("Message has been sent successfully to Queue");
		return "Message has been sent successfully to Queue";
	}
	
	@RequestMapping("/sendandReceiveProject")
	public String insertProject(@RequestBody Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectModel project = mapper.convertValue(obj,ProjectModel.class);
		jmsTemplate.setReceiveTimeout(60000);
		Message message=jmsTemplate.sendAndReceive(MESSAGE_QUEUE_2, new MessageCreator()
		{
			public javax.jms.Message createMessage(Session session) throws JMSException
			{ 	
				 Message messege = converter.toMessage(project, session);
				 messege.setJMSReplyTo(new ActiveMQQueue("responseQ"));
				 return messege;
				//ObjectMessage objectMessage = session.createObjectMessage(project);
				//return objectMessage;
			}
		});
		System.out.println("Message has been sent successfully to Queue");
		System.out.println("Reply----------" + message.toString());
		Object obj2 = null;
		try {
			obj2 = converter.fromMessage(message);
		} catch (MessageConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reply text----------" + obj2.toString());
		return obj2.toString();
	}

}
