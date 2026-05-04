# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build

# Debug APK
./gradlew assembleDebug

# Unit tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.example.loadout.ExampleUnitTest"

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Install on device
./gradlew installDebug

# Clean
./gradlew clean
```

## Architecture

This is a greenfield Android app using **Kotlin + Jetpack Compose + Material 3**. The implementation is in early scaffolding phase — currently only `MainActivity` and the Compose theme exist.

**Target architecture as features are built out:**
- Single `Activity` entry point (`MainActivity`)
- Compose Navigation for screen routing
- ViewModel + state hoisting for UI state
- Room for local persistence (data layer complete — entities, DAOs, `AppDatabase` in `model/lift/db/`)

**Domain — Tactical Barbell Fighter Protocol:**
- Users create a 6-week `TrainingBlock` by selecting an exercise cluster and entering current 1RMs
- The app generates sessions with percentage-based target weights and auto-regulated set counts (3–5 sets)
- Users log actual weights/reps per `LiftLog` entry against a `Session`
- Key entities: `TrainingBlock`, `OneRepMax`, `Session`, `LiftLog`, `Bodyweight` (see `docs/overview.md` for full data model)

**Exercise cluster (TB Fighter Protocol):** Overhead Press, Front Squat, Pullups, + optional Deadlift — 2 sessions per week, same cluster each session.

**Dependency versions** are managed centrally in `gradle/libs.versions.toml` (version catalog). Add new dependencies there, not inline in `build.gradle.kts`.

**Room must stay at 2.7.0+** — 2.6.x has a KSP2 bug that crashes on `suspend fun update(): Unit` in DAOs (`unexpected jvm signature V`).

**Min SDK 33**, target/compile SDK 36, Java 11, Kotlin 2.0.21.

## Rules

Anytime there is a significant learning, key decision made, architecture pattern or design pattern used, update JOURNAL.md [link](docs/JOURNAL.md) with a concise entry
Each session/day should have an overall summary of what we did for context. Follow existing conventions in the doc.
