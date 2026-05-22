package top.likoslupus.barkahud;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import top.likoslupus.barkahud.config.ConfigManager;

public class CameraHandler {

    public static void tick(AbstractBoat boat, LocalPlayer player) {
        var boatYaw = boat.getYRot();
        var velocity = boat.getDeltaMovement();
        var velocityAngle = (float) Math.toDegrees(Math.atan2(velocity.z, velocity.x)) - 90f;
        if (Float.isNaN(velocityAngle)) velocityAngle = boatYaw;

        var speedMS = velocity.multiply(1, 0, 1).length() * 20;
        var aggressivenessMS = ConfigManager.get().cameraAggressivenessMetersPerSecond;
        var lerpProg = (float) Math.min(speedMS / aggressivenessMS, 1);
        var newYRot = Mth.rotLerp(lerpProg, boatYaw, velocityAngle);

        var smoothing = ConfigManager.get().cameraSmoothingPercent / 100f * 0.9f;
        newYRot = Mth.rotLerp(smoothing, newYRot, player.getYRot());

        player.setYRot(newYRot);
    }

}
