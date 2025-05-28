package com.billtracker.billtracker.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bills")
@Data
public class Bill {

  @Id
  private String id;

  private String userId;
  private String name;
  private double amount;
  private int dueDate;
  private String category;
  private String account;


}
