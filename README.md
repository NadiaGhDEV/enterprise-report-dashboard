# Enterprise Report Dashboard

A backend-focused **enterprise report management system** built with **Java and Spring Boot**.

The system provides **JWT-based stateless authentication**, role-based authorization, dynamic department-level access control, and flexible report management with numeric data stored as **JSONB in PostgreSQL**.

## Features

* Stateless authentication with JWT
* Three access levels: `ADMIN`, `MANAGER`, `USER`
* Department-based report management
* Dynamic department-level access control
* Users can be granted access to multiple departments
* Reports with descriptions and dynamic numeric data tables
* Report data designed for frontend visualization using pie, bar, and line charts
* Numeric table data stored as PostgreSQL `JSONB`
* Real-time permission checks against the database
* Ownership and department-level access verification to prevent unauthorized resource access
* Docker / Docker Compose support

## Tech Stack

| Layer                 | Technology                  |
| --------------------- | --------------------------- |
| Language              | Java 17                     |
| Framework             | Spring Boot 4.1.0           |
| Web                   | Spring MVC                  |
| Security              | Spring Security + JWT       |
| JWT Library           | JJWT 0.12.6                 |
| Database              | PostgreSQL                  |
| ORM                   | Spring Data JPA / Hibernate |
| Validation            | Jakarta Bean Validation     |
| Template Engine       | Thymeleaf                   |
| Build Tool            | Maven                       |
| Containerization      | Docker / Docker Compose     |
| Boilerplate Reduction | Lombok                      |

## Roles and Access Model

| Role        | Access                                                                                         |
| ----------- | ---------------------------------------------------------------------------------------------- |
| **ADMIN**   | Manage user accounts, manage departments, grant/revoke department access, and view all reports |
| **MANAGER** | Read-only access to reports within assigned departments                                        |
| **USER**    | Create, edit, delete, and view reports within authorized departments                           |

### Department Permissions

Each user's department access is modeled as a **many-to-many relationship**.

A user can be granted access to multiple departments simultaneously, and the administrator can grant or revoke access at any time.

Department permissions are checked against the database in real time on each request. Therefore, permission changes take effect immediately without requiring the user to log in again.

## Authentication & Authorization

The application uses **stateless JWT authentication** with Spring Security.

### Authentication Flow

```text
Client
  │
  │ POST /login
  ▼
Spring Security
  │
  │ Validate credentials
  ▼
JWT Service
  │
  │ Generate JWT
  ▼
Client
  │
  │ Authorization: Bearer <token>
  ▼
JWT Filter
  │
  │ Validate & extract user information
  ▼
Security Context
  │
  ▼
Controller → Service → Database
```

The application uses:

* `SessionCreationPolicy.STATELESS`
* JWT-based authentication
* URL-level authorization
* Method-level authorization with `@PreAuthorize`
* Service-layer ownership and department permission checks
* Database-backed permission verification

This layered approach prevents relying solely on URL-level restrictions and helps protect resources against unauthorized access and IDOR-style attacks.

## Data Model

```text
Account
(userId, firstName, lastName, username, password, role)
       │
       │ ManyToMany
       ▼
Department
(departmentId, departmentName, active)

Account
   │
   │ OneToMany
   ▼
Report
(id, title, description, columns[JSON], rows[JSON], createdAt)
```

### Report Data

Each report contains:

* A title
* A short description
* A dynamic list of column names
* A list of rows containing numeric values
* Creation timestamp

The numeric table is stored as `JSONB` in PostgreSQL.

Example:

```json
{
  "columns": ["January", "February", "March"],
  "rows": [
    {
      "name": "Sales",
      "values": [120, 150, 180]
    },
    {
      "name": "Expenses",
      "values": [80, 90, 100]
    }
  ]
}
```

The number of numeric values in each row must match the number of defined columns. This consistency is validated at the DTO/input-validation level.

## API Reference

### Authentication

| Method | Endpoint | Access | Description                    |
| ------ | -------- | ------ | ------------------------------ |
| `POST` | `/login` | Public | Authenticate and receive a JWT |

### Admin

