package top.likoslupus.barkahud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.Vec3;

public class HudController {

    private static HudController instance;

    private final Minecraft client;
    private final HudRenderer hudRenderer;
    private HudSnapshot currentSnapshot = HudSnapshot.EMPTY;
    private double oldSpeed;

    public HudController(Minecraft client) {
        this.client = client;
        this.hudRenderer = new HudRenderer(client);
        instance = this;
    }

    public static HudController getInstance() {
        return instance;
    }

    public HudSnapshot getSnapshot() {
        return currentSnapshot;
    }

    public HudRenderer getRenderer() {
        return hudRenderer;
    }

    public void tick() {
        var player = client.player;
        if (player == null) {
            currentSnapshot = HudSnapshot.EMPTY;
            oldSpeed = 0;
            return;
        }

        var vehicle = player.getVehicle();
        if (!(vehicle instanceof AbstractBoat boat)) {
            currentSnapshot = HudSnapshot.EMPTY;
            oldSpeed = 0;
            return;
        }

        if (boat.getFirstPassenger() != player) {
            currentSnapshot = HudSnapshot.EMPTY;
            oldSpeed = 0;
            return;
        }

        Vec3 velocity = boat.getDeltaMovement().multiply(1, 0, 1);
        double speed = velocity.length() * 20;

        double driftAngle = Math.toDegrees(Math.acos(velocity.dot(boat.getLookAngle()
                .normalize()) / velocity.length()));
        if (Double.isNaN(driftAngle)) driftAngle = 0;

        double gForce = (speed - oldSpeed) * 2.040816327;
        oldSpeed = speed;

        var connection = client.getConnection();
        PlayerInfo playerInfo = connection != null ? connection.getPlayerInfo(player.getUUID()) : null;
        int ping = playerInfo != null ? playerInfo.getLatency() : -1;

        boolean isDriver = boat.getControllingPassenger() == player;

        currentSnapshot = new HudSnapshot(
                player.getName().getString(),
                speed,
                gForce,
                driftAngle,
                ping,
                isDriver
        );
    }

}
