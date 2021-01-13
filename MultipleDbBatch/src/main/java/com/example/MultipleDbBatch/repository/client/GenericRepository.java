package com.example.MultipleDbBatch.repository.client;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.MultipleDbBatch.model.client.BaseClientEmployee;

@NoRepositoryBean
public interface GenericRepository <T extends BaseClientEmployee,ID extends Serializable> extends JpaRepository<T, ID>{
	
}
