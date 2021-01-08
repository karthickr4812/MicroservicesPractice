package com.karthick.dbdemo.model.UserEntity;

import java.util.UUID;

import javax.persistence.Entity;

@Entity
public class UserTableThree extends UserEntity<String> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void ensureIdAssignedInternal() {
		this.id = generateMyTextId();
	}
	
	private String generateMyTextId() {
         return UUID.randomUUID().toString();
    }
}
