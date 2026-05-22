package top.likoslupus.barkahud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import top.likoslupus.barkahud.config.ConfigManager;

public class BarkaHudClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        ConfigManager.migrateLegacyIfNecessary();

        var client = Minecraft.getInstance();
        var hudController = new HudController(client);

        ClientTickEvents.END_LEVEL_TICK.register(level -> {
            hudController.tick();
        });

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (c.player == null) return;
            if (!ConfigManager.get().cameraEnabled) return;
            if (c.player.getVehicle() instanceof AbstractBoat boat && boat.getControllingPassenger() == c.player) {
                CameraHandler.tick(boat, c.player);
            }
        });
    }

}
