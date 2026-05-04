# JOURNAL.md

## 2026-05-03 — Data layer

### Starting point — data plan & user flows

**TB Fighter Protocol:**
- 2 sessions/week, same exercise cluster each session
- Sets auto-regulated (3–5), reps based on %1RM for the week
- 6-week training block

**User flows:**
- *Build a block:* select cluster → input current 1RMs → app generates 6-week block
- *Log a lift:* select next lift from block → enter actual weights/sets → repeat or finish

**Initial data plan (pre-implementation):**
- `TrainingBlock` — id, startDate
- `OneRepMax` — id, exercise (Enum), weight, recordedAt
- `Session` — id, block (FK), date, weekNumber
- `LiftLog` — id, exercise (Enum), targetWeight, targetReps, actualWeight?, actualReps?, setsCompleted, session (FK)
- `Bodyweight` — deferred
- Running — TBD

**What changed during implementation:** `Session.block` and `LiftLog.session` became flat Int FKs (Room can't store nested objects). `OneRepMax` gained `recordedAt` to support history tracking. `Exercise` stayed an enum (not a DB table) with a TypeConverter.

### Summary
Built the full Room data layer for the lifting domain from scratch. Added KSP and Room to the build via `libs.versions.toml` (version catalog), then created four entities (`TrainingBlock`, `OneRepMax`, `Session`, `LiftLog`) with foreign keys and cascade deletes. Wrote a DAO per entity with `Flow`-based observable queries and `suspend` writes, a `Converters` class for `Instant` and `Exercise` type conversion, and `AppDatabase` as the single database entrypoint. Topped it off with a `LiftingRepository` that exposes a clean API to the rest of the app and hides all DAO details.

**Key design patterns used:**
- **Repository pattern** — `LiftingRepository` sits between DAOs and ViewModels; the rest of the app never touches Room directly
- **Singleton pattern** — `AppDatabase` uses `@Volatile` + `synchronized` double-checked locking to ensure only one database connection is ever opened; `@Volatile` guarantees the write is visible across threads, `synchronized` prevents two threads from racing past the null check
- **Observer pattern** — DAOs return `Flow<T>` so the UI can reactively observe database state rather than polling
- **TypeConverter pattern** — `Converters` bridges Kotlin types (`Instant`, `Exercise`) that Room can't natively store to SQLite-compatible scalar values (`Long`, `String`)

**Room requires KSP 2.7.0+**
Room 2.6.x crashes on `suspend fun update(): Unit` in DAOs with KSP2 (`unexpected jvm signature V`). Must use Room 2.7.0+.

**KSP replaces kapt**
Room's annotation processor now uses KSP (`ksp(libs.androidx.room.compiler)`) instead of the older `kapt`. Faster compile times, required for Kotlin 2.x.

**`kotlin.time.Instant` is still experimental**
Even on minSdk 36, `kotlin.time.Instant` requires `@OptIn(ExperimentalTime::class)`. Use `java.time.Instant` for Android-only projects — stable since API 26, no opt-in needed.

**Room can't store nested objects**
Entity fields must be flat scalar types. A field like `val session: Session` in another entity won't compile — store the FK as a plain `Int` instead.

**`@Insert` always returns `Long`**
Room's insert return type is always `Long` (SQLite row ID), regardless of whether the entity's `@PrimaryKey` is `Int`. Safe to ignore at the call site with `autoGenerate = true`.

**Flow vs suspend in DAOs**
Observable queries (UI needs to react to changes) return `Flow<T>` — no `suspend` needed, Room handles threading. One-shot writes (`@Insert`, `@Update`, `@Delete`) should be `suspend fun`.

**Domain model = Room entity here**
Skipped the entity/domain model split. For a single-platform app with a simple schema, maintaining two parallel class hierarchies + mappers is overhead with no payoff. Room annotations on data classes are inert at runtime.

**1RM design: history over current value**
`OneRepMax` keeps a full history (one row per update with `recordedAt: Instant`) rather than overwriting a single row. DAOs query latest by `ORDER BY recordedAt DESC LIMIT 1` for block generation, full history for progress tracking.
