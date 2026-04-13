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

## Environment Variables

| Variable                 | Description                          | Default        |
| ------------------------ | ------------------------------------ | -------------- |
| `ORDER_SERVICE_PORT`     | Order service HTTP port              | 8081           |
| `ORDER_DB_HOST`          | Order MySQL host                     | localhost      |
| `ORDER_DB_PORT`          | Order MySQL port                     | 3306           |
| `ORDER_DB_NAME`          | Order database name                  | order_db       |
| `ORDER_DB_USER`          | Order database username              | order_user     |
| `ORDER_DB_PASSWORD`      | Order database password              | order_password |
| `RABBITMQ_HOST`          | RabbitMQ host                        | localhost      |
| `RABBITMQ_PORT`          | RabbitMQ port                        | 5672           |
| `RABBITMQ_USER`          | RabbitMQ username                    | guest          |
| `RABBITMQ_PASSWORD`      | RabbitMQ password                    | guest          |
| `EUREKA_HOST`            | Eureka host for service discovery    | localhost      |
| `EUREKA_PORT`            | Eureka port for service discovery    | 8761           |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile (docker usage) | prod           |

## Testing

```bash
# From OrderService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
