#!/bin/bash

echo "Stopping Security Application..."

# Find and kill the Java process
pkill -f "SecurityApp-0.0.1-SNAPSHOT.jar"

# Wait for process to stop
sleep 5

# Force kill if still running
if pgrep -f "SecurityApp-0.0.1-SNAPSHOT.jar" > /dev/null; then
    echo "Force killing application..."
    pkill -9 -f "SecurityApp-0.0.1-SNAPSHOT.jar"
fi

echo "Application stopped successfully"
