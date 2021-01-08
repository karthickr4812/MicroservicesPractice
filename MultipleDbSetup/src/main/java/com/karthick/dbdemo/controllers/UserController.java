package com.karthick.dbdemo.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthick.dbdemo.model.user.Address;
import com.karthick.dbdemo.model.user.User;
import com.karthick.dbdemo.services.UserService;

@RestController
public class UserController
{
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public List<User> getUsers()
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.getUsers();
	}

	@GetMapping("/user/{userid}")
	public User getUser(@PathVariable("userid") int userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> user = userService.getUserbyId(userid);
		try {
			if(user.isPresent()) {
				return user.get();
			}else {
				throw new NullPointerException();
			}
		}
		catch(NullPointerException e){
			log.error("No User Found" + e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/address/{userid}")
	public Set<Address> getAddressbyUserId(@PathVariable("userid") int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.getAddressbyUserId(userId);
	}

	@PostMapping("/user")
	public User addUser(@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(obj,User.class);
		return userService.addUser(user);
	}

	@PutMapping("/user/{userid}")
	public User addorupdateUser(@PathVariable("userid") int userid,@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(obj,User.class);
		return userService.addorupdateUser(userid,user);
	}

	@DeleteMapping("/user/{userid}")
	public String deleteUser(@PathVariable("userid") int userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.deleteUser(userid);
	}

	@PostMapping("/addEmployeebyUser/{userid}")
	public String addEmployeebyUser(@PathVariable("userid") int userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.addEmployeebyUser(userid);
	}

	@PostMapping("/addEmployeebyCreateDate")
	public String addEmployeebyCreateDate(@RequestParam("startDate") String startDate,@RequestParam(value= "endDate", required=false, defaultValue = "") String endDate)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		LocalDate sDate = LocalDate.parse(startDate); 
		LocalDate eDate;
		if(endDate == null || endDate.equals("")) 
			eDate = sDate; 
		else 
			eDate = LocalDate.parse(endDate);
		log.debug("Method ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.addEmployeebyCreateDate(sDate, eDate);
	}

	@PostMapping("/addEmployeebyUserCountry/{country}")
	public String addEmployeebyUserCountry(@PathVariable("country") String country)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.addEmployeebyUserCountry(country);
	}
	
	@PutMapping("/updateUserandEmployee/{userid}")
	public User updateUserandEmployee(@PathVariable("userid") int userid,@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(obj,User.class);
		return userService.updateUserandEmployee(userid,user);
	}
	
	@DeleteMapping("/deleteUserandEmployee/{userid}")
	public String deleteUserandEmployee(@PathVariable("userid") int userid)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userService.deleteUserandEmployee(userid);
	}
	
	@Scheduled(cron = "0 0 10 ? * MON-FRI")
	public void scheduleJob()
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		LocalDate currentDate = LocalDate.now();
		LocalDate prevDate = currentDate.minusDays(1);
		addEmployeebyCreateDate(prevDate.toString(), LocalDate.now().toString());
	}

}
