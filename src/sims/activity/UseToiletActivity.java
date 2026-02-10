package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.List;

public class UseToiletActivity implements Activity {
    @Override
    public String name() {
        return "Use Toilet";
    }

    @Override
    public String description() {
        return "Fix bladder quickly.";
    }

    @Override
    public int durationHours() {
        return 1;
    }

    @Override
    public List<String> perform(Sim sim) {
        sim.adjustNeed(NeedType.BLADDER, 80);
        return List.of("Much better now.");
    }
}
