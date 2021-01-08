package com.karthick.dbdemo.model.UserEntity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected T id;
	
	@PrePersist
    public void ensureIdAssigned() {
          ensureIdAssignedInternal();  
    }

    public abstract void ensureIdAssignedInternal();
}