package top.likoslupus.barkahud.platform.impl;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import top.likoslupus.barkahud.CameraSettings;
import top.likoslupus.barkahud.config.ConfigManager;
import top.likoslupus.barkahud.core.telemetry.CameraMath;
import top.likoslupus.barkahud.platform.ICameraPlatform;

public class FabricCameraPlatform implements ICameraPlatform {

    private final Minecraft client;

    public FabricCameraPlatform() {
        this.client = Minecraft.getInstance();
    }

    @Override
    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(_ -> tick());
    }

    @Override
    public void tick() {
        var player = client.player;
        if (player == null) return;

        var settings = CameraSettings.from(ConfigManager.get());
        if (!settings.enabled) return;

        var vehicle = player.getVehicle();
        if (!(vehicle instanceof AbstractBoat boat)) return;

        if (boat.getControllingPassenger() != player) return;

        var hVel = boat.getDeltaMovement().multiply(1, 0, 1);

        if (client.screen != null) return;
        if (player.isSpectator()) return;

        float newYaw = CameraMath.calculateYaw(
                hVel.x,
                hVel.z,
                boat.getYRot(),
                player.getYRot(),
                settings.aggressivenessMetersPerTick,
                settings.smoothing
        );

        player.setYRot(newYaw);
    }

}
