package sims.activity;

import sims.model.NeedType;
import sims.sim.Career;
import sims.sim.Sim;

import java.util.ArrayList;
import java.util.List;

public class WorkShiftActivity implements Activity {
    @Override
    public String name() {
        return "Work Shift";
    }

    @Override
    public String description() {
        return "4-hour shift that earns money and affects career performance.";
    }

    @Override
    public int durationHours() {
        return 4;
    }

    @Override
    public List<String> perform(Sim sim) {
        List<String> messages = new ArrayList<>();

        int baseline = sim.getMoodScore() - 50;
        int productivity = baseline / 4;

        sim.adjustNeed(NeedType.ENERGY, -25);
        sim.adjustNeed(NeedType.HUNGER, -15);
        sim.adjustNeed(NeedType.FUN, -10);
        sim.adjustNeed(NeedType.HYGIENE, -8);
        sim.adjustNeed(NeedType.BLADDER, -10);
        sim.adjustNeed(NeedType.SOCIAL, -6);

        Career career = sim.getCareer();
        sim.addMoney(career.shiftIncome());
        messages.add("Earned $" + career.shiftIncome() + " from today's shift.");
        messages.addAll(career.applyWorkResult(productivity));

        return messages;
    }
}
