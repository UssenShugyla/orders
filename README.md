# REST-сервис управления заказами

## Описание
Приложение на **Spring Boot** с архитектурой **MVC**, реализующее управление клиентами и заказами. Подключается к **PostgreSQL**, развернутой в **Docker**, поддерживает валидацию данных и глобальную обработку ошибок.

## Технологии
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Jakarta Validation

## Архитектура
- **Controller** — обработка HTTP-запросов
- **Service** — бизнес-логика
- **Repository** — доступ к данным

## Сущности

### Customer
- `id`: Long
- `name`: String (обязательное поле)
- `email`: String (обязательное, уникальное, валидный формат)
- `createdAt`: LocalDateTime

### Order
- `id`: Long
- `customerId`: Long (ссылка на Customer)
- `amount`: BigDecimal (должно быть > 0)
- `status`: NEW, PAID, CANCELLED
- `createdAt`: LocalDateTime

## CRUD Эндпоинты

### Customer
- `POST /customers` — создать клиента
- `GET /customers` — получить список
- `GET /customers/{id}` — получить по ID
- `PUT /customers/{id}` — обновить
- `DELETE /customers/{id}` — удалить

### Order
- `POST /orders` — создать заказ
- `GET /orders` — получить список
- `GET /orders/{id}` — получить по ID
- `PUT /orders/{id}` — обновить
- `DELETE /orders/{id}` — удалить
- `POST /orders/{id}/pay` — оплатить заказ (только NEW)
- `POST /orders/{id}/cancel` — отменить заказ (кроме PAID)

## Обработка ошибок
- Customer not found → `HTTP 404`
- Order not found → `HTTP 404`
- Нарушение бизнес-логики → `HTTP 400`
- Ошибки валидации → `HTTP 400`

## Docker
Файл `docker-compose.yml` поднимает PostgreSQL:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: orders_db
    environment:
      POSTGRES_DB: orders
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
    ports:
      - "5432:5432"