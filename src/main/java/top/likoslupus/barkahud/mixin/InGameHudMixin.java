package top.likoslupus.barkahud.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.likoslupus.barkahud.Common;
import top.likoslupus.barkahud.config.ConfigManager;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(
            method = "extractHotbarAndDecorations",
            at = @At("TAIL")
    )
    public void render(GuiGraphicsExtractor graphics, DeltaTracker counter, CallbackInfo info) {
        if (ConfigManager.get().hudEnabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen)) {
            Common.hudRenderer.render(graphics, counter);
        }
    }

}
