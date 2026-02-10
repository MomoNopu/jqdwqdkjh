package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.ArrayList;
import java.util.List;

public class EatMealActivity implements Activity {
    @Override
    public String name() {
        return "Eat Meal";
    }

    @Override
    public String description() {
        return "Spend $12 to cook/order a meal. Greatly restores hunger.";
    }

    @Override
    public int durationHours() {
        return 1;
    }

    @Override
    public List<String> perform(Sim sim) {
        List<String> messages = new ArrayList<>();
        if (sim.getMoney() < 12) {
            messages.add("Not enough money for ingredients.");
            sim.adjustNeed(NeedType.FUN, -2);
            return messages;
        }
        sim.addMoney(-12);
        sim.adjustNeed(NeedType.HUNGER, 45);
        sim.adjustNeed(NeedType.FUN, 5);
        messages.add("A tasty meal fills the stomach.");
        return messages;
    }
}
