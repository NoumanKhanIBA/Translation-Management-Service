## Features

- **CRUD** operations for Translations (`key`, `locale`, `content`, `tags`)  
- **Search** by locale, key‐fragment, content‐fragment, any/all tags  
- **Export** full locale → JSON map  
- **JWT‐based authentication** (`/api/auth/login`)  
- **In‐memory H2** (dev/tests), **PostgreSQL** (prod)  
- **Swagger UI** & **OpenAPI 3**  
- **Docker** multi‐stage build + **docker‐compose**  
- **CDN caching** headers on static assets & export endpoint  
- **Performance**: p95 < 200 ms for search, < 500 ms for export  

---

## Prerequisites

- Java 17  
- Maven 3.8+ (or Gradle 7+)  
- Docker & Docker Compose  
- (Optional) PostgreSQL 15+ for production mode  

---

## Installation & Running

### Local (H2)

1. Build the JAR:
   ```bash
   mvn clean package
