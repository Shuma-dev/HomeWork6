Запуск приложения

1. Установить Docker Desktop.
2. Выполнить:

```bash
docker compose up -d
```

3. Открыть проект в IntelliJ IDEA.
4. Запустить класс HomeWorkApplication.

Приложение будет доступно по адресу:

http://localhost:8080

Для проверки API можно использовать Postman.

API-тесты выполнены с использованием MockMvc.
Интеграционные тесты Repository выполнены с использованием Testcontainers.

Добавил Kafka