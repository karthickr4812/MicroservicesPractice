package com.karthick.dbdemo.repository.UserEntity;

import org.springframework.stereotype.Repository;

import com.karthick.dbdemo.model.UserEntity.UserTableTwo;

@Repository
public interface UserTwoRepository extends GenericRepository<UserTableTwo, Long>{

	
}

