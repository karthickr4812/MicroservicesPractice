package com.karthick.dbdemo.model.UserEntity;

import javax.persistence.Entity;

@Entity
public class UserTableOne extends UserEntity<Integer> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void ensureIdAssignedInternal() {
		// Do nothing because of X and Y.
	}
}
