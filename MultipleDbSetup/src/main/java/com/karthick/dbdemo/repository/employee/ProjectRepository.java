package com.karthick.dbdemo.repository.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.karthick.dbdemo.model.employee.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	//@Query(value="SELECT * FROM Project p WHERE p.employee_id = ?1",nativeQuery = true)
	List<Project> findByEmployeeId(int id);

	//@Query(value="delete FROM Project p WHERE p.employee_id = ?1",nativeQuery = true)
	@Modifying
	@Transactional(transactionManager="employeeTransactionManager")
	void deleteByEmployeeId(int employeeId);
	
	List<Project> findByProjectCode(String id);
}
