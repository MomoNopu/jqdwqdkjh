package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.List;

public class HaveFunActivity implements Activity {
    @Override
    public String name() {
        return "Play Games";
    }

    @Override
    public String description() {
        return "Gaming session to recover fun, costs a bit of energy.";
    }

    @Override
    public int durationHours() {
        return 2;
    }

    @Override
    public List<String> perform(Sim sim) {
        sim.adjustNeed(NeedType.FUN, 55);
        sim.adjustNeed(NeedType.ENERGY, -8);
        sim.adjustNeed(NeedType.HUNGER, -6);
        return List.of("That was actually entertaining.");
    }
}
