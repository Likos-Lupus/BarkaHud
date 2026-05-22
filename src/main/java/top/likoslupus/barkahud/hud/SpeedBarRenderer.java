package top.likoslupus.barkahud.hud;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import top.likoslupus.barkahud.SpeedBar;
import top.likoslupus.barkahud.config.SpeedBarProfile;

public final class SpeedBarRenderer {

    private SpeedBarRenderer() {
    }

    public static void render(
            GuiGraphicsExtractor graphics,
            int x,
            int y,
            double displayedSpeed,
            SpeedBarProfile profile,
            long gameTime
    ) {
        var bar = SpeedBar.values()[profile.ordinal()];
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                bar.unlitBar,
                x,
                y,
                HudLayout.BAR_WIDTH,
                HudLayout.BAR_HEIGHT
        );
        int progress = profile.getProgress(displayedSpeed, HudLayout.BAR_WIDTH);

        if (progress == 0) return;
        if (progress == -1) {
            if (gameTime % 2 == 0) return;
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    bar.litBar,
                    x,
                    y,
                    HudLayout.BAR_WIDTH,
                    HudLayout.BAR_HEIGHT
            );
            return;
        }

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                bar.litBar,
                HudLayout.BAR_WIDTH,
                HudLayout.BAR_HEIGHT,
                0,
                0,
                x,
                y,
                progress,
                HudLayout.BAR_HEIGHT
        );
    }

}
