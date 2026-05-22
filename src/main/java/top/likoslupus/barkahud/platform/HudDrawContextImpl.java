package top.likoslupus.barkahud.platform;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class HudDrawContextImpl implements IHudDrawContext {

    private final GuiGraphicsExtractor graphics;
    private final DeltaTracker counter;
    private final Font font;
    private final long gameTime;

    public HudDrawContextImpl(
            @NonNull GuiGraphicsExtractor graphics,
            @NonNull DeltaTracker counter,
            @NonNull Font font,
            long gameTime
    ) {
        this.graphics = graphics;
        this.counter = counter;
        this.font = font;
        this.gameTime = gameTime;
    }

    @Override
    public int guiWidth() {
        return graphics.guiWidth();
    }

    @Override
    public int guiHeight() {
        return graphics.guiHeight();
    }

    @Override
    public float tickDelta() {
        return counter.getGameTimeDeltaPartialTick(true);
    }

    @Override
    public long gameTime() {
        return gameTime;
    }

    @Override
    public int textWidth(@NonNull String text) {
        return font.width(text);
    }

    @Override
    public void drawSprite(
            @NonNull Identifier sprite,
            int x,
            int y,
            int w,
            int h
    ) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, w, h);
    }

    @Override
    public void drawSpriteRegion(
            @NonNull Identifier sprite,
            int texW,
            int texH,
            int u,
            int v,
            int x,
            int y,
            int w,
            int h
    ) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, texW, texH, u, v, x, y, w, h);
    }

    @Override
    public void drawText(
            @NonNull String text,
            int x,
            int y,
            int color
    ) {
        graphics.text(font, text, x, y, color);
    }

}
