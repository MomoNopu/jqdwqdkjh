# Sims CLI Game (Java)

A text-based life simulation inspired by **The Sims**, implemented with object-oriented design and a command-line interface.

## Features

- Sim needs system: hunger, energy, hygiene, fun, social, bladder.
- Time progression with day/hour clock.
- Activities with distinct effects and durations.
- Career system with performance, promotions, and salary.
- Relationship/friendship tracking.
- Warnings for critical needs and low mood.
- Win/lose conditions for a 7-day simulation.
- Lightweight unit tests (no Maven/Gradle required).

## Project Structure

- `src/Main.java`: entry point.
- `src/sims/engine`: game loop and CLI flow.
- `src/sims/activity`: activity polymorphism (`Activity` interface + concrete activities).
- `src/sims/sim`: core entities (`Sim`, `Career`, `Relationship`).
- `src/sims/model`: data model for needs.
- `test/sims`: unit test classes.

## How to Compile

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

## How to Run

```bash
java -cp out Main
```

## How to Run Tests

```bash
mkdir -p out_test
javac -d out_test $(find src test -name "*.java")
java -cp out_test sims.SimCoreTest
java -cp out_test sims.ActivityTest
```

## OOP Design Notes

- **Encapsulation:** `Sim` hides need management internals through methods like `adjustNeed`, `advanceTime`, and `getMoodScore`.
- **Abstraction:** `Activity` interface defines behavior contract (`perform`, `durationHours`, etc.).
- **Polymorphism:** Each activity applies unique behavior through a common `Activity` type.
- **Composition:** `Sim` owns `Career` and `Relationship`; `SimsGame` owns a list of activities.

## Gameplay Goal

Survive and manage your Sim over 7 in-game days while balancing needs, work progression, and social life.
