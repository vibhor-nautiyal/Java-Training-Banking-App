package com.example.demo.banking.repositories;

import com.example.demo.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CustomerRepo")
public interface CustomerRepo extends JpaRepository<Customer,Integer> {

}
