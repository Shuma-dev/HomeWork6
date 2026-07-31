# Microservices Project

Микросервисное приложение на Spring Boot, состоящее из двух сервисов:

- **user-service** — управление пользователями и публикация событий в Kafka.
- **notification-service** — получение событий из Kafka и отправка email-уведомлений.

## Запуск

Запустите Docker:

```bash
docker compose up -d
```

После запуска Docker запустите приложения:

- `user-service`
- `notification-service`

## Использование через терминал

Создать пользователя:

```bash
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{
  "name":"Denis",
  "email":"test@example.com",
  "age":25
}'
```

Удалить пользователя:

```bash
curl -X DELETE http://localhost:8080/users/{id}
```

Приложение доступно по адресу:

```
http://localhost:8080/users
```

После создания пользователь сохраняется в PostgreSQL, событие отправляется в Kafka, а `notification-service` получает его и отправляет email на указанный адрес