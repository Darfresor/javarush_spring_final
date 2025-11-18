echo "=== Starting MySQL Docker Setup ==="
# Собираем образ
echo "Building Docker image..."
docker build -t my-mysql:1.0 .

# Запускаем (аналогично оригинальной команде)

docker run --name mysql -d \
 -p 3306:3306 \
 --restart unless-stopped \
 -v mysql:/var/lib/mysql my-mysql:1.0