# Account Management Service

## Overview
Account Management Service is a Spring Boot application that provides APIs for managing user accounts and their subscriptions to various services. It allows creating and managing users, as well as adding, removing, and tracking subscriptions.

## Features
- User management (create, read, update, delete)
- Subscription management for users
- Tracking of popular subscriptions

## Technologies
- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Liquibase for database migrations
- TestContainers for integration testing
- OpenAPI/Swagger for API documentation
- Lombok for reducing boilerplate code
- Maven for build management

## Prerequisites

### For Local Development
- Java 21 or higher
- Maven 3.6 or higher
- Docker (for running TestContainers)

### For Docker Deployment
- Docker 20.10 or higher
- Docker Compose v2 or higher

## Setup and Running

### Using Maven
1. Clone the repository
2. Build the project:
   ```
   mvn clean install
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```
4. The application will be available at http://localhost:8080
5. Swagger UI is available at http://localhost:8080/swagger-ui.html

### Using Docker
1. Clone the repository
2. Build and run using Docker Compose:
   ```
   docker-compose up -d
   ```
3. The application will be available at http://localhost:8080
4. Swagger UI is available at http://localhost:8080/swagger-ui.html
5. To stop the application:
   ```
   docker-compose down
   ```
6. To stop the application and remove volumes (this will delete all data):
   ```
   docker-compose down -v
   ```

## API Endpoints

### User Management
- `POST /users` - Create a new user
- `GET /users/{id}` - Get a user by ID
- `PUT /users/{id}` - Update a user
- `DELETE /users/{id}` - Delete a user

### Subscription Management
- `POST /users/{id}/subscriptions` - Add a subscription for a user
- `GET /users/{id}/subscriptions` - Get all subscriptions for a user
- `DELETE /users/{id}/subscriptions/{sub_id}` - Delete a subscription
- `GET /subscriptions/top` - Get top popular subscriptions

## Database Schema

The application uses the following database tables:

### users
```sql
create table users
(
    id       uuid primary key not null,
    username varchar unique   not null
);
```

### services
```sql
create table services
(
    id   uuid primary key not null,
    name varchar unique   not null
);
```

### subscriptions
```sql
create table subscriptions
(
    id         uuid primary key not null,
    user_id    uuid references users (id),
    service_id uuid references services (id),
    price      numeric          not null,
    status     varchar          not null default 'active',
    start_date timestamp        not null,
    end_date   timestamp
);
```

## Configuration

### Local Development
The application is configured to use TestContainers for the database in local development, which means it will automatically start a PostgreSQL container when the application runs. This makes it easy to run the application without setting up a separate database.

### Docker Environment
When running with Docker, the application uses a dedicated PostgreSQL container as defined in the docker-compose.yml file. The application is configured to connect to this database using the docker profile, which is automatically activated when running in the Docker container. Database migrations are handled by Liquibase during application startup.

## License
This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contact
For any questions or support, please contact the Account Management Team at support@accountmanagement.app.
