# Week Number Widget

A simple Android desktop widget that displays the current week number.

## Features

*   Displays the current week number according to the ISO 8601 standard (as per Swedish standard).
*   Minimalistic design with a transparent gray background.
*   1x1 widget size.

## How to build

This project uses Docker to ensure a consistent build environment. To build the app, run the `build.sh` script from the root of the project:

```bash
./build.sh
```

This script will first build a Docker image using the `Dockerfile` in the project root and then run the Gradle build inside a container.

### Docker Permissions

If you encounter permission errors when running `build.sh`, you may need to run it with `sudo`:

```bash
sudo ./build.sh
```

Alternatively, you can add your user to the `docker` group to avoid using `sudo`. For more information, please refer to the [Docker documentation](https://docs.docker.com/engine/install/linux-postinstall/).

### Build Output

The final APK will be located at `app/build/outputs/apk/debug/app-debug.apk`.
