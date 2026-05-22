package top.likoslupus.barkahud;

import top.likoslupus.barkahud.config.BarkaHudConfig;

public class CameraSettings {

    public final boolean enabled;
    public final float aggressivenessMetersPerTick;
    public final float smoothing;

    private CameraSettings(
            boolean enabled,
            float aggressivenessMetersPerTick,
            float smoothing
    ) {
        this.enabled = enabled;
        this.aggressivenessMetersPerTick = aggressivenessMetersPerTick;
        this.smoothing = smoothing;
    }

    public static CameraSettings from(BarkaHudConfig config) {
        return new CameraSettings(
                config.cameraEnabled,
                config.cameraAggressivenessMetersPerSecond / 20f,
                config.cameraSmoothingPercent / 100f * 0.9f
        );
    }

}
