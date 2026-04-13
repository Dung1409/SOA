# Restaurant Order Management - SOA Microservices

Hệ thống tự động hóa quy trình đặt đơn cho nhà hàng đơn theo mô hình SOA/Microservices.

- Luồng nghiệp vụ: Đặt đơn -> Thanh toán -> Giao hàng -> Hoàn tất
- Điều phối giao dịch phân tán bằng Saga Orchestration (Task Service)
- Giao tiếp bất đồng bộ giữa các service bằng RabbitMQ

## Team Members

| Name | Student ID | Role | Contribution |
| ---- | ---------- | ---- | ------------ |
|      |            |      |              |
|      |            |      |              |
|      |            |      |              |

## Service Overview

| Service           | Responsibility                                     | Port                            |
| ----------------- | -------------------------------------------------- | ------------------------------- |
| Frontend          | UI đặt món, gửi yêu cầu đặt đơn                    | 3000                            |
| Gateway           | API Gateway route vào backend                      | 8080                            |
| Task Service      | Saga orchestrator, nhận lệnh đặt đơn và phát event | 8084                            |
| Order Service     | Lưu trữ và cập nhật trạng thái đơn hàng            | 8081                            |
| Payment Service   | Xử lý thanh toán và phát kết quả                   | 8082                            |
| Delivery Service  | Gán giao hàng và phát delivery.assigned            | 8083                            |
| Menu Service      | Cung cấp danh sách món ăn                          | 8085                            |
| Service Discovery | Eureka server cho service registry                 | 8761                            |
| RabbitMQ          | Message broker                                     | 5672 (AMQP), 15672 (Management) |
| MySQL Order       | DB cho Order Service                               | 3310                            |
| MySQL Payment     | DB cho Payment Service                             | 3307                            |
| MySQL Delivery    | DB cho Delivery Service                            | 3308                            |

## High-level Flow

1. Client gọi POST /task/order qua Gateway.
2. Task Service publish order.create.
3. Order Service tạo đơn (PENDING) và publish order.created.
4. Task Service publish payment.request.
5. Payment Service publish payment.success hoặc payment.failed.
6. Nếu thành công: Task Service publish delivery.request, Delivery Service publish delivery.assigned, Order Service cập nhật COMPLETED.
7. Nếu thất bại: Task Service publish order.cancel, Order Service cập nhật CANCELLED.
8. Frontend polling trạng thái đơn qua GET /order/status/{requestId}.

## Run with Docker Compose

```bash
# from repository root
cp .env.example .env
docker compose up --build
```

## Quick Verification

```bash
curl http://localhost:8080
curl http://localhost:8761
curl http://localhost:8084/task/health
curl http://localhost:8081/order/health
curl http://localhost:8082/payment/health
curl http://localhost:8083/delivery/health
curl http://localhost:8085/menu/health
```

## API Specs

- [Task Service - OpenAPI Spec](docs/api-specs/TaskService.yaml)
- [Order Service - OpenAPI Spec](docs/api-specs/OrderService.yaml)
- [Payment Service - OpenAPI Spec](docs/api-specs/PaymentService.yaml)
- [Delivery Service - OpenAPI Spec](docs/api-specs/DeliveryService.yaml)
- [Menu Service - OpenAPI Spec](docs/api-specs/MenuService.yaml)
