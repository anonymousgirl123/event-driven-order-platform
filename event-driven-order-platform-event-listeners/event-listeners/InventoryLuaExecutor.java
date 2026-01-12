package com.example.inventory;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLuaExecutor {

    private final StringRedisTemplate redis;
    private final DefaultRedisScript<List> reserveScript;
    private final DefaultRedisScript<List> commitScript;
    private final DefaultRedisScript<List> releaseScript;

    public InventoryLuaExecutor(
            StringRedisTemplate redis,
            DefaultRedisScript<List> reserveScript,
            DefaultRedisScript<List> commitScript,
            DefaultRedisScript<List> releaseScript) {
        this.redis = redis;
        this.reserveScript = reserveScript;
        this.commitScript = commitScript;
        this.releaseScript = releaseScript;
    }

    public String reserve(String skuId, int qty, String eventId) {
        List<String> keys = List.of(
            "sku:" + skuId + ":available",
            "sku:" + skuId + ":reserved",
            "sku:" + skuId + ":events"
        );

        List<?> result = redis.execute(reserveScript, keys,
                String.valueOf(qty), eventId);

        return (String) result.get(0);
    }

    public String commit(String skuId, int qty, String eventId) {
        List<String> keys = List.of(
            "sku:" + skuId + ":reserved",
            "sku:" + skuId + ":sold",
            "sku:" + skuId + ":events"
        );

        List<?> result = redis.execute(commitScript, keys,
                String.valueOf(qty), eventId);

        return (String) result.get(0);
    }

    public String release(String skuId, int qty, String eventId) {
        List<String> keys = List.of(
            "sku:" + skuId + ":available",
            "sku:" + skuId + ":reserved",
            "sku:" + skuId + ":events"
        );

        List<?> result = redis.execute(releaseScript, keys,
                String.valueOf(qty), eventId);

        return (String) result.get(0);
    }
}
