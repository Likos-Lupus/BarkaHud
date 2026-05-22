package top.likoslupus.barkahud;

import org.junit.jupiter.api.Test;
import top.likoslupus.barkahud.core.telemetry.BoatTelemetry;
import top.likoslupus.barkahud.core.telemetry.Vec2d;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriftAngleTest {

    @Test
    void sameDirection_zeroAngle() {
        Vec2d velocity = new Vec2d(1, 0);
        Vec2d look = new Vec2d(1, 0);
        assertEquals(0.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void oppositeDirection_180deg() {
        Vec2d velocity = new Vec2d(1, 0);
        Vec2d look = new Vec2d(-1, 0);
        assertEquals(180.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void perpendicular_90deg() {
        Vec2d velocity = new Vec2d(1, 0);
        Vec2d look = new Vec2d(0, 1);
        assertEquals(90.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void zeroVelocity_returnsZero() {
        Vec2d velocity = new Vec2d(0, 0);
        Vec2d look = new Vec2d(1, 0);
        assertEquals(0.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void zeroLook_returnsZero() {
        Vec2d velocity = new Vec2d(1, 0);
        Vec2d look = new Vec2d(0, 0);
        assertEquals(0.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void verticalComponents_projected_and_ignored() {
        Vec2d velocity = new Vec2d(0, 1);
        Vec2d look = new Vec2d(0, 1);
        assertEquals(0.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void negativeSpeed_stillCorrect() {
        Vec2d velocity = new Vec2d(-1, 0);
        Vec2d look = new Vec2d(1, 0);
        assertEquals(180.0, BoatTelemetry.calculateDriftAngle(velocity, look), 1e-9);
    }

}
