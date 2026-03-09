#!/bin/bash

# Stop any existing application
pkill -f "SecurityApp-0.0.1-SNAPSHOT.jar" 2>/dev/null || true

# Wait for process to stop
sleep 5

# Navigate to deployment directory
cd /opt/security-app

# Start the application with environment variables
java -jar \
  -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
  -Dspring.datasource.url=$DB_URL \
  -Dspring.datasource.username=$DB_USERNAME \
  -Dspring.datasource.password=$DB_PASSWORD \
  -Djwt.secretKey=$JWT_SECRET \
  -Dspring.security.oauth2.client.registration.google.client-id=$GOOGLE_CLIENT_ID \
  -Dspring.security.oauth2.client.registration.google.client-secret=$GOOGLE_CLIENT_SECRET \
  SecurityApp-0.0.1-SNAPSHOT.jar &

echo "Application started with PID: $!"
