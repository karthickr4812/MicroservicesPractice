package com.karthick.dbdemo.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthick.dbdemo.model.employee.Employee;
import com.karthick.dbdemo.model.employee.Project;
import com.karthick.dbdemo.model.employee.ProjectEntity;
import com.karthick.dbdemo.services.EmployeeService;

@RestController
public class EmployeeController
{
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
		
	@GetMapping("/employee")
	public List<Employee> getEmployees()
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeService.getEmployees();
	}
	
	@GetMapping("/employee/{employeeid}")
	public Employee getEmployee(@PathVariable("employeeid") int employeeId)
	{ 	
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Employee> employeeEntity = employeeService.getEmployeebyId(employeeId);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeEntity.orElse(new Employee());
	}
	
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.convertValue(obj,Employee.class);
		return employeeService.addEmployee(employee);
	}
	
	@PutMapping("/employee/{employeeid}")
	public Employee addorUpdateEmployee(@PathVariable("employeeid") int employeeId,@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.convertValue(obj,Employee.class);
		return employeeService.addorUpdateEmployee(employeeId,employee);
	}
	
	@DeleteMapping("/employee/{employeeid}")
	public String deleteEmployee(@PathVariable("employeeid") int employee)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeService.deleteEmployee(employee);
	}
	
	@GetMapping("/getEmployeeforProject/{projectId}")
	public ProjectEntity getEmployeeforProject(@PathVariable("projectId") String projectId)
	{ 	
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Employee> employeeEntity = employeeService.getEmployeeforProject(projectId);
		ProjectEntity pj=new ProjectEntity();
		pj.setProjectId(projectId);
		pj.setProjectEmployeeList(employeeEntity);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return pj;
	}
	
	@PostMapping("/addProject")
	public String addProject(@RequestBody Object obj)
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		ObjectMapper mapper = new ObjectMapper();
		Project project = mapper.convertValue(obj,Project.class);
		return employeeService.addProjectforEmployee(project);
	}
	
}
