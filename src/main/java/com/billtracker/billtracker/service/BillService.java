package com.billtracker.billtracker.service;

import com.billtracker.billtracker.model.Bill;
import com.billtracker.billtracker.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
  private final BillRepository billRepository;

  public BillService(BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  public List<Bill> getAllBills(String userId) {
    return billRepository.findByUserId(userId);
  }

  public Bill createBill(Bill bill) {
    return billRepository.save(bill);
  }

  public Bill updateBill(Bill bill) {
    if(!billRepository.existsById(bill.getId())) {
      throw new RuntimeException("Bill not found");
    }
    return billRepository.save(bill);
  }

  public void deleteBill(String billId) {
    billRepository.deleteById(billId);
  }
}
