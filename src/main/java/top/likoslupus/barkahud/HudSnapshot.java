package top.likoslupus.barkahud;

public record HudSnapshot(
        String playerName,
        double speedMS,
        double gForce,
        double driftAngle,
        int ping,
        boolean isDriver
) {

    public static final HudSnapshot EMPTY = new HudSnapshot(
            "",
            0,
            0,
            0,
            -1,
            false
    );

    public boolean isRiding() {
        return this != EMPTY;
    }

}
