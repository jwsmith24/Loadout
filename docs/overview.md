# Loadout Overview

TB Fighter Protocol:
- Two lifts per week
- Same cluster of lifts each day (Overhead Press, Front Squat, Pullups, + Optional Deadlift)
- Number of sets auto-regulated between 3-5
- Reps based on %1RM for the week


## User Flows

### Building a training block
```mermaid
flowchart TD
    A[Select exercise cluster] --> B[Input Current 1RMS for cluster]
    B --> C[App generates 6 week block]
    C --> D[Block generated]
```

### Logging a lift
```mermaid
flowchart TD
    A[Select next lift from block] --> B[Select start lift]
    B --> C[Enter actual weights / sets completed for each exercise]
    C --> D{Finish workout?}
    D -- YES --> E[Save and return to menu]
    D -- NO --> C
```

## Data Plan

### Lifting

`TrainingBlock`
- id : Int (just a simple counter)
- startDate: Instant

`OneRepMax`
- id: Int
- exercise: Enum
- weight: Double

`Session`
- id: Int
- blockId: Int (FK → TrainingBlock.id)
- date: Instant
- weekNumber: Int (used for weight / reps)

`LiftLog`
- id: Int
- exercise: Enum
- targetWeight: Double (calculated: 1RM x week%)
- targetReps: Int (derived from week number)
- actualWeight: Double? (default null)
- actualReps: Int? (default null)
- setsCompleted: Int (3, 4, or 5)
- sessionId: Int (FK → Session.id)


### User
`Bodyweight`
- id: Int
- date: Instant
- weight: Double

### Running
TBD