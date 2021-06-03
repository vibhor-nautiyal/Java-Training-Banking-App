package com.example.demo.banking.repositories;

import com.example.demo.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("CustomerRepo")
public interface CustomerRepo extends CrudRepository<Customer,Integer> {

    @Query(value = "select * from Customer where account_type=?1",nativeQuery = true)
    List<Customer> getByAccountType(String type);



}
