# Payment Service

## Overview

Payment Service processes payment steps in the order saga.

- Consumes payment-related events from RabbitMQ
- Persists payment transactions to MySQL
- Publishes payment success/failure events for orchestration
- Exposes a health endpoint for monitoring

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

| Method | Endpoint        | Description          |
| ------ | --------------- | -------------------- |
| GET    | /payment/health | Service health check |

Full API specification: [../docs/api-specs/PaymentService.yaml](../docs/api-specs/PaymentService.yaml)

## Running Locally

```bash
# From project root
docker compose up -d --build payment-service
```

## Environment Variables

| Variable                 | Description                          | Default          |
| ------------------------ | ------------------------------------ | ---------------- |
| `PAYMENT_SERVICE_PORT`   | Payment service HTTP port            | 8082             |
| `PAYMENT_DB_HOST`        | Payment MySQL host                   | localhost        |
| `PAYMENT_DB_PORT`        | Payment MySQL port                   | 3307             |
| `PAYMENT_DB_NAME`        | Payment database name                | payment_db       |
| `PAYMENT_DB_USER`        | Payment database username            | payment_user     |
| `PAYMENT_DB_PASSWORD`    | Payment database password            | payment_password |
| `RABBITMQ_HOST`          | RabbitMQ host                        | localhost        |
| `RABBITMQ_PORT`          | RabbitMQ port                        | 5672             |
| `RABBITMQ_USER`          | RabbitMQ username                    | guest            |
| `RABBITMQ_PASSWORD`      | RabbitMQ password                    | guest            |
| `EUREKA_HOST`            | Eureka host for service discovery    | localhost        |
| `EUREKA_PORT`            | Eureka port for service discovery    | 8761             |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile (docker usage) | prod             |

## Testing

```bash
# From PaymentService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
