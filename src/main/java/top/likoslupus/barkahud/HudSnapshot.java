package top.likoslupus.barkahud;

import org.jspecify.annotations.NonNull;

public record HudSnapshot(
        @NonNull String playerName,
        double speedMS,
        double driftAngle,
        double gForce,
        int ping,
        boolean isDriver,
        boolean keyLeft,
        boolean keyRight,
        boolean keyForward,
        boolean keyBackward
) {

    public static final HudSnapshot EMPTY = new HudSnapshot(
            "",
            0,
            0,
            0,
            -1,
            false,
            false,
            false,
            false,
            false
    );

    public boolean isRiding() {
        return this != EMPTY;
    }

}
