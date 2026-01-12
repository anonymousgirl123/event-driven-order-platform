# Event-Driven Order Platform

A Kafka-based order processing platform demonstrating
**failure-resilient inventory handling** using Redis Lua scripts.

This repository focuses on **orchestration**, not data correctness.

---

## Architecture Overview

This service:
- Consumes Kafka events
- Orchestrates order and payment flow
- Delegates inventory correctness to Redis Lua

Inventory is **never mutated in application code**.

---

## Inventory Handling Strategy

Inventory consistency is enforced using a dedicated repository:

➡ `redis-inventory-guard`

Why?
- Atomic stock operations
- Retry & replay safety
- Crash resilience
- Clear separation of concerns

---

## Event Flow

OrderCreated (Kafka)
↓
Inventory Reserve (Redis Lua)
↓
Payment Processing
↓
Commit or Release Stock (Redis Lua)


Kafka guarantees **delivery**.  
Redis guarantees **correctness**.

---

## Design Principles

- Thin Kafka consumers
- No business logic in listeners
- Idempotent by design
- Failure-first thinking
- Easy recovery & replay

---

## Failure Handling

✔ Consumer crashes → Kafka replay  
✔ Redis retries → Lua idempotency  
✔ Payment failure → stock release  
✔ Partial failures → compensation supported  

---

## Why This Design Matters

This approach:
- Scales horizontally
- Survives retries & replays
- Is easy to reason about under failure
- Mirrors real production architectures

> Inventory correctness is enforced at the data layer,  
> not scattered across services.

---

## Intended Audience

- Backend/platform engineers
- Distributed systems practitioners
- Kafka-based architectures
- Engineers designing for correctness, not demos

