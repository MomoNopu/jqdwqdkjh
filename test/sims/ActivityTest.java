package sims;

import sims.activity.EatMealActivity;
import sims.activity.SleepActivity;
import sims.activity.WorkShiftActivity;
import sims.model.NeedType;
import sims.sim.Sim;

public class ActivityTest {
    public static void main(String[] args) {
        testEatMealCostsMoneyAndRestoresHunger();
        testSleepRestoresEnergy();
        testWorkPaysSalary();
        System.out.println("ActivityTest passed");
    }

    private static void testEatMealCostsMoneyAndRestoresHunger() {
        Sim sim = new Sim("Test");
        sim.adjustNeed(NeedType.HUNGER, -50);
        int beforeMoney = sim.getMoney();
        new EatMealActivity().perform(sim);
        if (sim.getMoney() >= beforeMoney) {
            throw new AssertionError("Meal should cost money");
        }
        if (sim.getNeed(NeedType.HUNGER) <= 25) {
            throw new AssertionError("Meal should increase hunger need");
        }
    }

    private static void testSleepRestoresEnergy() {
        Sim sim = new Sim("Test");
        sim.adjustNeed(NeedType.ENERGY, -70);
        new SleepActivity().perform(sim);
        if (sim.getNeed(NeedType.ENERGY) < 60) {
            throw new AssertionError("Sleep should restore energy");
        }
    }

    private static void testWorkPaysSalary() {
        Sim sim = new Sim("Test");
        int beforeMoney = sim.getMoney();
        new WorkShiftActivity().perform(sim);
        if (sim.getMoney() <= beforeMoney) {
            throw new AssertionError("Work should increase money");
        }
    }
}
