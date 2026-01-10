
package com.example.agg;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name="orders")
public class OrderEntity{
 @Id private String orderId;
 private String status;
 private Instant updatedAt;
 public OrderEntity(){}
 public OrderEntity(String id){this.orderId=id;}
 public void setStatus(String s){this.status=s;this.updatedAt=Instant.now();}
}
