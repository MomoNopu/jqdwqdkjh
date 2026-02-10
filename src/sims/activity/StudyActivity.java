package sims.activity;

import sims.model.NeedType;
import sims.sim.Sim;

import java.util.List;

public class StudyActivity implements Activity {
    @Override
    public String name() {
        return "Study Skill";
    }

    @Override
    public String description() {
        return "Builds discipline; small career performance boost.";
    }

    @Override
    public int durationHours() {
        return 2;
    }

    @Override
    public List<String> perform(Sim sim) {
        sim.adjustNeed(NeedType.FUN, -10);
        sim.adjustNeed(NeedType.ENERGY, -8);
        sim.getCareer().applyWorkResult(8);
        return List.of("You studied and feel better prepared for work.");
    }
}
