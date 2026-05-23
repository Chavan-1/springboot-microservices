# Spring Boot Microservices Training

Hands-on implementation completed during 5-day Spring Boot Microservices training.

## Microservices Implemented

- Eureka Server
- API Gateway
- Product Service
- Order Service
- Payment Service

## Concepts Covered

- Microservices Architecture
- Eureka Service Discovery
- Spring Cloud Gateway
- OpenFeign Client
- WebClient
- Inter-service Communication
- DTO Pattern
- Custom Exception Handling
- Circuit Breaker (Resilience4j)
- Logging using Log4j2
- MySQL + JPA
- API Routing
- Load Balancing

## Flow

Order Service
   → Product Service
   → Payment Service

API Gateway
   → Product Service
   → Order Service
   → Payment Service
