#!/bin/bash
set -e

IMAGE_NAME="week-widget-builder"

echo "Building the Docker image with sudo..."
sudo docker build -t $IMAGE_NAME .

echo "Running the build inside the Docker container with sudo..."
# We mount the current directory to /app in the container,
# which is the WORKDIR we set in the Dockerfile.
sudo docker run --rm -v "$(pwd)":/app $IMAGE_NAME ./gradlew build

echo "Build successful! The APK can be found in app/build/outputs/apk/debug/app-debug.apk"
