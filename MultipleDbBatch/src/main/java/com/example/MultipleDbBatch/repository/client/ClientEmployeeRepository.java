package com.example.MultipleDbBatch.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MultipleDbBatch.model.client.ClientEmployee;

@Repository
public interface ClientEmployeeRepository extends JpaRepository<ClientEmployee, Integer>{

}
