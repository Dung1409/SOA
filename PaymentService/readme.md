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

## Testing

```bash
# From PaymentService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
