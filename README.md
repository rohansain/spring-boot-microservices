# Microservices E-Commerce Backend

A Spring Boot microservices-based backend project demonstrating service-to-service communication, service discovery, centralized configuration, API Gateway, load balancing, and resilience patterns.

## Architecture

The project consists of:

* **User Service** – manages user-related functionality
* **Product Service** – manages product-related functionality
* **Order Service** – handles order-related operations and communicates with Product Service
* **API Gateway** – single entry point for external client requests
* **Eureka Service Registry** – service discovery
* **Config Server** – centralized configuration management
* **Git Config Repository** – stores centralized configuration
* **RabbitMQ + Spring Cloud Bus** – broadcasts configuration refresh events

---

## Implemented Features

### 1. Microservices Architecture

Services are separated based on business functionality:

```text
User Service
Product Service
Order Service
```

Each service is independently developed and can be independently deployed and scaled.

---

### 2. API Gateway

Implemented an API Gateway as the single entry point for external clients.

```text
Client
   ↓
API Gateway
   ↓
Microservices
```

Responsibilities:

* Request routing
* Single entry point for clients
* Hides internal service URLs
* Can be extended for authentication, logging, rate limiting, etc.

Gateway runs on:

```text
localhost:8080
```

---

### 3. Eureka Service Registry

Implemented **Netflix Eureka Server** for service discovery.

Services register themselves with Eureka:

```text
User Service
Product Service
Order Service
       ↓
   Eureka Server
```

Eureka allows services to discover other services without hardcoding their host and port.

Eureka Server:

```text
localhost:8761
```

---

### 4. Service-to-Service Communication

Implemented **Spring Cloud OpenFeign** for communication between microservices.

Example:

```text
Order Service
      ↓
  ProductClient
      ↓
Product Service
```

Order Service communicates with Product Service using the service name:

```java
@FeignClient(name = "product-service")
```

The Order Service does not need to know the Product Service's fixed IP address.

---

### 5. Client-Side Load Balancing

Implemented **Spring Cloud LoadBalancer** with Eureka and OpenFeign.

Multiple Product Service instances can run:

```text
Product Service :8082
Product Service :8084
Product Service :8085
```

All instances register with Eureka.

When Order Service calls:

```text
product-service
```

Eureka provides the available instances and Spring Cloud LoadBalancer selects an instance to handle the request.

```text
                 ┌── Product :8082
Order Service → Eureka ── Product :8084
                 └── Product :8085
                         ↑
                    LoadBalancer
```

---

### 6. Centralized Configuration

Implemented **Spring Cloud Config Server** to manage configuration centrally.

```text
Git Config Repository
          ↓
    Config Server
          ↓
   Microservices
```

Instead of maintaining configuration separately in every service, common configuration can be stored in a central Git repository.

---

### 7. Git-Based Configuration

Configuration files are maintained in a separate Git repository.

Example:

```text
product-service.yml
order-service.yml
user-service.yml
application.yml
```

Config Server reads configuration from the Git repository and provides it to the microservices.

---

### 8. Configuration Refresh

Implemented configuration refresh using Spring Boot Actuator.

When configuration changes:

```text
Git Repository
      ↓
Config Server
      ↓
Actuator Refresh
      ↓
Running Service
```

This allows supported configuration to be refreshed without restarting the service.

---

### 9. Spring Cloud Bus

Implemented **Spring Cloud Bus** for broadcasting configuration refresh events between services.

```text
Config Change
     ↓
Spring Cloud Bus
     ↓
RabbitMQ
     ↓
All Connected Services
```

RabbitMQ acts as the message broker.

This avoids manually refreshing every service individually.

---

### 10. Circuit Breaker

Implemented **Circuit Breaker using Resilience4j** for service-to-service communication.

Example:

```text
Order Service
     ↓
Product Service
```

If Product Service becomes unavailable, the Circuit Breaker prevents continuous calls from repeatedly hitting the failing service.

Circuit Breaker states:

```text
CLOSED
   ↓
OPEN
   ↓
HALF_OPEN
   ↓
CLOSED
```

---

### 11. Fallback

Implemented fallback handling with the Circuit Breaker.

If Product Service is unavailable, Order Service returns a fallback response instead of continuously waiting for the failed service.

Example:

```text
Product Service DOWN
        ↓
Circuit Breaker
        ↓
Fallback
        ↓
"Product service unavailable"
```

---

## Technologies Used

* Java 21
* Spring Boot
* Spring Cloud
* Spring Web
* Spring Data JPA
* PostgreSQL
* Spring Cloud Gateway
* Spring Cloud Netflix Eureka
* Spring Cloud OpenFeign
* Spring Cloud LoadBalancer
* Spring Cloud Config
* Spring Cloud Bus
* RabbitMQ
* Resilience4j
* Spring Boot Actuator
* Lombok
* Maven
* Git / GitHub
* Postman

---

## Service Ports

| Component       | Port |
| --------------- | ---: |
| Eureka Server   | 8761 |
| Config Server   | 8888 |
| API Gateway     | 8080 |
| User Service    | 8081 |
| Product Service | 8082 |
| Order Service   | 8083 |

Additional Product Service instances can run on different ports for load-balancing demonstrations.

---

## Request Flow

### External Request

```text
Client
  ↓
API Gateway
  ↓
Required Microservice
```

### Internal Service Communication

```text
Order Service
     ↓
OpenFeign
     ↓
Eureka
     ↓
LoadBalancer
     ↓
Product Service Instance
```

### Configuration Flow

```text
Git Repository
      ↓
Config Server
      ↓
Microservices
```

### Configuration Refresh Flow

```text
Git
 ↓
Config Server
 ↓
Spring Cloud Bus
 ↓
RabbitMQ
 ↓
Microservices
```

### Failure Handling Flow

```text
Order Service
     ↓
OpenFeign
     ↓
Circuit Breaker
     ↓
Product Service
     ↓
Failure
     ↓
Fallback
```

---

## Resilience Patterns Studied

The following Resilience4j concepts were studied for future implementation:

* Circuit Breaker — **Implemented**
* Fallback — **Implemented**
* Retry — **Not implemented**
* Time Limiter — **Not implemented**
* Rate Limiter — **Not implemented**
* Bulkhead — **Not implemented**

---

## Project Learning

This project demonstrates the complete evolution of a microservices architecture:

```text
Microservices
      ↓
API Gateway
      ↓
Service Discovery
      ↓
OpenFeign
      ↓
Load Balancing
      ↓
Centralized Configuration
      ↓
Configuration Refresh
      ↓
Spring Cloud Bus + RabbitMQ
      ↓
Circuit Breaker + Fallback
```

The project was developed as a learning/mock microservices project to understand how these components work together in a distributed backend system.
