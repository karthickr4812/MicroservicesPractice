package com.karthick.dbdemo.model.UserEntity;

import javax.persistence.Entity;

@Entity
public class UserTableTwo extends UserEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Override
	public void ensureIdAssignedInternal() {
		// Do nothing because of X and Y.
	}
}
