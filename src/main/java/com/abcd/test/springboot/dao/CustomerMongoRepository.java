package com.abcd.test.springboot.dao;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abcd.test.springboot.model.Customer;


public interface CustomerMongoRepository extends MongoRepository<Customer, Long> {
	public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
}
