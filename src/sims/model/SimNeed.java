package sims.model;

public class SimNeed {
    private final NeedType type;
    private int value;

    public SimNeed(NeedType type, int initialValue) {
        this.type = type;
        this.value = clamp(initialValue);
    }

    public NeedType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void modify(int delta) {
        this.value = clamp(this.value + delta);
    }

    private int clamp(int v) {
        if (v < 0) {
            return 0;
        }
        return Math.min(v, 100);
    }

    public boolean isCritical() {
        return value <= 15;
    }
}
