package com.example.inventory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultConsumer {

    private final InventoryLuaExecutor inventory;

    public PaymentResultConsumer(InventoryLuaExecutor inventory) {
        this.inventory = inventory;
    }

    @KafkaListener(topics = "payment-result", groupId = "inventory")
    public void onPaymentResult(PaymentResultEvent event) {

        if ("SUCCESS".equals(event.getStatus())) {
            inventory.commit(
                    event.getSkuId(),
                    event.getQuantity(),
                    event.getEventId()
            );
        } else {
            inventory.release(
                    event.getSkuId(),
                    event.getQuantity(),
                    event.getEventId()
            );
        }
    }
}
