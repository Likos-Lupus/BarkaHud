package top.likoslupus.barkahud.platform.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.core.snapshot.HudSnapshot;
import top.likoslupus.barkahud.core.telemetry.BoatTelemetry;
import top.likoslupus.barkahud.core.telemetry.Vec2d;
import top.likoslupus.barkahud.platform.IClientSnapshotProvider;

public class MinecraftClientSnapshotProvider implements IClientSnapshotProvider {

    private final Minecraft client;

    public MinecraftClientSnapshotProvider(@NonNull Minecraft client) {
        this.client = client;
    }

    @Override
    public HudSnapshot capture() {
        var player = client.player;
        if (player == null) return HudSnapshot.EMPTY;

        var vehicle = player.getVehicle();
        if (!(vehicle instanceof Boat boat)) return HudSnapshot.EMPTY;

        if (boat.getFirstPassenger() != player) return HudSnapshot.EMPTY;

        Vec3 velocityH = boat.getDeltaMovement().multiply(1, 0, 1);
        Vec3 lookH = boat.getLookAngle().multiply(1, 0, 1);

        double speed = velocityH.length() * 20;

        double driftAngle = BoatTelemetry.calculateDriftAngle(
                new Vec2d(velocityH.x, velocityH.z),
                new Vec2d(lookH.x, lookH.z)
        );

        var connection = client.getConnection();
        PlayerInfo playerInfo = connection != null ? connection.getPlayerInfo(player.getUUID()) : null;
        int ping = playerInfo != null ? playerInfo.getLatency() : -1;

        boolean isDriver = boat.getControllingPassenger() == player;

        var options = client.options;
        return new HudSnapshot(
                player.getName().getString(),
                speed,
                driftAngle,
                0,
                ping,
                isDriver,
                options.keyLeft.isDown(),
                options.keyRight.isDown(),
                options.keyUp.isDown(),
                options.keyDown.isDown()
        );
    }

}
