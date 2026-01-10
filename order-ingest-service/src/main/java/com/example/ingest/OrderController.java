
package com.example.ingest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import shared.events.*;
import java.time.Instant;
import java.util.UUID;
@RestController
@RequestMapping("/orders")
public class OrderController{
 private final KafkaTemplate<String,EventEnvelope<OrderCreatedV2>> kafka;
 public OrderController(KafkaTemplate<String,EventEnvelope<OrderCreatedV2>> kafka){this.kafka=kafka;}
 @PostMapping
 public void create(@RequestBody OrderCreatedV2 order){
  EventEnvelope<OrderCreatedV2> env=new EventEnvelope<>(UUID.randomUUID().toString(),"OrderCreated",2,Instant.now(),order);
  kafka.send("orders.v2",order.orderId(),env);
 }
}
