package top.likoslupus.barkahud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import top.likoslupus.barkahud.config.ConfigManager;

public class Common {

    public static HudData hudData;
    public static Minecraft client = null;
    public static boolean ridingBoat = false;
    public static HudRenderer hudRenderer;

    public void onInitializeClient() {
        client = Minecraft.getInstance();
        hudRenderer = new HudRenderer(client);

        ConfigManager.load();
        ConfigManager.migrateLegacyIfNecessary();

        ClientTickEvents.END_LEVEL_TICK.register(_ -> {
            if (client.player == null) return;
            if (client.player.getVehicle() instanceof AbstractBoat boat && boat.getFirstPassenger() == client.player) {
                if (hudData == null) {
                    hudData = new HudData();
                }
                hudData.update();
            } else {
                if (ridingBoat) {
                    ridingBoat = false;
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (!ConfigManager.get().cameraEnabled) return;
            if (client.player.getVehicle() instanceof AbstractBoat boat && boat.getControllingPassenger() == client.player) {
                CameraHandler.tick(boat, client.player);
            }
        });
    }

}
