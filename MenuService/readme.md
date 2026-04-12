# Menu Service

## Overview

Menu Service provides read-only menu data for ordering.

- Exposes menu item catalog
- Exposes service health endpoint
- Registers with Eureka for discovery behind Gateway
- Uses in-memory menu list (no database dependency)

## Tech Stack

| Component         | Choice                |
| ----------------- | --------------------- |
| Language          | Java 17               |
| Framework         | Spring Boot 3.4.2     |
| Database          | None (in-memory list) |
| Service Discovery | Eureka Client         |

## API Endpoints

| Method | Endpoint     | Description              |
| ------ | ------------ | ------------------------ |
| GET    | /menu/health | Service health check     |
| GET    | /menu/items  | Get available menu items |

Full API specification: [../docs/api-specs/MenuService.yaml](../docs/api-specs/MenuService.yaml)

## Running Locally

```bash
# From project root
docker compose up -d --build menu-service
```

## Testing

```bash
# From MenuService directory
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```
