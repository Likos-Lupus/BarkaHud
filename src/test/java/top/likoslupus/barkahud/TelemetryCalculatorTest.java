package top.likoslupus.barkahud;

import org.junit.jupiter.api.Test;
import top.likoslupus.barkahud.core.telemetry.BoatTelemetry;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelemetryCalculatorTest {

    private static final double G_DELTA = 1e-6;

    @Test
    void gForce_zeroChange_returnsZero() {
        assertEquals(0.0, BoatTelemetry.calculateGForce(20.0, 20.0), G_DELTA);
    }

    @Test
    void gForce_acceleration_positiveG() {
        double g = BoatTelemetry.calculateGForce(30.0, 20.0);
        assertEquals(10.0 * 20.0 / 9.80665, g, G_DELTA);
    }

    @Test
    void gForce_deceleration_negativeG() {
        double g = BoatTelemetry.calculateGForce(10.0, 20.0);
        assertEquals(-10.0 * 20.0 / 9.80665, g, G_DELTA);
    }

    @Test
    void gForce_oneG_acceleration() {
        double deltaSpeed = 9.80665 / 20.0;
        assertEquals(1.0, BoatTelemetry.calculateGForce(deltaSpeed, 0.0), G_DELTA);
    }

    @Test
    void gForce_twoG_acceleration() {
        double deltaSpeed = 2.0 * 9.80665 / 20.0;
        assertEquals(2.0, BoatTelemetry.calculateGForce(deltaSpeed, 0.0), G_DELTA);
    }

    @Test
    void gForce_fromZero_sameAsDelta() {
        double deltaSpeed = 5.0;
        assertEquals(deltaSpeed * 20.0 / 9.80665, BoatTelemetry.calculateGForce(deltaSpeed, 0.0), G_DELTA);
    }

    @Test
    void gForce_toZero_negativeFromDelta() {
        double initial = 5.0;
        assertEquals(-initial * 20.0 / 9.80665, BoatTelemetry.calculateGForce(0.0, initial), G_DELTA);
    }

}
