# Spring Boot 3 + Apache Kafka Microservices Project (Chapters 1, 2 & 3)

Complete, production-structured microservices project compatible with **Java 17**, **Maven 3.x**, **Spring Boot 3.3.5**, and **Docker 29.x**.

## Project Structure
```text
spring-kafka-microservices/
├── docker-compose.yml               # Chapter 3: Full stack (Kafka KRaft + Both Services)
├── employee-service/                # Chapter 1: REST API + Kafka Producer (Port 8081)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/...
└── notification-service/            # Chapter 2: Kafka Consumer + Email Validation (Port 8082)
    ├── Dockerfile
    ├── pom.xml
    └── src/main/...
```

---

## Running Chapter 1 & Chapter 2 Locally (Without Dockerizing Apps)

### Step 1: Start Kafka Broker Only
You can start just Kafka using Docker Compose:
```bash
docker compose up -d kafka
```

### Step 2: Run Employee Service (Chapter 1)
```bash
cd employee-service
mvn spring-boot:run
```
Service runs on `http://localhost:8081`.

### Step 3: Run Notification Service (Chapter 2)
Open a second terminal:
```bash
cd notification-service
mvn spring-boot:run
```
Service runs on `http://localhost:8082`.

---

## Running Chapter 3 (Full Docker Compose Stack)

Build and start Kafka, `employee-service`, and `notification-service` together:
```bash
docker compose up --build
```

---

## Testing the API (All Chapters)

### 1. Valid Employee Request (Triggers Notification)
```bash
curl -i -X POST http://localhost:8081/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "department": "Engineering"
  }'
```
* **Expected HTTP Status:** `201 Created`
* **Notification Service Log:** `Notification sent to: alice.johnson@example.com`

### 2. Invalid Email Request (Tests Consumer Validation)
```bash
curl -i -X POST http://localhost:8081/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Smith",
    "email": "invalid-email-format",
    "department": "Marketing"
  }'
```
* **Expected HTTP Status:** `201 Created` (Producer accepts & publishes)
* **Notification Service Log:** `ERROR ... Invalid email format received: 'invalid-email-format'. Skipping notification.`

### 3. Get All Stored Employees (Helper Endpoint)
```bash
curl -X GET http://localhost:8081/api/employees
```
