package sims.activity;

import sims.sim.Sim;

import java.util.List;

public interface Activity {
    String name();

    String description();

    int durationHours();

    List<String> perform(Sim sim);
}
