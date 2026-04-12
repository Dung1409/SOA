# Task Service

## Overview

Task Service is the saga orchestrator entrypoint for order processing.

- Accepts order requests from frontend/API clients
- Publishes saga start event to RabbitMQ
- Tracks saga state by requestId in memory
- Exposes status endpoint so clients can poll final result

## Tech Stack

| Component         | Choice                                       |
| ----------------- | -------------------------------------------- |
| Language          | Java 17                                      |
| Framework         | Spring Boot 3.4.2                            |
| Messaging         | RabbitMQ                                     |
| Service Discovery | Eureka Client                                |

## API Endpoints

| Method | Endpoint     | Description                |
| ------ | ------------ | -------------------------- |
| GET    | /task/health | Service health check       |


### POST /task/order

Submits an order request to the saga orchestrator.

**Request:**

```json
{
  "amount": 100.0,
  "phone": "0123456789",
  "address": "123 Main St",
  "description": "Item 1, Item 2",
  "menuItemIds": ["M01", "M02", "M03"]
}
```

**Response (202 Accepted):**

```json
{
  "requestId": "req-123",
  "status": "ORDER_SUBMITTED",
  "message": "Order request accepted and queued."
}
```

## Running Locally

```bash
# From project root
docker compose up -d --build task-service
```

## Testing

```bash
# From TaskService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
