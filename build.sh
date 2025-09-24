#!/bin/bash
set -e

IMAGE_NAME="week-widget-builder"

echo "Building the Docker image..."
# Build the Docker image using the Dockerfile in the current directory.
# If you encounter permission errors, you may need to run this script with sudo
# or add your user to the 'docker' group.
docker build -t $IMAGE_NAME .

echo "Running the build inside the Docker container..."
# Mount the current directory to /app in the container,
# which is the WORKDIR set in the Dockerfile.
# The --rm flag ensures the container is removed after the build.
docker run --rm -v "$(pwd)":/app $IMAGE_NAME ./gradlew build

echo "Build successful! The APK can be found in app/build/outputs/apk/debug/app-debug.apk"