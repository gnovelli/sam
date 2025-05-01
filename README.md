
# API Key Management Service

A Spring Boot application written in Kotlin that provides API key management functionality with secure authentication.

## Features

- Create, read, update, and delete API keys
- API key authentication for protected endpoints
- API key expiration management
- Project and owner-based key organization
- Swagger/OpenAPI documentation
- InfluxDB integration for key storage (with in-memory fallback)

## Tech Stack

- Kotlin
- Spring Boot
- Spring Security
- InfluxDB
- Gradle

## Getting Started

1. Clone the repository
2. Configure `application.yml` with your InfluxDB settings
3. Run the application:
```bash
./gradlew bootRun
```

The application will start on port 5000.

## API Documentation

Once the application is running, access the Swagger UI documentation at:
- `/swagger-ui/index.html`

## API Endpoints

### API Key Management
- `POST /api/keys` - Create new API key
- `GET /api/keys` - List all API keys
- `GET /api/keys/{id}` - Get API key by ID
- `DELETE /api/keys/{id}` - Delete API key
- `PUT /api/keys/{id}/deactivate` - Deactivate API key

### Protected Endpoints
- `GET /api/hello` - Example protected endpoint

## Authentication

Protected endpoints require an API key to be included in the request header:
```
X-API-KEY: your-api-key-here
```

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/
│   │       └── apikeys/
│   │           ├── config/       # Configuration classes
│   │           ├── controller/   # REST controllers
│   │           ├── dto/         # Data Transfer Objects
│   │           ├── exception/   # Exception handling
│   │           ├── model/       # Domain models
│   │           ├── repository/  # Data access layer
│   │           ├── security/    # Security configuration
│   │           ├── service/     # Business logic
│   │           └── util/        # Utility classes
│   └── resources/
└── test/
    └── kotlin/                  # Test classes
```

## Testing

Run tests using:
```bash
./gradlew test
```

## License

See the LICENSE file for details.
