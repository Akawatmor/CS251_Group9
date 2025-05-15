# Backend System Installation Guide

This guide explains how to set up and run the backend system for the TUVaporr Game Platform.

---

## Prerequisites

- **Java 17** or later (JDK)
- **Maven** (for building the project)
- **MySQL** database server
- **Node.js** (if you plan to run the frontend, not covered here)

---

## 1. Clone the Repository

Clone the project to your local machine:

```sh
git clone <your-repo-url>
cd CS251_Group9/backend
```

---

## 2. Configure the Database

1. Make sure MySQL is running.
2. Update the database connection settings in [`src/main/resources/application.properties`](src/main/resources/application.properties) if needed (host, port, username, password, database name).

Default settings are:
```
spring.datasource.url=jdbc:mysql://petchsko123.trueddns.com:56268/CS251Group9Database?...
spring.datasource.username=DatabaseEditUser
spring.datasource.password=CS251Group9DatabaseEdit
```

---

## 3. Build the Backend

Use Maven to build the project:

```sh
./mvnw clean package
```
or (if you have Maven installed globally):
```sh
mvn clean package
```

---

## 4. Run the Backend

You can run the backend using the Spring Boot Maven plugin:

```sh
./mvnw spring-boot:run
```
or
```sh
mvn spring-boot:run
```

Alternatively, run the generated WAR file:

```sh
java -jar target/backend-0.0.1-SNAPSHOT.war
```

---

## 5. File Uploads

Uploaded files (game icons, pictures, executables, user profiles, etc.) are stored in the `uploads/` directory. Make sure this directory exists and is writable.

---

## 6. Accessing the API

The backend will start on the default port (usually 8080). You can access the API at:

```
http://localhost:8080/api/
```

Refer to the API documentation in [`src/main/resources/document/api-documentation.md`](src/main/resources/document/api-documentation.md) for available endpoints and usage.

---

## 7. Troubleshooting

- Ensure your database is running and accessible.
- Check that the `uploads/` directory has the correct permissions.
- Review logs for errors if the application fails to start.

---

## 8. Additional Notes

- For production, configure environment variables and secure your database credentials.
- You may need to adjust CORS settings in [`BackendApplication.java`](src/main/java/cs251/group9/backend/BackendApplication.java) if accessing from a different domain.

---

**For more details, see the README.md and API documentation.**