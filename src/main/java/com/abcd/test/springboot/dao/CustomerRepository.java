package com.abcd.test.springboot.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.abcd.test.springboot.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
