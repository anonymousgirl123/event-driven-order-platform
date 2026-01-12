# Event Driven Order Platform

Kafka-based order processing platform.

## Inventory Handling

This service does not mutate inventory directly.
All inventory consistency is enforced using Redis Lua scripts
from the `redis-inventory-guard` repository.

Inventory operations are triggered via Kafka event listeners
and executed atomically inside Redis.
