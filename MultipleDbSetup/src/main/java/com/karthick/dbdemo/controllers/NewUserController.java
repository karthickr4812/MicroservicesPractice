package com.karthick.dbdemo.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.karthick.dbdemo.model.UserEntity.UserTableOne;
import com.karthick.dbdemo.model.UserEntity.UserTableThree;
import com.karthick.dbdemo.model.UserEntity.UserTableTwo;
import com.karthick.dbdemo.repository.UserEntity.UserOneRepository;
import com.karthick.dbdemo.repository.UserEntity.UserThreeRepository;
import com.karthick.dbdemo.repository.UserEntity.UserTwoRepository;

@RestController
@Transactional(transactionManager="userTransactionManager")
public class NewUserController
{   
	private static final Logger log = LoggerFactory.getLogger(NewUserController.class);
	
	/*
	@Autowired
	private GenericUserService<UserTableOne,Integer> userOneService;

	@Autowired 
	private GenericUserService<UserTableTwo,Integer> userTwoService;
	
	@Autowired 
	private GenericUserService<UserTableThree,Integer> userThreeService;  */
	
	
	@Autowired
	private UserOneRepository UserOneRepository;
	
	@Autowired
	private UserTwoRepository UserTwoRepository;
	
	@Autowired
	private UserThreeRepository UserThreeRepository;

	@GetMapping("/user1/{userid}")
	public Optional<UserTableOne> getUser1(@PathVariable("userid") int userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return UserOneRepository.findById(userid);
	}

	@GetMapping("/user2/{userid}")
	public Optional<UserTableTwo> getUser2(@PathVariable("userid") String userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return UserTwoRepository.findById(Long.valueOf(userid));
	}


	@GetMapping("/user3/{userid}")
	public Optional<UserTableThree> getUser3(@PathVariable("userid") String userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return UserThreeRepository.findById(userid);
	}
}
