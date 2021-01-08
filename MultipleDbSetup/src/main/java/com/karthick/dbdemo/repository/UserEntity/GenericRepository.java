package com.karthick.dbdemo.repository.UserEntity;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.karthick.dbdemo.model.UserEntity.UserEntity;

@NoRepositoryBean
public interface GenericRepository <T extends UserEntity<ID>,ID extends Serializable> extends JpaRepository<T, ID>{
	
}
