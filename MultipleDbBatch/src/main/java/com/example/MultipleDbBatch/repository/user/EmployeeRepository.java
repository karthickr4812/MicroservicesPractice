package com.example.MultipleDbBatch.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MultipleDbBatch.model.user.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Integer>{
	
	
}

