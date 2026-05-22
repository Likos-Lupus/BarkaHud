package top.likoslupus.barkahud.hud;

import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.SpeedBar;
import top.likoslupus.barkahud.config.SpeedBarProfile;
import top.likoslupus.barkahud.platform.IHudDrawContext;

public final class SpeedBarRenderer {

    private SpeedBarRenderer() {
    }

    public static void render(
            @NonNull IHudDrawContext context,
            int x,
            int y,
            double displayedSpeed,
            @NonNull SpeedBarProfile profile,
            long gameTime
    ) {
        var bar = SpeedBar.values()[profile.ordinal()];
        context.drawSprite(
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
            context.drawSprite(
                    bar.litBar,
                    x,
                    y,
                    HudLayout.BAR_WIDTH,
                    HudLayout.BAR_HEIGHT
            );
            return;
        }

        context.drawSpriteRegion(
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
