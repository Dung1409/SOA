# Service Discovery

## Overview

Service Discovery cung cấp Eureka Server để các microservice đăng ký và tìm thấy nhau trong hệ thống.

- Chạy vai trò service registry trung tâm
- Gateway và các service backend kết nối qua service name
- Không chứa business logic nghiệp vụ

## Tech Stack

| Component | Choice                             |
| --------- | ---------------------------------- |
| Language  | Java 17                            |
| Framework | Spring Boot 3.4.2                  |
| Discovery | Spring Cloud Netflix Eureka Server |

## Endpoint

| Method | Endpoint | Description      |
| ------ | -------- | ---------------- |
| GET    | /        | Eureka dashboard |

Default port: 8761

## Running Locally

```bash
# From project root
docker compose up -d --build service-discovery
```

## Configuration

Main config file: src/main/resources/application.yaml

- spring.application.name = ServiceDiscovery
- server.port = ${SERVICE_DISCOVERY_PORT:8761}
- register-with-eureka = false
- fetch-registry = false

## Notes

- ServiceDiscovery là Eureka Server nên không cần đăng ký vào chính nó.
- Các service khác sẽ dùng defaultZone trỏ tới http://service-discovery:8761/eureka trong docker network.
