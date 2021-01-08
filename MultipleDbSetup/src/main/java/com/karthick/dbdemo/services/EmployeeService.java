package com.karthick.dbdemo.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.karthick.dbdemo.model.employee.Employee;
import com.karthick.dbdemo.model.employee.Project;
import com.karthick.dbdemo.model.user.Address;
import com.karthick.dbdemo.model.user.User;
import com.karthick.dbdemo.repository.employee.EmployeeRepository;
import com.karthick.dbdemo.repository.employee.ProjectRepository;

@Service
@Transactional(transactionManager="employeeTransactionManager")
public class EmployeeService {

	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/*
	 * public void setEmployeeRepository(EmployeeRepository employeeRepository) {
	 * this.employeeRepository = employeeRepository; }
	 */
	
	@Autowired
	private ProjectRepository projectRepository;


	@Transactional(transactionManager="employeeTransactionManager")
	public List<Employee> getEmployees()
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.findAll();
	}

	public Optional<Employee> getEmployeebyId(int employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.findById(employee);
	}

	@Transactional(transactionManager="employeeTransactionManager",readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public Employee addEmployee(Employee employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Employee employee1=new Employee();
		loadEmployee(employee, employee1);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.save(employee1);
	}

	private void loadEmployee(Employee employee, Employee employee1) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Project> projectList = employee.getProject();
		for(Project prj:projectList) {
			Project prj1=new Project();
			prj1.setProjectCode(prj.getProjectCode());
			prj1.setRole(prj.getRole());
			prj1.setDurationInMonth(prj.getDurationInMonth());
			prj1.setStartDate(prj.getStartDate());
			prj1.setEndDate(prj.getEndDate());
			prj1.setCurrentProject(prj.getCurrentProject());
			prj1.setEmployee(employee1);
			employee1.getProject().add(prj1);
			projectRepository.save(prj1);
		}
		employee1.setEmployeeName(employee.getEmployeeName());
		employee1.setEmployeeEmail(employee.getEmployeeEmail());
		employee1.setPhoneNumber(employee.getPhoneNumber());
		employee1.setLocation(employee.getLocation());
		employee1.setSalary(employee.getSalary());
		employee1.setJoiningDate(employee.getJoiningDate());
		employee1.setUserId(employee.getUserId());
		employee1.setProject(projectList);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/*
	 * private void createProjectEntity(Employee employee, Employee employee1) {
	 * List<Project> projectList = new CopyOnWriteArrayList<Project>(); projectList
	 * = employee.getProject(); List<Project> projectList1 = new
	 * CopyOnWriteArrayList<Project>(); projectList1 = employee1.getProject();
	 * 
	 * employee1.setProject(projectList1); }
	 */
	public String deleteEmployee(int employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Employee emp = employeeRepository.getOne(employee);
		List<Project> projectList = projectRepository.findByEmployeeId(emp.getId());
		for(Project prj:projectList) {
			Project prj1=projectRepository.getOne(prj.getId());
			projectRepository.delete(prj1);
		}
		employeeRepository.delete(emp);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return "Employee Deleted";
	}

	public void addEmployeebyUser(User user) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		employeeRepository.save(convertUserToEmployee(user,new Employee()));
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	public void updateEmployeebyUser(User user,Employee employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		employeeRepository.save(convertUserToEmployee(user,employee));
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private Employee convertUserToEmployee(User user,Employee employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		employee.setUserId(user.getId());
		employee.setEmployeeName(user.getFirstName() + user.getLastName());
		employee.setEmployeeEmail(user.getEmail());
		employee.setJoiningDate(user.getCreatedDate());
		Set<Address> address = user.getAddresses();
		if(address.isEmpty())
			employee.setLocation("Chennai");
		else {
			Optional<Address> primaryAddress = address.stream().filter(value -> value.getPrimaryAddress() == 1).findFirst();
			if(primaryAddress.isPresent())
				employee.setLocation(primaryAddress.get().getCity());
		}
		employee.setPhoneNumber(user.getPhoneNumber());
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employee;	
	}

	@Transactional(transactionManager="employeeTransactionManager")
	public Employee addorUpdateEmployee(int employeeId, Employee employee) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Employee> oldEntity=getEmployeebyId(employeeId);
		if(oldEntity.isPresent()) {
			Employee employeeEntity = oldEntity.get();
			projectRepository.deleteByEmployeeId(employeeId);
			employeeEntity.getProject().clear();
			int empId=employeeEntity.getId();
			//oldEntity = employee;
			loadEmployee(employee, employeeEntity);
			employeeEntity.setId(empId);
			return employeeRepository.save(employeeEntity);
		}else {
			return addEmployee(employee);
		}
		
		/*
		 * ListIterator<Project> projectList = employee.getProject().listIterator();
		 * while(projectList.hasNext()) { Project prj=projectList.next(); Project
		 * prj1=new Project(); prj1.setProjectCode(prj.getProjectCode());
		 * prj1.setRole(prj.getRole());
		 * prj1.setDurationInMonth(prj.getDurationInMonth());
		 * prj1.setEmployee(oldEntity); oldEntity.getProject().add(prj1);
		 * projectRepository.save(prj1); }
		 */
		
	}

	public List<Integer> getEmployeeUserList() {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.getEmployeeUserList();
	}

	public Integer getEmployeeCountUserId(int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.countByUserId(userId);
		
	}

	public Employee getEmployeebyUserId(int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return employeeRepository.findByUserId(userId);
		
	}

	public String deleteEmployeebyUser(int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Employee employee=employeeRepository.findByUserId(userId);
		try {
			if(Objects.isNull(employee)) {
				throw new NullPointerException();
				
			}else {
				return deleteEmployee(employee.getId());
			}
		}
		catch(NullPointerException e){
			log.error("No employee Found" + e.getMessage());
			return "No employee Found";
		}
	}

	public List<Employee> getEmployeeforProject(String projectId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Project> projectList = projectRepository.findByProjectCode(projectId);
	    List<Employee> aa = projectList.stream().map(input -> input.getEmployee()).collect(Collectors.toList());
		return aa;
	}

	public String addProjectforEmployee(Project project) {
		Integer empId = project.getEmpId();
		Optional<Employee> employee = getEmployeebyId(empId);
		if(employee.isPresent()) {
			projectRepository.save(project);	
			return "project updated for employee";
		} else {
			return "Employee not Present";
		}
		
	}

}
