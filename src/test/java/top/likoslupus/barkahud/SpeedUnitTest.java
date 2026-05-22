package top.likoslupus.barkahud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import top.likoslupus.barkahud.config.SpeedUnit;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SpeedUnitTest {

    private static final double SPEED_MS = 20.0;
    private static final double DELTA = 1e-4;

    @Test
    void metersPerSecond_identity() {
        assertEquals(20.0, SPEED_MS * SpeedUnit.METERS_PER_SECOND.multiplier, DELTA);
    }

    @Test
    void kilometersPerHour_conversion() {
        assertEquals(72.0, SPEED_MS * SpeedUnit.KILOMETERS_PER_HOUR.multiplier, DELTA);
    }

    @Test
    void milesPerHour_conversion() {
        assertEquals(44.73872, SPEED_MS * SpeedUnit.MILES_PER_HOUR.multiplier, DELTA);
    }

    @Test
    void knots_conversion() {
        assertEquals(38.87688, SPEED_MS * SpeedUnit.KNOTS.multiplier, DELTA);
    }

    @Test
    void format_containsUnit() {
        assertEquals("%03.0f m/s", SpeedUnit.METERS_PER_SECOND.format);
        assertEquals("%03.0f km/h", SpeedUnit.KILOMETERS_PER_HOUR.format);
        assertEquals("%03.0f mph", SpeedUnit.MILES_PER_HOUR.format);
        assertEquals("%03.0f kt", SpeedUnit.KNOTS.format);
    }

    @Test
    void formatToString_rendersCorrectly() {
        assertEquals("020 m/s", String.format(SpeedUnit.METERS_PER_SECOND.format, 20.0));
        assertEquals("072 km/h", String.format(SpeedUnit.KILOMETERS_PER_HOUR.format, 72.0));
    }

    @Test
    void allUnits_translationKeyNotNull() {
        Arrays.stream(SpeedUnit.values())
                .map(unit -> unit.translationKey.isBlank())
                .forEach(Assertions::assertFalse);
    }

}
