package top.likoslupus.barkahud.core.telemetry;

import org.jspecify.annotations.NonNull;

public final class BoatTelemetry {

    private static final double TPS_DIV_G = 20.0 / 9.80665;

    private BoatTelemetry() {
    }

    public static double calculateDriftAngle(
            @NonNull Vec2d horizontalVelocity,
            @NonNull Vec2d horizontalLook
    ) {
        double vLen = horizontalVelocity.length();
        if (vLen == 0) return 0;

        double lLen = horizontalLook.length();
        if (lLen == 0) return 0;

        double dot = horizontalVelocity.dot(horizontalLook);
        double cos = Math.clamp(dot / (vLen * lLen), -1, 1);

        return Math.toDegrees(Math.acos(cos));
    }

    public static double calculateGForce(
            double currentSpeedMS,
            double previousSpeedMS
    ) {
        return (currentSpeedMS - previousSpeedMS) * TPS_DIV_G;
    }

}
