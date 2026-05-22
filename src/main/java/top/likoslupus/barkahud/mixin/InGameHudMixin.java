package top.likoslupus.barkahud.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.likoslupus.barkahud.HudController;
import top.likoslupus.barkahud.config.ConfigManager;
import top.likoslupus.barkahud.platform.HudDrawContextImpl;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(
            method = "extractHotbarAndDecorations",
            at = @At("TAIL")
    )
    public void render(GuiGraphicsExtractor graphics, DeltaTracker counter, CallbackInfo info) {
        var client = Minecraft.getInstance();
        var controller = HudController.getInstance();
        var snapshot = controller.getSnapshot();

        if (ConfigManager.get().hudEnabled && snapshot.isRiding() && !(client.screen instanceof ChatScreen)) {
            var context = new HudDrawContextImpl(
                    graphics, counter,
                    client.font, client.level.getGameTime()
            );
            controller.getRenderer().render(context, snapshot, ConfigManager.get());
        }
    }

}
