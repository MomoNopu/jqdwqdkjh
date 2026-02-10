package sims.sim;

public class Relationship {
    private final String friendName;
    private int friendship;

    public Relationship(String friendName) {
        this.friendName = friendName;
        this.friendship = 40;
    }

    public String getFriendName() {
        return friendName;
    }

    public int getFriendship() {
        return friendship;
    }

    public void loadState(int friendship) {
        this.friendship = friendship;
        if (this.friendship < 0) {
            this.friendship = 0;
        }
        if (this.friendship > 100) {
            this.friendship = 100;
        }
    }

    public void changeFriendship(int delta) {
        friendship += delta;
        if (friendship < 0) {
            friendship = 0;
        }
        if (friendship > 100) {
            friendship = 100;
        }
    }
}
