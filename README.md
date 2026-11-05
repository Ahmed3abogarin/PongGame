# 🛸 Space Pong Game

<div align="center">

A modern, arcade-style **Pong** game built entirely with **Jetpack Compose** for Android, featuring custom UFO visuals, smooth touch controls, real-time physics simulation, and dynamic animations.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202024.09.00-blue.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-1.3.x-orange.svg?style=flat&logo=materialdesign)](https://m3.material.io)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-29%20(Android%2010)-green.svg)](https://developer.android.com/about/versions/10)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-35%20(Android%2015)-brightgreen.svg)](https://developer.android.com/about/versions/15)

</div>

---

## 📖 Project Overview

**Space Pong** is a fresh, immersive spin on the classic arcade Pong game. Developed as a native Android application using modern declarative UI paradigms, it showcases real-time game loops, touch gesture handling, and fluid Compose animations entirely in Kotlin.

---

## ✨ Features

- **🚀 UFO & Arcade Physics**: Bouncing ball (UFO) physics with velocity vectors reflecting off screen boundaries and paddle surfaces.
- **⚡ Dynamic Speed Scaling**: The ball's horizontal velocity increases progressively with each successful paddle deflection, ramping up the challenge.
- **🎮 Smooth Drag Controls**: Responsive horizontal paddle movement powered by Jetpack Compose touch gestures (`Modifier.draggable`).
- **🏆 Real-Time Score Tracking**: Earn 10 points for every successful paddle hit, displayed prominently at the top of the screen.
- **🎨 Animated UI & Game Over Screen**: Immersive game-over overlay with a pulsing background-color animation on the restart button driven by `rememberInfiniteTransition`.
- **🌙 Material 3 & Dynamic Color**: Built on Material 3 design principles with support for dynamic color themes (Android 12+).

---

## 🛠️ Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/) (Coroutines for game loop timing)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io/)
- **Activity & Lifecycle**: AndroidX Activity Compose (`1.10.1`), Lifecycle Runtime KTX (`2.8.7`)
- **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`) & Version Catalogs (`libs.versions.toml`)

---

## 🏗️ Architecture & Implementation

The application follows a clean single-activity architecture (`MainActivity`) hosting a declarative Compose hierarchy:

```
com.ahmed.ponggame/
├── MainActivity.kt           # Entry point and core game state machine / loop
└── ui/theme/
    ├── Color.kt              # Color palettes & custom PaddleColor
    ├── Theme.kt              # Material 3 theme configuration (Dynamic color support)
    └── Type.kt               # Typography definitions
```

### Game Loop & State Management
- **Game Engine**: Powered by a coroutine-based `LaunchedEffect` running a ~16ms frame-rate update loop (`delay(16)`).
- **Position & Animation**: Uses Compose `Animatable` and state variables (`mutableFloatStateOf`, `mutableIntStateOf`) for performant 60 FPS rendering without recomposition bottlenecks.
- **Collision Detection**: Real-time bounding box intersection logic checking UFO position against screen boundaries and paddle coordinates.

---

## 📸 Screenshots

| Phone View | Tablet / Large Screen View |
| :---: | :---: |
| ![Pong game phone](https://github.com/user-attachments/assets/3c7e78a8-0e11-466c-bb09-6323e5e85050) | ![Pong game tablet](https://github.com/user-attachments/assets/ed819e78-b7b7-42fd-bdbe-e64e4eed4b8c) |

---

## 📋 Requirements

- **Android Studio**: Ladybug / Koala or newer (supporting Kotlin 2.0+)
- **JDK**: Version 11 or higher
- **Minimum SDK**: API 29 (Android 10.0+)
- **Target SDK**: API 35 (Android 15+)

---

## 🚀 Installation & Getting Started

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/PongGame.git
   ```
2. **Open in Android Studio**:
   - Launch Android Studio and select **Open**.
   - Navigate to the cloned `PongGame` directory.
3. **Sync Gradle**:
   - Allow Android Studio to automatically download dependencies and sync the Gradle project (`build.gradle.kts`).
4. **Run the App**:
   - Connect an Android device with USB Debugging enabled or start an Android Emulator (API 29+).
   - Click the **Run 'app'** ▶️ button in Android Studio.

---

## 🔮 Future Improvements

- **🔊 Audio FX**: Add sound effects for bouncing, scoring, and game over events.
- **💾 High Score Persistence**: Save personal best scores locally using Jetpack DataStore or SharedPreferences.
- **👥 Multiplayer / AI Opponent**: Introduce dual-paddle mode or a computer-controlled opponent AI.
- **⭐ Power-ups & Obstacles**: Dynamic power-ups (speed boost, paddle enlargement) and obstacles on the playfield.
- **🎇 Particle Effects**: Explosion or trail particles when the UFO bounces or scores.

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
