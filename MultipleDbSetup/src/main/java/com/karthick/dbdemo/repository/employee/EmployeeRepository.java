package com.karthick.dbdemo.repository.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.karthick.dbdemo.model.employee.Employee;

@Repository
@EnableJpaRepositories(
		basePackages = {"com.karthick.dbdemo.repository.employee"},
		entityManagerFactoryRef = "employeeEntityManagerFactory",
		transactionManagerRef = "employeeTransactionManager"
		)
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query(value="SELECT userId FROM Employee",nativeQuery = true)
	List<Integer> getEmployeeUserList();
	
	//@Query(value="SELECT COUNT(userId) FROM employee where userId = ?1",nativeQuery = true)
	Integer countByUserId(int userId);

	//@Query(value="SELECT * FROM Employee where userId = ?1",nativeQuery = true)
	Employee findByUserId(int userId);

}
