# Task Tracker Backend (Spring Boot)

## 📌 Objective

This project is a **Spring Boot backend application** built using **Java, Spring Boot, Spring Security, Hibernate/JPA, and MySQL**. It demonstrates the ability to design and implement a **complete backend system** with **role-based access control (RBAC)**.

The application manages **tasks** where:

* **Admins** can create and manage tasks for **all users**.
* **Regular users** can create and manage **only their own tasks**.

---

## 🛠️ Tech Stack

* **Java**
* **Spring Boot**
* **Spring Security (JWT Authentication & Authorization)**
* **Spring Data JPA (Hibernate)**
* **MySQL**
* **Maven**

---

## 🏗️ Architecture & Design

The project follows **Clean Architecture principles**, with clear separation of concerns:

```
config        → Security configuration & JwtAuthenticationFilter
controller    → REST API controllers
dto           → Data Transfer Objects
entity        → JPA entities (User, Task, Role)
enums         → Enum definitions (Role Name, Task Status)
exception     → Custom Exceptions, Exception Handlers, Model Responses
mapper        → Entity ↔ DTO mapping  (Task Mapper , User Mapper)
repository    → JPA repositories (Role Repository, Task Repository, User Repository)
service       → Business logic layer
```

---

## 🔐 Authentication & Authorization

Authentication and authorization are implemented using **JWT (JSON Web Tokens)**.

### Roles

* **ADMIN**
* **USER**

### Access Rules

* **ADMIN**: Full access to all tasks and users
* **USER**: Access limited to their own tasks

---

## 🔑 Auth APIs

| HTTP Method | Endpoint             | Description                      |
| ----------- | -------------------- | -------------------------------- |
| POST        | `/api/auth/register` | Register a new user              |
| POST        | `/api/auth/login`    | Authenticate user and return JWT |

---

## 👤 User APIs

| HTTP Method | Endpoint          | Description      |
| ----------- | ----------------- | ---------------- |
| GET         | `/api/users/{id}` | Get user profile |

---

## ✅ Task APIs

| HTTP Method | Endpoint                 | Description                                       |
| ----------- | ------------------------ | ------------------------------------------------- |
| POST        | `/api/tasks`             | Create a task                                     |
| GET         | `/api/tasks`             | List tasks (filter by status, assignee, priority) |
| GET         | `/api/tasks/{id}`        | Get a specific task                               |
| PUT         | `/api/tasks/{id}`        | Update full task details                          |
| PATCH       | `/api/tasks/{id}/status` | Update only task status                           |
| DELETE      | `/api/tasks/{id}`        | Delete a task                                     |

---

## 👮 Role-Based Capabilities

### Admin Can:

* Get list of all tasks
* Get any task by ID
* Add tasks for any user
* Update any task
* Delete any task

### User Can:

* Get their own tasks only
* Get a task by ID (if owned)
* Create tasks for themselves
* Update their own tasks
* Delete their own tasks

---

## 🗄️ Database Setup (MySQL)

### Create Database

```sql
CREATE DATABASE IF NOT EXISTS task_tracker;
USE task_tracker;
```

### Tables & Sample Data

```sql
-- Create database if does not exist 
   CREATE DATABASE IF NOT EXISTS `task_tracker;
   USE `task_tracker`;

-- Drop old tables if they exist
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

-- Role table
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('ADMIN','USER') NOT NULL UNIQUE
);

-- User table
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Task table
CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status ENUM('OPEN', 'IN_PROGRESS', 'DONE') DEFAULT 'OPEN',
    created_by BIGINT NOT NULL,
    assigned_to BIGINT,
    FOREIGN KEY (created_by) REFERENCES user(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES user(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Insert roles
INSERT INTO role (name) VALUES ('ADMIN'), ('USER');

-- Insert Users
INSERT INTO user (username, email, password, role_id) VALUES
('mohammed', 'mohammed@example.com', 'pass123', 1),  -- ADMIN
('sara', 'sara@example.com', 'saraSecure!', 2),      -- USER
('ahmed', 'ahmed@example.com', 'ahmed2025', 2),      -- USER
('fatma', 'fatma@example.com', 'fatma@123', 2),      -- USER
('ali', 'ali@example.com', 'ali546', 1); -- ADMIN

-- Insert Tasks
INSERT INTO task (title, description, status, created_by, assigned_to) VALUES
('Setup project', 'Initialize Spring Boot project and setup MySQL DB', 'DONE', 1, 2),
('Design API', 'Create REST API endpoints for user and task management', 'IN_PROGRESS', 1, 3),
('Write documentation', 'Prepare project documentation and API guide', 'OPEN', 2, NULL),
('UI Prototype', 'Design a simple frontend prototype for task tracker', 'IN_PROGRESS', 3, 4),
('Database backup', 'Schedule automatic database backups', 'OPEN', 5, NULL);

```

---

## 🚀 Project Setup & Run

### 1️⃣ Clone Repository

```bash
git clone https://github.com/MohammedEmadEldinMahmoud/TaskTracker.git
cd task-tracker
```

### 2️⃣ Configure Application Properties

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_tracker
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.show-sql=true

```

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing

You can test the APIs using:

* Postman
* cURL
* Swagger (if enabled)

⚠️ Make sure to include the JWT token in the `Authorization` header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🎯 Key Features Demonstrated

* RESTful API design
* JWT-based authentication & authorization
* Role-based access control
* Clean Architecture best practices
* JPA entity relationships
* Secure API access

---

## 📌 Future Enhancements

* Pagination & sorting
* Swagger/OpenAPI documentation
* Dockerization
* Frontend integration

---




