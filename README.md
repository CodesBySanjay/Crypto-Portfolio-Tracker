# Crypto-Portfolio-Tracker


A backend application that helps users manage and monitor their cryptocurrency investments. Users can track their holdings, fetch real-time market prices, calculate profit/loss, and configure price alerts — all through secure API endpoints.

---

## 🎯 Objective

Build a secure and beginner-friendly backend system where users can:

- ✅ Add, delete, and view crypto holdings  
- ✅ Track real-time prices using third-party APIs (e.g., CoinGecko)  
- ✅ Get notified when coins hit target prices  
- ✅ View profit/loss performance on each holding

---

## 🧩 Core Features

### 🔐 1. User Authentication Module

**Purpose:** Authenticate users and associate actions with specific sessions.

**Functionalities:**

- ✅ Register with name, email, password, and role (USER or ADMIN)  
- ✅ Login to receive a session code  
- ✅ Session-based access for portfolio and alerts  

**What You’ll Learn:**

- How to hash and verify passwords using BCrypt  
- Managing session logic with a custom store  
- Restricting access based on roles

---

### 📈 2. Holdings Management Module

**Purpose:** Allow users to record and manage their crypto investments.

**Functionalities:**

- ✅ Add new crypto: name, symbol, quantity, price  
- ✅ View full portfolio with real-time valuation  
- ✅ Delete holdings individually  

**What You’ll Learn:**

- Creating and connecting REST APIs  
- Using JPA repositories and DTOs  
- Mapping entity relationships and validations  

---

### 🌐 3. Real-Time Price Fetcher

**Purpose:** Keep investment values current using public APIs.

**Functionalities:**

- ✅ Fetch prices from CoinGecko  
- ✅ Background jobs run every 2–10 minutes  
- ✅ Used for viewing and evaluating holdings  

**What You’ll Learn:**

- Calling external APIs with RestTemplate  
- Using `@Scheduled` for periodic jobs  
- JSON deserialization with Jackson  

---

### 📊 4. Gain/Loss Calculator

**Purpose:** Evaluate crypto performance in real time.

**Calculations:**

```text
gain = (currentPrice - buyPrice) * quantity
percentage = (gain / (buyPrice * quantity)) * 100
```

**What You’ll Learn:**

- Writing reusable service logic  
- Formatting and returning calculated results  
- Performing safe arithmetic in Java  

---

### 🔔 5. Price Alert Module

**Purpose:** Let users monitor specific price targets.

**Functionalities:**

- ✅ Set alert: symbol, target price, direction (ABOVE/BELOW)  
- ✅ Periodic checks using scheduler  
- ✅ Mark alerts as “triggered” if condition is met  

**What You’ll Learn:**

- Writing rule-based conditional logic  
- Managing alert lifecycle  
- Automating checks with cron-style tasks  

---

## ⚙️ Tech Stack Overview

| Layer        | Technology                     |
|--------------|--------------------------------|
| Language     | Java 17+                       |
| Framework    | Spring Boot, Spring Data JPA   |
| Security     | Spring Security, BCrypt        |
| Scheduler    | Spring Scheduler (`@Scheduled`)|
| REST Client  | RestTemplate                   |
| JSON Parser  | Jackson                        |
| Database     | MySQL                          |
| Build Tool   | Maven                          |
| Testing      | JUnit 5                        |

---

## 📊 Diagrams

> Add the following:
- ER Diagram (Insert Image or Markdown Diagram)
- Class Diagram (Insert Image or Markdown Diagram)

---

## 🧪 API Module Overview

### 📌 Authentication

- `POST /auth/register` – Register new user  
- `POST /auth/login` – Login and receive session code  

### 📌 Portfolio

- `POST /portfolio/buy?code=xxxxxx` – Buy crypto  
- `GET /portfolio/view?code=xxxxxx` – View holdings  
- `DELETE /portfolio/delete/{id}?code=xxxxxx` – Delete a holding  

### 📌 Price Alerts

- `POST /api/alerts` – Create alert (userId in header)

### 📌 Admin

- `GET /admin/users?code=xxxxxx` – View all users (admin-only)

---

## 🧠 Key Concepts for Freshers

| Area              | What You’ll Learn                          |
|-------------------|--------------------------------------------|
| Spring Boot APIs  | Build REST endpoints                       |
| Session Security  | Handle login sessions using code           |
| BCrypt            | Secure password hashing and matching       |
| Scheduler         | Automate background checks                 |
| API Integration   | Fetch external market data                 |
| Business Logic    | Create alerts, PnL, validation              |
| Global Exceptions | Centralized error responses                |

---

## 🗂 Suggested Project Structure

```
com.crypto.tracker
├── config
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── PortfolioController.java
│   ├── AlertController.java
│   ├── AdminController.java
│   └── ExternalApiController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── SessionAuthResponse.java
│   ├── CryptoHoldingDTOs
│   └── AlertDTOs
├── model
│   ├── User.java
│   ├── CryptoHolding.java
│   ├── Alert.java
│   └── Enums (Role, Direction, Status)
├── repository
│   ├── UserRepository.java
│   ├── CryptoHoldingRepository.java
│   └── AlertRepository.java
├── scheduler
│   └── PriceUpdateScheduler.java
├── security
│   └── SessionStore.java
├── service
│   ├── UserServiceImpl.java
│   ├── PortfolioService.java
│   ├── AlertService.java
│   ├── ExternalPriceService.java
│   └── ValuationService.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── CustomExceptions.java
└── CryptoTrackerApplication.java
```

---

## 🚀 How to Run

### ✅ Prerequisites

- Java 17+  
- Maven  
- MySQL  
- Postman (for API testing)  

### 🧪 Steps

1. Clone the project:

```bash
git clone https://github.com/your-username/crypto-tracker.git
cd crypto-tracker
```

2. Create the database:

```sql
CREATE DATABASE crypto;
```

3. Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crypto
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

4. Run the application:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

5. Test APIs using Swagger UI or Postman.

---

## 📦 Optional Enhancements

| Feature             | Benefit                            |
|---------------------|------------------------------------|
| Email notifications | Better user feedback               |
| PDF / Excel export  | Downloadable portfolio reports     |
| Chart integration   | Visualize holdings + performance   |
| Mobile compatibility| Future frontend integration        |
| WebSocket support   | Real-time price updates            |

---

## 👩‍💻 Contributors

| Name                   | Role                           |
|------------------------|--------------------------------|
| Sanjay Praveen         | Project Lead, Authentication   |
| Ragul Sankar           | Price Tracking Module          |
| Sandhiya R             | Portfolio Management Module    |
| K Harshitha Chowdary   | Alerts, Price Threshold Logic  |
| Preetha Varadarajan    | Diagram Designs, Testing       |
