package top.likoslupus.barkahud.platform;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public interface IHudDrawContext {

    int guiWidth();

    int guiHeight();

    float tickDelta();

    long gameTime();

    int textWidth(@NonNull String text);

    void drawSprite(
            @NonNull Identifier sprite,
            int x,
            int y,
            int w,
            int h
    );

    void drawSpriteRegion(
            @NonNull Identifier sprite,
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
