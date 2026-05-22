package top.likoslupus.barkahud.core.telemetry;

public final class CameraMath {

    private static final double MIN_SPEED_MT = 0.01;

    private CameraMath() {
    }

    public static float calculateYaw(
            double velocityX,
            double velocityZ,
            float boatYaw,
            float playerYaw,
            float aggressivenessMetersPerTick,
            float smoothing
    ) {
        double speedMT = Math.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        if (speedMT < MIN_SPEED_MT) return playerYaw;

        float velocityAngle = (float) Math.toDegrees(Math.atan2(velocityZ, velocityX)) - 90f;

        float lerpProg = Math.clamp((float) speedMT / aggressivenessMetersPerTick, 0f, 1f);
        float targetYaw = rotLerp(lerpProg, boatYaw, velocityAngle);

        return rotLerp(smoothing, targetYaw, playerYaw);
    }

    private static float rotLerp(
            float delta,
            float start,
            float end
    ) {
        return start + delta * wrapDegrees(end - start);
    }

    private static float wrapDegrees(float degrees) {
        float wrapped = degrees % 360.0F;
        if (wrapped >= 180.0F) wrapped -= 360.0F;
        if (wrapped < -180.0F) wrapped += 360.0F;
        return wrapped;
    }

}
