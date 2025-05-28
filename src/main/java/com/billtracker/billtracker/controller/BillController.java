package com.billtracker.billtracker.controller;


import com.billtracker.billtracker.model.Bill;
import com.billtracker.billtracker.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "https://localhost:5173")
public class BillController {
  private final BillService billService;

  public BillController(BillService billService) {
    this.billService = billService;
  }

  @GetMapping
  public ResponseEntity<List<Bill>> getAllBills(@RequestParam String userId) {
    List<Bill> bills = billService.getAllBills(userId);

    return ResponseEntity.ok(bills);
  }

  @PostMapping
  public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
    Bill createdBill = billService.createBill(bill);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdBill.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdBill);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Bill> updateBill(@PathVariable String id, @RequestBody Bill bill) {
    try {
      Bill updatedBill = billService.updateBill(bill);
      return ResponseEntity.ok(updatedBill);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBill(@PathVariable String id) {
    try {
      billService.deleteBill(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
