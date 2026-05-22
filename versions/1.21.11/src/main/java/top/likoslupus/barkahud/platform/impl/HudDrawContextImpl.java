package top.likoslupus.barkahud.platform.impl;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.hud.HudSprite;
import top.likoslupus.barkahud.platform.IHudDrawContext;

public class HudDrawContextImpl implements IHudDrawContext {

    private final GuiGraphics graphics;
    private final DeltaTracker counter;
    private final Font font;
    private final long gameTime;

    public HudDrawContextImpl(
            @NonNull GuiGraphics graphics,
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
            @NonNull HudSprite sprite,
            int x,
            int y,
            int w,
            int h
    ) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, toId(sprite), x, y, w, h);
    }

    @Override
    public void drawSpriteRegion(
            @NonNull HudSprite sprite,
            int texW,
            int texH,
            int u,
            int v,
            int x,
            int y,
            int w,
            int h
    ) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, toId(sprite), texW, texH, u, v, x, y, w, h);
    }

    @Override
    public void drawText(
            @NonNull String text,
            int x,
            int y,
            int color
    ) {
        graphics.drawString(font, text, x, y, color);
    }

    private static Identifier toId(@NonNull HudSprite sprite) {
        return Identifier.fromNamespaceAndPath(sprite.namespace(), sprite.path());
    }

}
