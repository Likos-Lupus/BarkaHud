package top.likoslupus.barkahud.platform.impl;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import top.likoslupus.barkahud.HudController;
import top.likoslupus.barkahud.config.ConfigManager;
import top.likoslupus.barkahud.platform.IHudRegistrar;

public class FabricHudRegistrar implements IHudRegistrar {

    @Override
    public void register(HudController controller) {
        HudRenderCallback.EVENT.register(
                (graphics, tickCounter) -> {
                    var client = Minecraft.getInstance();
                    var snapshot = controller.getSnapshot();
                    if (ConfigManager.get().hudEnabled
                            && snapshot.isRiding()
                            && !(client.screen instanceof ChatScreen)) {
                        var context = new HudDrawContextImpl(
                                graphics,
                                tickCounter,
                                client.font,
                                client.level.getGameTime()
                        );
                        controller.getRenderer().render(context, snapshot, ConfigManager.get());
                    }
                }
        );
    }

}
