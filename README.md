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
1️⃣ System-Level Architecture (Big Picture)
📌 What this communicates

  - Clear separation of responsibilities

  - Inventory correctness isolated

  - Event-driven thinking

<img width="706" height="355" alt="image" src="https://github.com/user-attachments/assets/c431586a-1a5c-49ec-92b6-0dddead8a53f" />

2️⃣ Inventory State Machine (Correctness Focus)
📌 What this communicates

  - Deterministic inventory lifecycle

  - Explicit rollback & compensation

  - No hidden transitions

<img width="387" height="318" alt="image" src="https://github.com/user-attachments/assets/e4eef178-7d43-4731-b0d3-ab1a7895a8b5" />

3️⃣ Failure Scenario: Kafka Replay Protection
📌 What this communicates

  - Real production failure handling

  - Idempotency awareness

  - Kafka maturity

    <img width="347" height="307" alt="image" src="https://github.com/user-attachments/assets/7e9dd822-47ad-4050-a254-4a57ccb63fd5" />

4️⃣ Failure Scenario: Payment Failure Rollback
📌 What this communicates

  - You designed for negative paths

  - No inventory leaks

  - Clean rollback

<img width="302" height="199" alt="image" src="https://github.com/user-attachments/assets/f8e8919a-74ac-48be-a55b-07833bda585c" />


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

