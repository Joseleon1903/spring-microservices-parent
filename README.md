# spring-microservices-parent
Spring boot microservices laboratory

## Security Configuration with Authentication server KeyCloak

### Step by Step 

- run docker command:
 -- docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0.0 start-dev
 -- login -> http://localhost:8181/admin {password and user = admin}

