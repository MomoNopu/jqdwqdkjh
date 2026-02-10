package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.List;

public class SleepActivity implements Activity {
    @Override
    public String name() {
        return "Sleep";
    }

    @Override
    public String description() {
        return "Sleep for 6 hours to recover energy and some mood.";
    }

    @Override
    public int durationHours() {
        return 6;
    }

    @Override
    public List<String> perform(Sim sim) {
        sim.adjustNeed(NeedType.ENERGY, 70);
        sim.adjustNeed(NeedType.FUN, 8);
        sim.adjustNeed(NeedType.HUNGER, -8);
        return List.of("You wake up feeling refreshed.");
    }
}
