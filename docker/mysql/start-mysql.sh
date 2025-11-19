echo "=== Starting MySQL Docker Setup ==="

echo "Cleaning up existing containers and volumes..."
docker stop mysql 2>/dev/null
docker rm mysql 2>/dev/null
docker volume rm mysql 2>/dev/null

# Собираем образ
echo "Building Docker image..."
docker build -t my-mysql:1.0 .



# Запускаем
docker run --name mysql -d \
 -p 3306:3306 \
 --restart unless-stopped \
 -v mysql:/var/lib/mysql my-mysql:1.0


 echo "Press Enter to close this window..."
 read -p ""  # Ждем нажатия Enter