# Start from a well-known Ubuntu base image.
FROM ubuntu:22.04

# Set non-interactive frontend to avoid prompts during installation.
ENV DEBIAN_FRONTEND=noninteractive

# Install Java 17 and other necessary packages.
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjdk-17-jdk wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Set environment variables for the Android SDK.
ENV ANDROID_SDK_ROOT /opt/android-sdk
ENV PATH $PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools

# Download and install the Android SDK command-line tools.
RUN mkdir -p /tmp/android-sdk-temp && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O /tmp/android-sdk.zip && \
    unzip -q /tmp/android-sdk.zip -d /tmp/android-sdk-temp && \
    rm /tmp/android-sdk.zip

# Move the command line tools to the expected final location.
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    mv /tmp/android-sdk-temp/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest && \
    rm -r /tmp/android-sdk-temp

# Accept the Android SDK licenses before trying to install packages.
RUN yes | sdkmanager --licenses

# Install the necessary Android SDK packages for our project.
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Set the working directory for subsequent commands.
WORKDIR /app
