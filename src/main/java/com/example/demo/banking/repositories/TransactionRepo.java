package com.example.demo.banking.repositories;


import com.example.demo.banking.entities.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends CrudRepository<Transactions,Integer> {

    @Query(value = "select * from transactions where c_id=?1",nativeQuery = true)
    List<Transactions> findByCId(Integer id);

    @Query(value = "select * from transactions where c_id=?1",nativeQuery = true)
    Page<Transactions> findByCidPaged(Integer id, Pageable pageable);
}
