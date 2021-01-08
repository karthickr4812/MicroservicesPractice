package com.karthick.dbdemo;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.karthick.dbdemo.model.user.Address;
import com.karthick.dbdemo.model.user.User;
import com.karthick.dbdemo.repository.employee.EmployeeRepository;
import com.karthick.dbdemo.repository.user.AddressRepository;
import com.karthick.dbdemo.repository.user.UserRepository;
import com.karthick.dbdemo.services.EmployeeService;
import com.karthick.dbdemo.services.UserService;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class MultipleDbSetupApplicationTests {

	@InjectMocks
	protected UserService userService = new UserService();
	@Mock
	protected EmployeeService employeeService = new EmployeeService();
	@Mock
	private UserRepository userRepository = mock(UserRepository.class);
	@Mock
	private AddressRepository addressRepository= mock(AddressRepository.class);
	@Mock
	private EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

	@Test
	void testFindAll() {
		List<User> user=new ArrayList<>();
		user.add(new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now()));
		user.add(new User(2,"Rohit","Sharma","Rohit@gmail.com", null, null, null, LocalDate.now()));
		user.add(new User(3,"Irfan","Khan","Irfan@gmail.com", null, null, null, LocalDate.now()));
		user.add(new User(4,"Ramesh","Kumar","ramesh@gmail.com", null,null, null, LocalDate.now()));
		user.add(new User(5,"john","Becham","john@gmail.com", null, null, null, LocalDate.now()));
		user.add(new User(6,"Salman","Khan","salman@gmail.com", null, null, null, LocalDate.now()));

		when(userRepository.findAll()).thenReturn(user);
		List<User> result = userService.getUsers();
		Assert.assertEquals(6, result.size());
		verify(userRepository).findAll();
	}

	@Test
	void testifUserEmpty() {
		List<User> user=new ArrayList<>();
		when(userRepository.findAll()).thenReturn(user);
		List<User> result = userService.getUsers();
		Assert.assertEquals(0, result.size());
		verify(userRepository).findAll();
	}

	@Test
	void testGetUserbyId() {
		Optional<User> user = Optional.of(new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now()));
		when(userRepository.findById(1)).thenReturn(user);
		Optional<User> result = userService.getUserbyId(1);
		Assert.assertEquals("suresh", result.get().getFirstName());
		verify(userRepository).findById(1);
	}

	@Test
	void testGetAddressbyUserId() {
		User user = new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now());
		Set<Address> address = new HashSet<>();
		address.add(new Address(1,"MCN Nagar","Chennai", 600097L, "India", 1,user));
		address.add(new Address(2,"Meenakshi Nagar","Madurai", 610102L, "India", 0, user));
		when(addressRepository.findByUserId(1)).thenReturn(address);
		Set<Address> result = addressRepository.findByUserId(1);
		int userId = result.stream().map(x -> x.getUser().getId()).findFirst().get();
		Assert.assertEquals(1, userId);
		verify(addressRepository).findByUserId(1);
	}

	@Test
	void testAddUser() {
		when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

		User user = new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now());
		User savedUser =userRepository.save(user);
		Assert.assertEquals(user.getId(), savedUser.getId());
		verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	void testDeleteUser() throws Exception {     
		Optional<User> user = Optional.of(new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now()));
		when(userRepository.findById(1)).thenReturn(user);
		Set<Address> address = new HashSet<>();
		Optional<Address> one = Optional.of(new Address(1,"MCN Nagar","Chennai", 600097L, "India", 1,user.get()));
		address.add(one.get());
		when(addressRepository.findByUserId(1)).thenReturn(address);
		when(addressRepository.findById(1)).thenReturn(one);
		userService.deleteUser(1);
		verify(userRepository, atLeast(1)).findById(1);
		verify(userRepository, atLeast(1)).deleteById(1);
	}
	
	@Test
	void testaddEmployeebyCreateDate() {
		List<User> user=new ArrayList<>();
		user.add(new User(1,"suresh","Kumar","suresh@gmail.com", null,null, null, LocalDate.now()));
		user.add(new User(2,"Rohit","Sharma","Rohit@gmail.com", null, null, null, LocalDate.now()));
		int[] id = {1,2};
		List idList = Arrays.asList(id);
		int[] empid = {3};
		List empidList = Arrays.asList(empid);
		
		when(userRepository.getUserBetweenDates(LocalDate.now(),LocalDate.now())).thenReturn(idList);
		doReturn(empidList).when(employeeService).getEmployeeUserList();
		String result = userService.addEmployeebyCreateDate(LocalDate.now(),LocalDate.now());
		Assert.assertEquals("Employee's Inserted", result);
		verify(userRepository,times(1)).getUserBetweenDates(LocalDate.now(),LocalDate.now());
		
		reset(userRepository,employeeService);
		
		int[] sameempid = {1,2};
		List sameempidList = Arrays.asList(sameempid);
		doReturn(sameempidList).when(employeeService).getEmployeeUserList();
		String result2 = userService.addEmployeebyCreateDate(LocalDate.now(),LocalDate.now());
		
		Assert.assertEquals("No User found to update", result2);
		verify(userRepository,times(1)).getUserBetweenDates(LocalDate.now(),LocalDate.now());
	}
	
	@Test
	void TestAddEmployeebyUserCountry() {
		
		
		int[] id = {1,2};
		List idList = Arrays.asList(id);
		int[] empid = {3};
		List empidList = Arrays.asList(empid);
		
		when(userRepository.getUserbyCountry("India")).thenReturn(idList);
		doReturn(empidList).when(employeeService).getEmployeeUserList();
		String result = userService.addEmployeebyUserCountry("India");
		Assert.assertEquals("Employee's Inserted", result);
		verify(userRepository,times(1)).getUserbyCountry("India");
	}
	
	
	
}
