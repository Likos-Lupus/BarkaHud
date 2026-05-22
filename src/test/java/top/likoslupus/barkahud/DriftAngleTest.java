package top.likoslupus.barkahud;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriftAngleTest {

    @Test
    void sameDirection_zeroAngle() {
        Vec3 velocity = new Vec3(1, 0, 0);
        Vec3 look = new Vec3(1, 0, 0);
        assertEquals(0.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void oppositeDirection_180deg() {
        Vec3 velocity = new Vec3(1, 0, 0);
        Vec3 look = new Vec3(-1, 0, 0);
        assertEquals(180.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void perpendicular_90deg() {
        Vec3 velocity = new Vec3(1, 0, 0);
        Vec3 look = new Vec3(0, 0, 1);
        assertEquals(90.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void zeroVelocity_returnsZero() {
        Vec3 velocity = new Vec3(0, 0, 0);
        Vec3 look = new Vec3(1, 0, 0);
        assertEquals(0.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void zeroLook_returnsZero() {
        Vec3 velocity = new Vec3(1, 0, 0);
        Vec3 look = new Vec3(0, 0, 0);
        assertEquals(0.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void verticalComponents_projected_and_ignored() {
        Vec3 velocity = new Vec3(0, 10, 1).multiply(1, 0, 1);
        Vec3 look = new Vec3(0, 5, 1).multiply(1, 0, 1);
        assertEquals(0.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

    @Test
    void negativeSpeed_stillCorrect() {
        Vec3 velocity = new Vec3(-1, 0, 0);
        Vec3 look = new Vec3(1, 0, 0);
        assertEquals(180.0, HudController.calculateDriftAngle(velocity, look), 1e-9);
    }

}
