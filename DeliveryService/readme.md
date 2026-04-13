# Delivery Service

## Overview

Delivery Service handles delivery assignment steps in the order saga.

- Consumes delivery request events from RabbitMQ
- Persists delivery records to MySQL
- Publishes delivery assigned events for downstream flow
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

| Method | Endpoint         | Description          |
| ------ | ---------------- | -------------------- |
| GET    | /delivery/health | Service health check |

Full API specification: [../docs/api-specs/DeliveryService.yaml](../docs/api-specs/DeliveryService.yaml)

## Running Locally

```bash
# From project root
docker compose up -d --build delivery-service
```

## Environment Variables

| Variable               | Description                          | Default           |
| ---------------------- | ------------------------------------ | ----------------- |
| DELIVERY_SERVICE_PORT  | Delivery service HTTP port           | 8083              |
| DELIVERY_DB_HOST       | Delivery MySQL host                  | localhost         |
| DELIVERY_DB_PORT       | Delivery MySQL port                  | 3308              |
| DELIVERY_DB_NAME       | Delivery database name               | delivery_db       |
| DELIVERY_DB_USER       | Delivery database username           | delivery_user     |
| DELIVERY_DB_PASSWORD   | Delivery database password           | delivery_password |
| RABBITMQ_HOST          | RabbitMQ host                        | localhost         |
| RABBITMQ_PORT          | RabbitMQ port                        | 5672              |
| RABBITMQ_USER          | RabbitMQ username                    | guest             |
| RABBITMQ_PASSWORD      | RabbitMQ password                    | guest             |
| EUREKA_HOST            | Eureka host for service discovery    | localhost         |
| EUREKA_PORT            | Eureka port for service discovery    | 8761              |
| SPRING_PROFILES_ACTIVE | Active Spring profile (docker usage) | prod              |

## Testing

```bash
# From DeliveryService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
