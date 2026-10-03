# Minecraft AFK Bot APK

This repository contains a starter Android APK project for a Minecraft AFK helper app.

Important note:
- This is a helper app prototype and not a bypass or exploit tool.
- Aternos does not provide an official server-side bot API for arbitrary Minecraft automation.
- For real automation on a privately owned Minecraft server, the clean approach is a custom plugin or server API.
- This project is intended as a local Android utility template for a custom server setup.

## What this app does
- Lets the user enter a server address / player name
- Starts and stops an AFK loop helper
- Simulates movement and idle actions for a local utility UI
- Provides a foundation you can expand into a custom plugin controller

## Project structure
- `app/` — Android app project
- `README.md` — project information and build instructions

## Build steps
1. Install Android Studio Hedgehog or newer
2. Open this folder as a project
3. Let Gradle sync
4. Build > Generate signed bundle/APK

## APK generation
You can create a debug APK from Android Studio:
- Build > Build Bundle(s) / APK(s) > Build APK(s)

## Recommended next step
If you want a true Minecraft automation system, use a plugin on your server and let the Android app connect to it securely over a custom API.

## Legal and safety reminder
Do not use this project against servers you do not own or do not have explicit permission to automate.
