package sims.activity;

import sims.model.NeedType;
import sims.sim.Relationship;
import sims.sim.Sim;

import java.util.List;

public class SocializeActivity implements Activity {
    @Override
    public String name() {
        return "Socialize";
    }

    @Override
    public String description() {
        return "Hang out with your friend to improve social need and friendship.";
    }

    @Override
    public int durationHours() {
        return 2;
    }

    @Override
    public List<String> perform(Sim sim) {
        Relationship relationship = sim.getRelationship();
        sim.adjustNeed(NeedType.SOCIAL, 45);
        sim.adjustNeed(NeedType.FUN, 15);
        relationship.changeFriendship(12);
        return List.of("You spent quality time with " + relationship.getFriendName() + ".");
    }
}
