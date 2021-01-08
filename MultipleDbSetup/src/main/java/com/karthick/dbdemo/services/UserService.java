
package com.karthick.dbdemo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.karthick.dbdemo.model.employee.Employee;
import com.karthick.dbdemo.model.user.Address;
import com.karthick.dbdemo.model.user.User;
import com.karthick.dbdemo.repository.user.AddressRepository;
import com.karthick.dbdemo.repository.user.UserRepository;

@Service
@Transactional(transactionManager="userTransactionManager")
public class UserService
{
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private AddressRepository addressRepository;
	
	/*
	 * public void setAddressRepository(AddressRepository addressRepository) {
	 * this.addressRepository = addressRepository; }
	 */

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * public void setUserRepository(UserRepository userRepository) {
	 * this.userRepository = userRepository; }
	 */

	@Transactional(transactionManager="userTransactionManager")
	public List<User> getUsers()
	{
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userRepository.findAll();
	}

	public Optional<User> getUserbyId(int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userRepository.findById(userId);
	}
	
	public Set<Address> getAddressbyUserId(int userId) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return addressRepository.findByUserId(userId);
	}

	@Transactional(transactionManager="userTransactionManager")
	public User addUser(User user) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		User user1=new User();
		loadUser(user,user1);
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		return userRepository.save(user1);
	}

	private void loadUser(User user, User user1) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Set<Address> addressList= user.getAddresses();
		if(addressList.size()>0) {
			for(Address adrs:addressList) {
				Address adrs1=new Address();
				adrs1.setCity(adrs.getCity());
				adrs1.setCountry(adrs.getCountry());
				adrs1.setPrimaryAddress(adrs.getPrimaryAddress());
				adrs1.setStreet(adrs.getStreet());
				adrs1.setZip(adrs.getZip());
				adrs1.setUser(user1);
				user1.getAddresses().add(adrs1);
				addressRepository.save(adrs1);
			}
		}
		user1.setFirstName(user.getFirstName());
		user1.setLastName(user.getLastName());
		user1.setEmail(user.getEmail());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setAadharNumber(user.getAadharNumber());
		user1.setCreatedDate(user.getCreatedDate());
		log.debug("Method Ends {}",Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public String deleteUser(int userid) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> user = userRepository.findById(userid);
		if(user.isPresent()) {
			Set<Address> addressList=addressRepository.findByUserId(user.get().getId());
			for(Address adrs:addressList) {
				Optional<Address> adrs1= addressRepository.findById(adrs.getId());
				if (adrs1.isPresent())
					addressRepository.delete(adrs1.get());
			}
			userRepository.deleteById(userid);
			return "User Deleted";
		} else {
			return "No User Found";
		}
	}

	@Transactional(transactionManager="userTransactionManager")
	public User addorupdateUser(int userId,User user) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> oldEntity=getUserbyId(userId);
		if(oldEntity.isPresent()) {
			User userEntity = oldEntity.get();
			addressRepository.deleteByUserId(userId);
			userEntity.getAddresses().clear();
			int usrId=userEntity.getId();
			loadUser(user, userEntity);
			userEntity.setId(usrId);
			return userRepository.save(userEntity);
		}else {
			return addUser(user);
		}
	}

	public String addEmployeebyCreateDate(LocalDate startDate, LocalDate endDate) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Integer> userIdList = userRepository.getUserBetweenDates(startDate,endDate);
		return checkAndInsertUser(userIdList);
	}

	public String addEmployeebyUser(int userid) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> usrService = getUserbyId(userid);
		if(usrService.isPresent()) {
			System.out.println("------------------------------------------------------" + employeeService.getEmployeeCountUserId(userid));
			Integer employeeCount = employeeService.getEmployeeCountUserId(userid);
			if(employeeCount > 0) {
				return "User Already Present";
			}
			employeeService.addEmployeebyUser(usrService.get());
		} else {
			return "User Not Found";
		}
		return "Employee Inserted";
	}

	public String addEmployeebyUserCountry(String country) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Integer> userIdList = userRepository.getUserbyCountry(country);
		return checkAndInsertUser(userIdList);
	}

	private String checkAndInsertUser(List<Integer> userIdList) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Integer> employeeUserList = employeeService.getEmployeeUserList();
		userIdList.removeAll(employeeUserList);
		if(!userIdList.isEmpty()) {
			for(Integer usrId:userIdList) {
				addEmployeebyUser(usrId);
			}
		} else
			return "No User found to update";
		return "Employee's Inserted";
	}

	@Transactional(transactionManager="userTransactionManager")
	public User updateUserandEmployee(int userId,User user) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		User updatedUsr = addorupdateUser(userId,user);
		Employee emp= employeeService.getEmployeebyUserId(userId);
		if(Objects.isNull(emp)) {
			employeeService.addEmployeebyUser(updatedUsr);
		}else {
			employeeService.updateEmployeebyUser(updatedUsr,emp);
		}
		return updatedUsr;
	}

	@Transactional(transactionManager="userTransactionManager")
	public String deleteUserandEmployee(int userid) {
		log.debug("Method Starts {}",Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> user = userRepository.findById(userid);
		try {
			if(user.isEmpty()) {
				throw new NullPointerException();
			}else {
				deleteUser(userid);
				return "User Deleted and " + employeeService.deleteEmployeebyUser(userid);
			}
		}
		catch(NullPointerException e){
			log.error("No User Found" + e.getMessage());
			return "No User Found";
		}
	}
}