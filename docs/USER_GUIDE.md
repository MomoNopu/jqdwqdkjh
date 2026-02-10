# User Guide

## Start the game
1. Compile: `javac -d out $(find src -name "*.java")`
2. Run: `java -cp out Main`
3. Enter your Sim name.

## HUD overview
Each turn shows:
- Current game time (`Day X - HH:00`)
- Money, career title, and performance
- Friendship value
- All needs (0–100)
- Warning messages for critical conditions

## Actions
Choose one action each turn:
1. Eat Meal
2. Sleep
3. Shower
4. Use Toilet
5. Play Games
6. Socialize
7. Study Skill
8. Work Shift
9. Quit

Each action consumes in-game hours and updates needs, money, relationship, and/or career.

## Win/Loss conditions
- **Win:** Reach beyond Day 7.
- **Lose:** Mood score drops to 5 or below.

## Strategy tips
- Do not chain long work sessions without restoring hunger/energy/hygiene.
- Socialize occasionally to prevent social collapse and improve friendship.
- Study boosts career growth but harms short-term fun and energy.
