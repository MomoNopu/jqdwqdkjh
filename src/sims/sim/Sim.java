package sims.sim;

import sims.model.NeedType;
import sims.model.SimNeed;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Sim {
    private final String name;
    private final EnumMap<NeedType, SimNeed> needs;
    private final Career career;
    private final Relationship relationship;
    private int money;
    private int day;
    private int hour;

    public Sim(String name) {
        this.name = name;
        this.needs = new EnumMap<>(NeedType.class);
        for (NeedType type : NeedType.values()) {
            needs.put(type, new SimNeed(type, 75));
        }
        this.career = new Career();
        this.relationship = new Relationship("Alex");
        this.money = 120;
        this.day = 1;
        this.hour = 8;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Career getCareer() {
        return career;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getNeed(NeedType type) {
        return needs.get(type).getValue();
    }

    public void adjustNeed(NeedType type, int delta) {
        needs.get(type).modify(delta);
    }

    public void addMoney(int amount) {
        money += amount;
        if (money < 0) {
            money = 0;
        }
    }

    public void advanceTime(int hours) {
        for (int i = 0; i < hours; i++) {
            hour++;
            if (hour >= 24) {
                hour = 0;
                day++;
            }
            tickNeeds();
        }
    }

    public List<String> tickWarnings() {
        List<String> warnings = new ArrayList<>();
        for (Map.Entry<NeedType, SimNeed> entry : needs.entrySet()) {
            if (entry.getValue().isCritical()) {
                warnings.add("Critical need: " + entry.getKey());
            }
        }
        if (getMoodScore() < 25) {
            warnings.add("Mood is very low. Take care of your Sim!");
        }
        return warnings;
    }

    public int getMoodScore() {
        int sum = 0;
        for (SimNeed need : needs.values()) {
            sum += need.getValue();
        }
        return sum / needs.size();
    }

    public String formattedClock() {
        return String.format("Day %d - %02d:00", day, hour);
    }

    private void tickNeeds() {
        adjustNeed(NeedType.HUNGER, -5);
        adjustNeed(NeedType.ENERGY, -4);
        adjustNeed(NeedType.HYGIENE, -3);
        adjustNeed(NeedType.FUN, -3);
        adjustNeed(NeedType.SOCIAL, -2);
        adjustNeed(NeedType.BLADDER, -4);

        if (getNeed(NeedType.HUNGER) <= 10) {
            adjustNeed(NeedType.ENERGY, -2);
        }
        if (getNeed(NeedType.ENERGY) <= 10) {
            adjustNeed(NeedType.FUN, -2);
        }
    }
}
