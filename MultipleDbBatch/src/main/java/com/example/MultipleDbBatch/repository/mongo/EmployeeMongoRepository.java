package com.example.MultipleDbBatch.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.MultipleDbBatch.model.mongo.EmployeeMongo;

@Repository
public interface EmployeeMongoRepository extends MongoRepository<EmployeeMongo, Integer>{

}
