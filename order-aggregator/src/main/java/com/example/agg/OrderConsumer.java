
package com.example.agg;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import shared.events.*;
@Component
public class OrderConsumer{
 private final OrderRepository repo;
 public OrderConsumer(OrderRepository repo){this.repo=repo;}
 @KafkaListener(topics="order-events.v1",groupId="aggregator")
 public void consume(EventEnvelope<?> env){
  if(env.payload() instanceof OrderApproved a){
   OrderEntity e=repo.findById(a.orderId()).orElse(new OrderEntity(a.orderId()));
   e.setStatus("APPROVED"); repo.save(e);
  }
  if(env.payload() instanceof OrderRejected r){
   OrderEntity e=repo.findById(r.orderId()).orElse(new OrderEntity(r.orderId()));
   e.setStatus("REJECTED"); repo.save(e);
  }
 }
}
