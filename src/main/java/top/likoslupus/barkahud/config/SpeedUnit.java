package top.likoslupus.barkahud.config;

import org.jspecify.annotations.NonNull;

public enum SpeedUnit {

    METERS_PER_SECOND(1.0, "%03.0f m/s", "barkahud.enum.speed_unit.MS"),
    KILOMETERS_PER_HOUR(3.6, "%03.0f km/h", "barkahud.enum.speed_unit.KMPH"),
    MILES_PER_HOUR(2.236936, "%03.0f mph", "barkahud.enum.speed_unit.MPH"),
    KNOTS(1.943844, "%03.0f kt", "barkahud.enum.speed_unit.KT");

    public final double multiplier;
    public final String format;
    public final String translationKey;

    SpeedUnit(
            double multiplier,
            @NonNull String format,
            @NonNull String translationKey
    ) {
        this.multiplier = multiplier;
        this.format = format;
        this.translationKey = translationKey;
    }

}
