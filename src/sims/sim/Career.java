package sims.sim;

import java.util.ArrayList;
import java.util.List;

public class Career {
    private static final List<String> TITLES = List.of(
            "Intern",
            "Junior Worker",
            "Associate",
            "Specialist",
            "Senior Specialist",
            "Manager",
            "Director"
    );

    private int level;
    private int performance;

    public Career() {
        this.level = 0;
        this.performance = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getPerformance() {
        return performance;
    }

    public String getTitle() {
        return TITLES.get(level);
    }

    public int shiftIncome() {
        return 40 + (level * 20);
    }

    public void loadState(int level, int performance) {
        if (level < 0) {
            this.level = 0;
        } else if (level >= TITLES.size()) {
            this.level = TITLES.size() - 1;
        } else {
            this.level = level;
        }
        this.performance = performance;
    }

    public List<String> applyWorkResult(int productivityScore) {
        List<String> messages = new ArrayList<>();
        performance += productivityScore;

        if (productivityScore < 0) {
            messages.add("Work was rough today. Performance dropped.");
        } else {
            messages.add("Work shift complete. You made solid progress.");
        }

        while (performance >= 100 && level < TITLES.size() - 1) {
            performance -= 100;
            level++;
            messages.add("Promotion! New title: " + getTitle());
        }

        if (performance < -50) {
            if (level > 0) {
                level--;
                performance = 10;
                messages.add("Demotion... take care of your Sim before work.");
            } else {
                performance = -20;
            }
        }

        return messages;
    }
}
