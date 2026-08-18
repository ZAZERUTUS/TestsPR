# Конфигурация тестов

Конфигурация тестов задаётся через файл `test.properties`, параметры командной строки или переменные окружения.

## Файл конфигурации

Файл конфигурации находится здесь:

`src/test/resources/test.properties`

Пример:

```properties
baseUrl=https://citronus.com
browser=chrome
headless=false
timeout=10000
screenSize=1920x1080
email=test@example.com
password=your_password
```

## Приоритет переменных

Если одна и та же переменная задана в нескольких источниках, используется значение с более высоким приоритетом:

1. **Консоль / System Property**
2. **`test.properties`**
3. **Переменная окружения**

Схема приоритета:

```text
-D параметр
    ↓
test.properties
    ↓
Environment Variable
```

Пустые значения (`""` или строка, содержащая только пробелы) считаются **не заданными**. В этом случае значение ищется в следующем источнике.

Если переменная не найдена ни в одном из источников, запуск теста завершается с ошибкой.

## Пример приоритета

В `test.properties` указано:

```properties
browser=chrome
```

При запуске:

```bash
mvn test -Dbrowser=firefox
```

будет использовано значение:

```text
browser=firefox
```

поскольку параметр командной строки имеет более высокий приоритет.

Если `-Dbrowser` не указан, будет использовано значение из `test.properties`.

Если значения нет ни в `test.properties`, ни в параметрах командной строки, проверяется переменная окружения:

```bash
export BROWSER=chrome
```

## Доступные параметры

| Параметр     | Переменная окружения | Описание                          |
| ------------ | -------------------- | --------------------------------- |
| `baseUrl`    | `BASE_URL`           | URL приложения                    |
| `browser`    | `BROWSER`            | Браузер                           |
| `headless`   | `HEADLESS`           | Запуск браузера в headless режиме |
| `timeout`    | `TIMEOUT`            | Таймаут ожидания элементов        |
| `screenSize` | `SCREEN_SIZE`        | Размер окна браузера              |
| `email`      | `TEST_EMAIL`         | Email тестового пользователя      |
| `password`   | `TEST_PASSWORD`      | Пароль тестового пользователя     |

## Запуск тестов

### Использование `test.properties`

```bash
mvn clean test
```

### Переопределение параметров через консоль

```bash
mvn clean test -Dheadless=true
```

### Передача данных тестового пользователя

```bash
mvn test -Demail=test@example.com -Dpassword=secret
```
