# Week Number Widget

A simple Android desktop widget that displays the current week number.

## Features

*   Displays the current week number according to the ISO 8601 standard (as per Swedish standard).
*   Minimalistic design with a transparent gray background.
*   1x1 widget size.

## How to build

This project uses Docker to ensure a consistent build environment.

1.  Make sure you have Docker installed and running.
2.  Run the setup script: `./setup.sh`
    -   This script will build the Docker image and then run the Android build inside a container.
3.  The built APK can be found at `app/build/outputs/apk/debug/app-debug.apk`.
