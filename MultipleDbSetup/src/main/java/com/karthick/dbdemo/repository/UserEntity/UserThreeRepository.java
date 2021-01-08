package com.karthick.dbdemo.repository.UserEntity;

import org.springframework.stereotype.Repository;

import com.karthick.dbdemo.model.UserEntity.UserTableThree;

@Repository
public interface UserThreeRepository extends GenericRepository<UserTableThree, String>{
	
}

