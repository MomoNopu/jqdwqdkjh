package sims.engine;

import sims.activity.Activity;
import sims.activity.EatMealActivity;
import sims.activity.HaveFunActivity;
import sims.activity.ShowerActivity;
import sims.activity.SleepActivity;
import sims.activity.SocializeActivity;
import sims.activity.StudyActivity;
import sims.activity.UseToiletActivity;
import sims.activity.WorkShiftActivity;
import sims.io.ConsoleIO;
import sims.io.SaveManager;
import sims.model.NeedType;
import sims.sim.Sim;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimsGame {
    private static final int TARGET_DAYS = 7;
    private static final Path DEFAULT_SAVE_FILE = Path.of("savegame.txt");

    private final ConsoleIO io;
    private final SaveManager saveManager;
    private final List<Activity> activities;
    private Sim sim;

    public SimsGame(ConsoleIO io) {
        this.io = io;
        this.saveManager = new SaveManager();
        this.activities = new ArrayList<>();
        activities.add(new EatMealActivity());
        activities.add(new SleepActivity());
        activities.add(new ShowerActivity());
        activities.add(new UseToiletActivity());
        activities.add(new HaveFunActivity());
        activities.add(new SocializeActivity());
        activities.add(new StudyActivity());
        activities.add(new WorkShiftActivity());
    }

    public void run() {
        showWelcome();
        setupSim();

        while (sim.getDay() <= TARGET_DAYS) {
            renderStatus();
            PlayerChoice choice = promptChoice();

            if (choice.type == ChoiceType.ACTIVITY) {
                executeActivity(choice.activity);
            } else if (choice.type == ChoiceType.SAVE) {
                saveGame();
            } else if (choice.type == ChoiceType.LOAD) {
                loadGame();
            } else if (choice.type == ChoiceType.QUIT) {
                io.println("Thanks for playing!");
                printEndSummary(false);
                return;
            }

            if (sim.getMoodScore() <= 5) {
                io.println("\nYour Sim has burnt out completely. Game over.");
                printEndSummary(false);
                return;
            }
        }

        io.println("\nYou made it through " + TARGET_DAYS + " days!");
        printEndSummary(true);
    }

    private void showWelcome() {
        io.println("======================================");
        io.println("      THE SIMS CLI - JAVA EDITION     ");
        io.println("======================================");
        io.println("Goal: survive 7 in-game days while maintaining needs and career growth.");
    }

    private void setupSim() {
        io.println("1) New game");
        io.println("2) Load game from savegame.txt");
        io.print("> ");
        String mode = io.readLine().trim();

        if ("2".equals(mode)) {
            try {
                sim = saveManager.load(DEFAULT_SAVE_FILE);
                io.println("Loaded save for " + sim.getName() + " successfully.");
                return;
            } catch (Exception e) {
                io.println("Could not load save file. Starting new game instead.");
            }
        }

        io.print("Enter your Sim name: ");
        String name = io.readLine().trim();
        if (name.isEmpty()) {
            name = "Taylor";
        }
        sim = new Sim(name);
        io.println("Welcome, " + sim.getName() + "! Let's start your week.");
    }

    private void renderStatus() {
        io.println("\n--- " + sim.formattedClock() + " ---");
        io.println("Money: $" + sim.getMoney()
                + " | Career: " + sim.getCareer().getTitle()
                + " (Perf " + sim.getCareer().getPerformance() + ")");
        io.println("Friendship with " + sim.getRelationship().getFriendName() + ": "
                + sim.getRelationship().getFriendship());

        io.println("Needs:");
        for (NeedType needType : NeedType.values()) {
            io.println(String.format("- %-8s : %3d", needType, sim.getNeed(needType)));
        }

        List<String> warnings = sim.tickWarnings();
        if (!warnings.isEmpty()) {
            io.println("Warnings:");
            for (String warning : warnings) {
                io.println("! " + warning);
            }
        }
    }

    private PlayerChoice promptChoice() {
        io.println("\nChoose an option:");
        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            io.println((i + 1) + ") " + a.name() + " (" + a.durationHours() + "h) - " + a.description());
        }
        io.println("9) Save game");
        io.println("10) Load game");
        io.println("11) Quit game");

        while (true) {
            io.print("> ");
            String line = io.readLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= activities.size()) {
                    return PlayerChoice.activity(activities.get(choice - 1));
                }
                if (choice == 9) {
                    return PlayerChoice.simple(ChoiceType.SAVE);
                }
                if (choice == 10) {
                    return PlayerChoice.simple(ChoiceType.LOAD);
                }
                if (choice == 11) {
                    return PlayerChoice.simple(ChoiceType.QUIT);
                }
            } catch (NumberFormatException ignored) {
                // continue loop
            }
            io.println("Invalid option. Enter a number between 1 and 11.");
        }
    }

    private void executeActivity(Activity selected) {
        io.println("\n>> " + selected.name());
        List<String> messages = selected.perform(sim);
        sim.advanceTime(selected.durationHours());
        for (String message : messages) {
            io.println("- " + message);
        }
    }

    private void saveGame() {
        try {
            saveManager.save(sim, DEFAULT_SAVE_FILE);
            io.println("Game saved to " + DEFAULT_SAVE_FILE + ".");
        } catch (Exception e) {
            io.println("Failed to save game: " + e.getMessage());
        }
    }

    private void loadGame() {
        try {
            sim = saveManager.load(DEFAULT_SAVE_FILE);
            io.println("Game loaded from " + DEFAULT_SAVE_FILE + ".");
        } catch (Exception e) {
            io.println("Failed to load game: " + e.getMessage());
        }
    }

    private void printEndSummary(boolean completedWeek) {
        io.println("\n========= FINAL SUMMARY =========");
        io.println("Sim: " + sim.getName());
        io.println("Completed week: " + completedWeek);
        io.println("Time reached: " + sim.formattedClock());
        io.println("Money: $" + sim.getMoney());
        io.println("Career: " + sim.getCareer().getTitle() + " | Performance: " + sim.getCareer().getPerformance());
        io.println("Friendship: " + sim.getRelationship().getFriendship());
        io.println("Mood score: " + sim.getMoodScore());
        io.println("=================================");
    }

    private enum ChoiceType {
        ACTIVITY,
        SAVE,
        LOAD,
        QUIT
    }

    private static class PlayerChoice {
        private final ChoiceType type;
        private final Activity activity;

        private PlayerChoice(ChoiceType type, Activity activity) {
            this.type = type;
            this.activity = activity;
        }

        private static PlayerChoice activity(Activity activity) {
            return new PlayerChoice(ChoiceType.ACTIVITY, activity);
        }

        private static PlayerChoice simple(ChoiceType type) {
            return new PlayerChoice(type, null);
        }
    }
}
