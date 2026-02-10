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
import sims.model.NeedType;
import sims.sim.Sim;

import java.util.ArrayList;
import java.util.List;

public class SimsGame {
    private static final int TARGET_DAYS = 7;

    private final ConsoleIO io;
    private final List<Activity> activities;
    private Sim sim;

    public SimsGame(ConsoleIO io) {
        this.io = io;
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
            Activity selected = promptActivity();
            executeActivity(selected);

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

    private Activity promptActivity() {
        io.println("\nChoose an activity:");
        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            io.println((i + 1) + ") " + a.name() + " (" + a.durationHours() + "h) - " + a.description());
        }
        io.println("9) Quit game");

        while (true) {
            io.print("> ");
            String line = io.readLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice == 9) {
                    io.println("Thanks for playing!");
                    printEndSummary(false);
                    System.exit(0);
                }
                if (choice >= 1 && choice <= activities.size()) {
                    return activities.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {
                // continue loop
            }
            io.println("Invalid option. Enter a number between 1 and 9.");
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
}
