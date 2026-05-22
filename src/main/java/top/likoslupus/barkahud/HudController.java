package top.likoslupus.barkahud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.Vec3;

public class HudController {

    private static final double TPS_DIV_G = 20.0 / 9.80665;

    private static HudController instance;

    private final Minecraft client;
    private final HudRenderer hudRenderer;
    private HudSnapshot currentSnapshot = HudSnapshot.EMPTY;
    private double oldSpeed;
    private boolean firstFrame = true;

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
            setEmpty();
            return;
        }

        var vehicle = player.getVehicle();
        if (!(vehicle instanceof AbstractBoat boat)) {
            setEmpty();
            return;
        }

        if (boat.getFirstPassenger() != player) {
            setEmpty();
            return;
        }

        Vec3 velocityH = boat.getDeltaMovement().multiply(1, 0, 1);
        Vec3 lookH = boat.getLookAngle().multiply(1, 0, 1);

        double speed = velocityH.length() * 20;

        double driftAngle = calculateDriftAngle(velocityH, lookH);

        double gForce = firstFrame ? 0 : (speed - oldSpeed) * TPS_DIV_G;
        firstFrame = false;
        oldSpeed = speed;

        var connection = client.getConnection();
        PlayerInfo playerInfo = connection != null ? connection.getPlayerInfo(player.getUUID()) : null;
        int ping = playerInfo != null ? playerInfo.getLatency() : -1;

        boolean isDriver = boat.getControllingPassenger() == player;

        var options = client.options;
        currentSnapshot = new HudSnapshot(
                player.getName().getString(),
                speed,
                driftAngle,
                gForce,
                ping,
                isDriver,
                options.keyLeft.isDown(),
                options.keyRight.isDown(),
                options.keyUp.isDown(),
                options.keyDown.isDown()
        );
    }

    private void setEmpty() {
        currentSnapshot = HudSnapshot.EMPTY;
        oldSpeed = 0;
        firstFrame = true;
    }

    static double calculateDriftAngle(Vec3 horizontalVelocity, Vec3 horizontalLook) {
        double vLen = horizontalVelocity.length();
        if (vLen == 0) return 0;

        double lLen = horizontalLook.length();
        if (lLen == 0) return 0;

        double dot = horizontalVelocity.dot(horizontalLook);
        double cos = Mth.clamp(dot / (vLen * lLen), -1, 1);

        return Math.toDegrees(Math.acos(cos));
    }

}
