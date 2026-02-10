package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.List;

public class ShowerActivity implements Activity {
    @Override
    public String name() {
        return "Shower";
    }

    @Override
    public String description() {
        return "Quick shower that restores hygiene and comfort.";
    }

    @Override
    public int durationHours() {
        return 1;
    }

    @Override
    public List<String> perform(Sim sim) {
        sim.adjustNeed(NeedType.HYGIENE, 60);
        sim.adjustNeed(NeedType.FUN, 3);
        return List.of("Clean and fresh again.");
    }
}
