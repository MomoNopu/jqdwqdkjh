# System Overview & Architecture

## High-level module view

- **Engine (`sims.engine`)**
  - `SimsGame`: orchestrates CLI loop, rendering, user input, activity execution, and end conditions.
- **Entities (`sims.sim`)**
  - `Sim`: aggregate root for needs, time, money, career, relationship.
  - `Career`: job level, title, performance, salary logic.
  - `Relationship`: friendship value management.
- **Model (`sims.model`)**
  - `NeedType`: enum of needs.
  - `SimNeed`: encapsulated need value with clamping and critical thresholds.
- **Activities (`sims.activity`)**
  - `Activity` interface defines activity contract.
  - Concrete actions (eat, sleep, shower, etc.) implement behavior polymorphically.
- **I/O (`sims.io`)**
  - `ConsoleIO`: scanner/output abstraction.
  - `SaveManager`: plain-text persistence service for save/load.

## Time and simulation flow

1. Game renders status.
2. Player chooses activity.
3. Activity executes direct effects.
4. Sim time advances by activity duration.
5. Passive need decay applies each in-game hour.
6. Warnings/conditions evaluated.

## OOP justification

- **Single responsibility:** each class targets one domain concern.
- **Open/closed principle:** new activities can be added by implementing `Activity` and registering in `SimsGame`.
- **Loose coupling:** game engine depends on `Activity` abstraction, not concrete implementations.
- **Testability:** core logic in domain classes is deterministic and validated via standalone tests.
