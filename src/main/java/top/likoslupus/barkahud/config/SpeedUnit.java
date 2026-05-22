package top.likoslupus.barkahud.config;

public enum SpeedUnit {

    METERS_PER_SECOND(1.0, "%03.0f m/s", "barkahud.option.speed_format.MS"),
    KILOMETERS_PER_HOUR(3.6, "%03.0f km/h", "barkahud.option.speed_format.KMPH"),
    MILES_PER_HOUR(2.236936, "%03.0f mph", "barkahud.option.speed_format.MPH"),
    KNOTS(1.943844, "%03.0f kt", "barkahud.option.speed_format.KT");

    public final double multiplier;
    public final String format;
    public final String translationKey;

    SpeedUnit(
            double multiplier,
            String format,
            String translationKey
    ) {
        this.multiplier = multiplier;
        this.format = format;
        this.translationKey = translationKey;
    }

}
