# API Gateway

## Overview

The API Gateway is the single entry point for client-facing API traffic. It routes incoming requests to backend services discovered via Eureka.

## Responsibilities

- Request routing to microservices
- Service discovery integration (Eureka)
- Basic CORS support for frontend access
- Centralized external API port exposure

## Tech Stack

| Component         | Choice               |
| ----------------- | -------------------- |
| Language          | Java 17              |
| Framework         | Spring Cloud Gateway |
| Runtime Model     | WebFlux (reactive)   |
| Service Discovery | Eureka Client        |

## Routing Table

| External Path  | Target Service ID |
| -------------- | ----------------- |
| /order/\*\*    | ORDER-SERVICE     |
| /payment/\*\*  | PAYMENT-SERVICE   |
| /delivery/\*\* | DELIVERY-SERVICE  |
| /menu/\*\*     | MENU-SERVICE      |
| /task/\*\*     | TASK-SERVICE      |

## Running

```bash
# From project root
docker compose up -d --build gateway
```

## Testing

```bash
# From Gateway directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Configuration

Gateway port is configured by GATEWAY_PORT (default 8080).

Route definitions are in: src/main/resources/application.yaml

Upstream routes use load-balanced URIs (lb://SERVICE-NAME), resolved from Eureka registry.

## Notes

- When running in Docker, services should communicate by compose service name
- If startup order is slow, initial requests may fail until all services register with Eureka
