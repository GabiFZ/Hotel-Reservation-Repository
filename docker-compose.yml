services:
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: mydatabase
      POSTGRES_USER: myuser
      POSTGRES_PASSWORD: secret
    volumes:
      - pgdata:/var/lib/postgresql/data
    networks:
      - internal_net
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U myuser -d mydatabase"]
      interval: 5s
      timeout: 3s
      retries: 20

  backend:
    image: hotel-reservation-repository-main-backend:latest
    environment:
      SERVER_PORT: 8080
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/mydatabase
      SPRING_DATASOURCE_USERNAME: myuser
      SPRING_DATASOURCE_PASSWORD: secret
      SPRING_JPA_HIBERNATE_DDL_AUTO: none
      SPRING_JPA_SHOW_SQL: "true"
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - internal_net
      - edge_net
    ports:
      - "8080:8080"

  frontend:
    image: nginx:alpine
    depends_on:
      - backend
    networks:
      - edge_net
    ports:
      - "3000:80"

networks:
  internal_net:
    internal: true
  edge_net:

volumes:
  pgdata: