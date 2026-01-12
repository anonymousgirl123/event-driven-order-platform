package com.example.inventory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final InventoryLuaExecutor inventory;

    public OrderCreatedConsumer(InventoryLuaExecutor inventory) {
        this.inventory = inventory;
    }

    @KafkaListener(topics = "order-created", groupId = "inventory")
    public void onOrderCreated(OrderCreatedEvent event) {

        String result = inventory.reserve(
                event.getSkuId(),
                event.getQuantity(),
                event.getEventId()
        );

        switch (result) {
            case "RESERVED" -> System.out.println("Stock reserved");
            case "DUPLICATE" -> System.out.println("Duplicate event ignored");
            case "INSUFFICIENT_STOCK" -> System.out.println("Reject order");
            default -> throw new IllegalStateException("Unknown result " + result);
        }
    }
}