| Method   | Endpoint                                        | Description               |
| -------- | ----------------------------------------------- | ------------------------- |
| `POST`   | `/admin/addAccount`                             | Create a new user account |
| `GET`    | `/admin/allAccounts`                            | Get all user accounts     |
| `POST`   | `/admin/changePass`                             | Change a user's password  |
| `PUT`    | `/admin/permissions/{userId}?departmentId={id}` | Grant department access   |
| `DELETE` | `/admin/permissions/{userId}`                   | Revoke department access  |
| `POST`   | `/admin/department/addDepartment`               | Create a department       |
| `PUT`    | `/admin/department/{id}/disable`                | Disable a department      |
| `GET`    | `/admin/department/allDepartments`              | Get all departments       |

### User Profile

| Method | Endpoint         | Description                        |
| ------ | ---------------- | ---------------------------------- |
| `GET`  | `/profile`       | Get the current user's profile     |
| `GET`  | `/myDepartments` | Get the current user's departments |

### Reports

| Method   | Endpoint              | Access                 | Description                           |
| -------- | --------------------- | ---------------------- | ------------------------------------- |
| `POST`   | `/reports`            | USER                   | Create a report                       |
| `GET`    | `/reports/{id}`       | ADMIN / MANAGER / USER | Get a report with access verification |
| `PUT`    | `/reports/{id}`       | USER                   | Update a report                       |
| `DELETE` | `/reports/{id}`       | USER                   | Delete a report                       |
| `GET`    | `/reports`            | ADMIN                  | Get all reports                       |
| `GET`    | `/reports/myReports`  | USER                   | Get the current user's reports        |
| `GET`    | `/reports/getReports` | MANAGER                | Get reports from assigned departments |

## Project Structure

```text
src/main/java/com/faniherfeei/demo1dashboard/
│
├── config/
│   └── Security configuration and JWT filter
│
├── controller/
│   └── REST controllers
│
├── dto/
│   └── Request / Response DTOs
│
├── exception/
│   └── Custom exceptions and global exception handling
│
├── mapper/
│   └── Entity ↔ DTO mapping
│
├── model/
│   └── JPA entities
│
├── repository/
│   └── Spring Data JPA repositories
│
└── service/
    └── Business logic
```

## Getting Started

### Prerequisites

Make sure you have the following installed:

* JDK 17+
* Maven
* PostgreSQL

### 1. Clone the Repository

```bash
git clone https://github.com/NadiaGhDEV/enterprise-report-dashboard.git
cd enterprise-report-dashboard
```

### 2. Create the Database

Create a PostgreSQL database:

```sql
CREATE DATABASE dashboard_db;
```

Then create the application schema:

```sql
CREATE SCHEMA dashboard;
```

### 3. Configure the Application

Configure your PostgreSQL connection and JWT settings in the application configuration.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dashboard_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false

jwt.secret=your_secret
jwt.expiration=7200000
```

> Never commit your real database credentials or JWT secret to GitHub. Use environment variables or another secure configuration mechanism for sensitive values.

### 4. Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## Docker

The project also supports running the application using Docker and Docker Compose.

This allows the backend and PostgreSQL database to be started together without requiring a local PostgreSQL installation.

```bash
docker compose up --build
```

To stop the containers:

```bash
docker compose down
```

## Security Notes

* Authentication is stateless and based on JWT.
* Passwords are not stored in plain text.
* Endpoint-level authorization is enforced through Spring Security.
* Method-level authorization is used where appropriate.
* Resource ownership and department permissions are checked at the service/data-access level.
* Department permissions are retrieved from the database during request processing.
* Users cannot access reports solely by knowing their IDs.
* JWT expiration is configured to limit the lifetime of access tokens.

## Known Limitations

* No refresh-token mechanism is currently implemented.
* Access tokens currently have a fixed expiration period.
* Account activation/deactivation is not fully implemented yet.
* A `MANAGER` can currently be assigned to multiple departments.

## Future Improvements

* Refresh token mechanism
* Account activation/deactivation
* Frontend dashboard with interactive charts
* Advanced report filtering and analytics
* Improved production deployment configuration
* Additional security hardening
* Expanded automated test coverage
* API documentation with OpenAPI / Swagger

## License

This project is currently intended as a personal portfolio and educational project.

## Author

**Nadia**

Computer Engineering Student
Java / Spring Boot Backend Developer
