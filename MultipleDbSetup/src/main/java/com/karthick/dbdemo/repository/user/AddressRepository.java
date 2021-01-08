package com.karthick.dbdemo.repository.user;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.karthick.dbdemo.model.user.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>
{
	//@Query(value="SELECT * FROM Address WHERE user_id = ?1",nativeQuery = true)
	Set<Address> findByUserId(int userId);

	//@Query(value="delete FROM Address WHERE user_id = ?1",nativeQuery = true)
	@Modifying
	@Transactional(transactionManager="userTransactionManager")
	void deleteByUserId(int userId);

}

