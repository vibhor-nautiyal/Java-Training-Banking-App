package com.example.demo.banking.repositories;


import com.example.demo.banking.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("Transaction")
public interface TransactionRepo extends JpaRepository<Transactions,Integer> {

}
