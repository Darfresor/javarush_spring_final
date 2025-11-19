# Используем официальный образ MySQL
FROM mysql:8.0

# Устанавливаем переменные окружения
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=myapp

# Создаем volume
VOLUME ["/var/lib/mysql"]

# Открываем порт MySQL
EXPOSE 3306