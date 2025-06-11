# JSONPlaceholder Clone API

A RESTful API clone of JSONPlaceholder with JWT authentication, built using Spring Boot.

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Docker (optional, for running PostgreSQL in a container)

## Database Setup

### Option 1: Using Docker (Recommended)

1. Start PostgreSQL container:
```bash
docker run --name jsonplaceholder-db \
  -e POSTGRES_DB=jsonplaceholder \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5440:5432 \
  -d postgres:latest
```

### Option 2: Using Local PostgreSQL

1. Create a database named `jsonplaceholder`
2. Update `application.yml` with your database credentials if different from defaults

## Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

#### Register a new user
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

### Users API

All user endpoints require authentication. Include the JWT token in the Authorization header:
```bash
-H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Get all users
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Get user by ID
```bash
curl -X GET http://localhost:8080/users/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Create user
```bash
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New User",
    "username": "newuser",
    "email": "new@example.com",
    "password": "password123",
    "address": {
      "street": "123 Main St",
      "suite": "Apt 4B",
      "city": "New York",
      "zipcode": "10001",
      "geo": {
        "lat": "40.7128",
        "lng": "-74.0060"
      }
    },
    "phone": "1-234-567-8900",
    "website": "example.com",
    "company": {
      "name": "Example Corp",
      "catchPhrase": "Making things better",
      "bs": "synergize scalable supply-chains"
    }
  }'
```

#### Update user
```bash
curl -X PUT http://localhost:8080/users/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name",
    "username": "updateduser",
    "email": "updated@example.com"
  }'
```

#### Delete user
```bash
curl -X DELETE http://localhost:8080/users/{id} \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Additional API Usage Examples

### Authentication Examples

#### Register with Invalid Data
```bash
# Missing required fields
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "invalid-email"
  }'

# Duplicate username
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Another User",
    "username": "johndoe",
    "email": "another@example.com",
    "password": "password123"
  }'
```

#### Login with Invalid Credentials
```bash
# Wrong password
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "wrongpassword"
  }'

# Non-existent user
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nonexistent",
    "password": "password123"
  }'
```

### User Management Examples

#### Get User with Invalid ID
```bash
curl -X GET http://localhost:8080/users/999 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Update User with Partial Data
```bash
# Update only name
curl -X PUT http://localhost:8080/users/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name Only"
  }'

# Update address only
curl -X PUT http://localhost:8080/users/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "address": {
      "street": "New Street",
      "suite": "New Suite",
      "city": "New City",
      "zipcode": "12345",
      "geo": {
        "lat": "40.7128",
        "lng": "-74.0060"
      }
    }
  }'
```

#### Create User with Minimal Data
```bash
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Minimal User",
    "username": "minimal",
    "email": "minimal@example.com",
    "password": "password123"
  }'
```

### Error Handling Examples

#### Access Without Authentication
```bash
# Try to get users without token
curl -X GET http://localhost:8080/users

# Try to get users with invalid token
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer invalid.token.here"
```

#### Invalid Request Format
```bash
# Malformed JSON
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Invalid User",
    "username": "invalid",
    "email": "invalid@example.com",
    "password": "password123"
    missing closing brace
```

### Batch Operations

#### Get Multiple Users by IDs
```bash
# Get users 1, 2, and 3
curl -X GET "http://localhost:8080/users/1,2,3" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Search Users
```bash
# Search users by name
curl -X GET "http://localhost:8080/users/search?name=John" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Search users by email domain
curl -X GET "http://localhost:8080/users/search?email=@example.com" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Response Headers

All responses include standard headers:
- `Content-Type: application/json`
- `X-Request-ID`: Unique request identifier
- `X-Rate-Limit-Remaining`: Remaining API calls in current time window

Example of checking headers:
```bash
curl -I -X GET http://localhost:8080/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Testing

Run the tests using Maven:
```bash
mvn test
```

## Configuration

The application uses the following default configuration in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5440/jsonplaceholder
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

jwt:
  secret: your-secret-key
  expiration: 86400000 # 24 hours
```

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Stateless session management

## Technologies Used

- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT for authentication
- Maven
- Lombok
- JUnit 5 for testing 