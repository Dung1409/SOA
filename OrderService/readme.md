# Order Service

## Overview

Order Service is responsible for creating and managing order records during the saga flow.

- Consumes order creation events from RabbitMQ
- Persists order data to MySQL
- Emits order-created events for downstream services (payment, delivery)
- Handles order cancellation updates from saga rollback

## Tech Stack

| Component         | Choice            |
| ----------------- | ----------------- |
| Language          | Java 17           |
| Framework         | Spring Boot 3.4.2 |
| Data Access       | Spring Data JPA   |
| Database          | MySQL             |
| Messaging         | RabbitMQ          |
| Service Discovery | Eureka Client     |

## API Endpoints

| Method | Endpoint          | Description                              |
| ------ | ----------------- | ---------------------------------------- |
| GET    | /order/health     | Service health check                     |
| GET    | /order/status/:id | Get order status by request ID (polling) |

### Order Status Enum

The order status field uses an enum with the following values:

- **PENDING** - Order created, waiting for payment
- **PAID** - Payment successful, waiting for delivery
- **COMPLETED** - Delivery assigned, order completed
- **CANCELLED** - Order cancelled

### GET /order/status/:id

Returns the order status information for a given request ID.

**Response (200 OK):**

```json
{
  "requestId": "req-123",
  "orderId": "order-456",
  "status": "PAID",
  "message": "Order status: Paid",
  "updatedAt": "2026-04-12T10:30:45.123456"
}
```

**Response (404 Not Found):**
Order with given requestId not found.

Full API specification: [../docs/api-specs/OrderService.yaml](../docs/api-specs/OrderService.yaml)

## Running Locally

```bash
# From project root
docker compose up -d --build order-service
```

## Testing

```bash
# From OrderService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
