# Frontend

## Overview

This is the frontend module of the microservices system. It provides the user interface and communicates with backend services through the API Gateway.

## Tech Stack

| Component       | Choice                              |
| --------------- | ----------------------------------- |
| Framework       | Plain HTML, CSS, JavaScript         |
| Web Server      | Nginx (nginx:alpine)                |
| Styling         | Custom CSS (embedded in index.html) |
| Package Manager | N/A                                 |
| Build Tool      | N/A (served as static files)        |

## Getting Started

```bash
# From project root
docker compose up frontend --build

# Or run frontend container only
docker build -t soa-frontend ./Frontend
docker run --rm -p 3000:80 soa-frontend
```

## Project Structure

```
frontend/
├── Dockerfile
├── index.html     # Main UI page
├── nginx.conf     # Reverse proxy to Gateway/services
├── readme.md
└── src/           # Reserved for future frontend source split
```

## Environment Variables

No required environment variables at this time.

API routing is configured in `nginx.conf`:

- `/api/*` is proxied to `gateway:8080`
- `/api-menu/*` is proxied to `menu-service:8085` (fallback)

## Build for Production

```bash
# Build production image
docker build -t soa-frontend ./Frontend
```

## Notes

- All API calls should go through the **API Gateway** (`gateway`), not directly to individual services.
- Frontend currently ships as static files served by Nginx, so API path mapping should be changed in `nginx.conf` when needed.
