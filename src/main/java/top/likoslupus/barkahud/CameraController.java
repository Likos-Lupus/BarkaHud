package top.likoslupus.barkahud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class CameraController {

    private static final double MIN_SPEED_MT = 0.01;

    public static void tick(
            @NonNull AbstractBoat boat,
            @NonNull LocalPlayer player,
            @NonNull CameraSettings settings
    ) {
        if (!settings.enabled) return;
        if (boat.getControllingPassenger() != player) return;

        Vec3 hVel = boat.getDeltaMovement().multiply(1, 0, 1);
        float speedMT = (float) hVel.length();
        if (speedMT < MIN_SPEED_MT) return;

        var client = Minecraft.getInstance();
        if (client.screen != null) return;
        if (player.isSpectator()) return;

        float boatYaw = boat.getYRot();
        float velocityAngle = (float) Math.toDegrees(Math.atan2(hVel.z, hVel.x)) - 90f;

        float lerpProg = Mth.clamp(speedMT / settings.aggressivenessMetersPerTick, 0f, 1f);
        float targetYaw = Mth.rotLerp(lerpProg, boatYaw, velocityAngle);

        float newYaw = Mth.rotLerp(settings.smoothing, targetYaw, player.getYRot());

        player.setYRot(newYaw);
    }

}
