package top.likoslupus.barkahud.platform;

import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.hud.HudSprite;

public interface IHudDrawContext {

    int guiWidth();

    int guiHeight();

    float tickDelta();

    long gameTime();

    int textWidth(@NonNull String text);

    void drawSprite(
            @NonNull HudSprite sprite,
            int x,
            int y,
            int w,
            int h
    );

    void drawSpriteRegion(
            @NonNull HudSprite sprite,
            int texW,
            int texH,
            int u,
            int v,
            int x,
            int y,
            int w,
            int h
    );

    void drawText(
            @NonNull String text,
            int x,
            int y,
            int color
    );

}
