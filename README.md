# Spring Boot final project

Оглавление 
* [Используемые технологии](#используемые-технологии)
  * [Компоненты observability](#компоненты-observability)
* [Требования к проекту](#требования-к-проекту)

## Используемые технологии
....
### Компоненты observability:
- Spring Boot Actuator (эндпоинты health, metrics, info)
- Micrometer (регистрация метрик в формате понятном для Prometheus)
- Prometheus (сбор и хранение)
- Grafana (визуализация)

## Требования к проекту
С описанными требованиями и выполненными чекпоинтами можно ознакомиться в файле [требования к проекту](/требования к проекту.md)

## Пометки по ключевым моментам проекта
* Создана кастомная метрика
[CustomMetricsService](src/main/java/com/javarush/hibernate_final/ostapenko/hibernate/service/metrics/CustomMetricsService.java)
для отслеживания кол-ва вызовов метода контроллера
[CustomMetricsService](src/main/java/com/javarush/hibernate_final/ostapenko/hibernate/controller/home/HomeUIController.java).

    Посмотреть эту метрику можно по пути: http://localhost:8080/actuator/metrics/custom.metric.counter