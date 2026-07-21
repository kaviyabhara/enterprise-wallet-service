# Enterprise Wallet API Service

A robust, production-ready RESTful API built with **Spring Boot** and **Java 17** for managing digital wallet transactions. Designed to handle high-concurrency wallet operations safely with pessimistic locking, double-entry audit logging, and automated API documentation.

---

## 🚀 Key Features

* **Account & Wallet Management**: Create user accounts and initialize digital wallets with configurable initial balances.
* **Concurrency Control**: Utilizes DB-level pessimistic write locking (`PESSIMISTIC_WRITE`) to ensure data consistency and prevent race conditions during simultaneous deposit/withdrawal operations.
* **Double-Entry Ledger Audit**: Stores immutable transaction history logs tracking every financial debit/credit with timestamped references.
* **Robust Error Handling**: Centralized global exception management returning structured JSON error payloads with accurate HTTP status codes.
* **Interactive API Documentation**: Integrated OpenAPI / Swagger UI for real-time testing and exploration of API endpoints.
* **Production Observability**: Configured Spring Boot Actuator endpoints for real-time application health monitoring.

---

## 🛠️ Tech Stack & Dependencies

* **Language**: Java 17
* **Framework**: Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Boot Actuator)
* **Database**: MySQL / H2 Database
* **Documentation**: Springdoc OpenAPI / Swagger UI
* **Build Tool**: Apache Maven

---

## 🔌 API Endpoints Summary

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/wallets` | Create a new digital wallet |
| `GET` | `/api/v1/wallets/{id}` | Fetch wallet details and current balance |
| `POST` | `/api/v1/wallets/{id}/deposit` | Deposit funds into a wallet |
| `POST` | `/api/v1/wallets/{id}/withdraw` | Withdraw funds from a wallet |
| `GET` | `/api/v1/wallets/{id}/transactions` | Retrieve complete transaction history |
| `GET` | `/actuator/health` | Service health status check |

---

## ⚙️ How to Run Locally

### Prerequisites
* **JDK 17** or higher
* **Maven 3.8+**
* **MySQL** (Optional: H2 in-memory DB enabled by default for quick testing)

### Setup Steps
1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/kaviyabhara/enterprise-wallet-service.git](https://github.com/kaviyabhara/enterprise-wallet-service.git)
   cd enterprise-wallet-service
