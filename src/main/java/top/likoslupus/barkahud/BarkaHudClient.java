package top.likoslupus.barkahud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import top.likoslupus.barkahud.config.ConfigManager;
import top.likoslupus.barkahud.platform.PlatformServices;

public class BarkaHudClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigManager.load();

        var hudController = new HudController(PlatformServices.clientSnapshotProvider());

        PlatformServices.hudRegistrar().register(hudController);
        PlatformServices.cameraPlatform().register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            hudController.tick();
        });
    }

}
