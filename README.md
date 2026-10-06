# Parfumerie - Web Application

Full-stack web application for perfumery management built with Java Spring Boot and React.

## Technologies
- **Backend:** Java 17, Spring Boot 3.3.4, Spring Security, JWT, JPA/Hibernate, MySQL
- **Frontend:** React, Axios, React Router
- **Database:** MySQL

## How to run

### Backend
1. Create MySQL database `parfumery`
2. Configure `application.properties` with your credentials
3. Run `mvn spring-boot:run`
4. Backend runs on `http://localhost:8080`

### Frontend
1. `cd parfumeryfront`
2. `npm install`
3. `npm start`
4. Frontend runs on `http://localhost:3000`

## Project structure

```text
perfumery-web-app
 ├── perfumeryproject
 └── perfumeryfront
```

## Features
- Email verification on registration
- JWT authentication with CLIENT/ADMIN roles
- Brand and perfume catalogue management
- Shopping cart and order creation
- PDF order confirmation via email
- Admin order management
