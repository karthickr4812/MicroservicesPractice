package com.karthick.dbdemo.repository.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karthick.dbdemo.model.user.User;

@Repository
public interface UserRepository extends JpaRepository <User, Integer>{
	
	@Query(value="SELECT usr.id FROM user usr WHERE  (usr.createdDate BETWEEN ?1 AND ?2)",nativeQuery = true)
	List<Integer> getUserBetweenDates(LocalDate startDate, LocalDate endDate);

	@Query(value="SELECT user_id FROM Address WHERE primaryAddress = 1 AND country = ?1",nativeQuery = true)
	List<Integer> getUserbyCountry(String country);
}

