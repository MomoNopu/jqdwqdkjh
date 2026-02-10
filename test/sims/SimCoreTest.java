package sims;

import sims.model.NeedType;
import sims.sim.Sim;

public class SimCoreTest {
    public static void main(String[] args) {
        testNeedsClamp();
        testTimeAdvance();
        testMoodScoreRange();
        System.out.println("SimCoreTest passed");
    }

    private static void testNeedsClamp() {
        Sim sim = new Sim("Test");
        sim.adjustNeed(NeedType.HUNGER, 999);
        if (sim.getNeed(NeedType.HUNGER) != 100) {
            throw new AssertionError("Need should clamp to 100");
        }
        sim.adjustNeed(NeedType.HUNGER, -999);
        if (sim.getNeed(NeedType.HUNGER) != 0) {
            throw new AssertionError("Need should clamp to 0");
        }
    }

    private static void testTimeAdvance() {
        Sim sim = new Sim("Test");
        sim.advanceTime(20);
        if (sim.getDay() != 2 || sim.getHour() != 4) {
            throw new AssertionError("Time should roll over to next day");
        }
    }

    private static void testMoodScoreRange() {
        Sim sim = new Sim("Test");
        sim.advanceTime(6);
        int mood = sim.getMoodScore();
        if (mood < 0 || mood > 100) {
            throw new AssertionError("Mood should stay in [0,100]");
        }
    }
}
