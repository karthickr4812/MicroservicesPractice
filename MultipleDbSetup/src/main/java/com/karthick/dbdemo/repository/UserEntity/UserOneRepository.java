package com.karthick.dbdemo.repository.UserEntity;

import org.springframework.stereotype.Repository;

import com.karthick.dbdemo.model.UserEntity.UserTableOne;

@Repository
public interface UserOneRepository extends GenericRepository<UserTableOne, Integer>{
	
}

