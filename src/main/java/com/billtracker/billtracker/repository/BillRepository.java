package com.billtracker.billtracker.repository;


import com.billtracker.billtracker.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String > {
  List<Bill> findAll();
}
