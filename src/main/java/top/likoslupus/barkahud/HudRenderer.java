package top.likoslupus.barkahud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import top.likoslupus.barkahud.config.BarkaHudConfig;
import top.likoslupus.barkahud.hud.HudAssets;
import top.likoslupus.barkahud.hud.HudLayout;
import top.likoslupus.barkahud.hud.PingIconResolver;
import top.likoslupus.barkahud.hud.SpeedBarRenderer;

public class HudRenderer {

    private static final String ANGLE_FORMAT = "%03.0f \u00b0";
    private static final String G_FORMAT = "%+.1f g";

    private final Minecraft client;
    private int scaledWidth;
    private int scaledHeight;
    private double displayedSpeed = 0.0d;

    public HudRenderer(Minecraft client) {
        this.client = client;
    }

    public void render(
            GuiGraphicsExtractor graphics,
            DeltaTracker counter,
            HudSnapshot snapshot,
            BarkaHudConfig config
    ) {
        this.scaledWidth = graphics.guiWidth();
        this.scaledHeight = graphics.guiHeight();
        int i = this.scaledWidth / 2;

        this.displayedSpeed = Mth.lerp(
                counter.getGameTimeDeltaPartialTick(true),
                this.displayedSpeed,
                snapshot.speedMS()
        );

        if (config.extendedHud) {
            renderExtended(graphics, i, snapshot, config);
        } else {
            renderCompact(graphics, i, snapshot, config);
        }
    }

    private void renderExtended(
            GuiGraphicsExtractor graphics,
            int centerX,
            HudSnapshot snapshot,
            BarkaHudConfig config
    ) {
        int x = centerX - HudLayout.HUD_X_OFFSET;
        int y = this.scaledHeight - HudLayout.HUD_Y_OFFSET;

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                HudAssets.BACKGROUND_EXTENDED,
                x,
                y,
                HudLayout.BAR_WIDTH,
                HudLayout.EXTENDED_HEIGHT
        );
        SpeedBarRenderer.render(
                graphics,
                x,
                y,
                this.displayedSpeed,
                config.speedBarProfile,
                this.client.level.getGameTime()
        );

        if (snapshot.isDriver()) {
            renderInputKeys(graphics, centerX);
        }

        int nameLen = this.client.font.width(snapshot.playerName());
        int pingX = centerX + HudLayout.PING_X_OFFSET - nameLen;
        int pingY = this.scaledHeight - HudLayout.ARROW_ROW_Y;
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                PingIconResolver.resolve(snapshot.ping()),
                pingX,
                pingY,
                HudLayout.PING_ICON_SIZE,
                HudLayout.PING_ICON_H
        );

        int textY = this.scaledHeight - HudLayout.TEXT_ROW_Y;
        typeCentered(
                graphics,
                String.format(config.speedUnit.format, this.displayedSpeed * config.speedUnit.multiplier),
                centerX - HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                graphics,
                String.format(ANGLE_FORMAT, snapshot.driftAngle()),
                centerX,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                graphics,
                String.format(G_FORMAT, snapshot.gForce()),
                centerX + HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );

        int nameX = centerX + HudLayout.NAME_X_OFFSET - nameLen;
        int nameY = this.scaledHeight - HudLayout.ARROW_ROW_Y;
        graphics.text(
                this.client.font,
                snapshot.playerName(),
                nameX,
                nameY,
                HudLayout.TEXT_COLOR
        );
    }

    private void renderCompact(
            GuiGraphicsExtractor graphics,
            int centerX,
            HudSnapshot snapshot,
            BarkaHudConfig config
    ) {
        int x = centerX - HudLayout.HUD_X_OFFSET;
        int y = this.scaledHeight - HudLayout.HUD_Y_OFFSET;

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                HudAssets.BACKGROUND_COMPACT,
                x,
                y,
                HudLayout.BAR_WIDTH,
                HudLayout.COMPACT_HEIGHT
        );
        SpeedBarRenderer.render(
                graphics,
                x,
                y,
                this.displayedSpeed,
                config.speedBarProfile,
                this.client.level.getGameTime()
        );

        int textY = this.scaledHeight - HudLayout.TEXT_ROW_Y;
        typeCentered(
                graphics,
                String.format(config.speedUnit.format, this.displayedSpeed * config.speedUnit.multiplier),
                centerX - HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                graphics,
                String.format(ANGLE_FORMAT, snapshot.driftAngle()),
                centerX + HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
    }

    private void renderInputKeys(GuiGraphicsExtractor graphics, int centerX) {
        var opts = this.client.options;
        int y = this.scaledHeight - HudLayout.ARROW_ROW_Y;

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                opts.keyLeft.isDown() ? HudAssets.LEFT_LIT : HudAssets.LEFT_UNLIT,
                centerX - HudLayout.LEFT_ARROW_X, y, HudLayout.ARROW_SPRITE_W, HudLayout.ARROW_SPRITE_H);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                opts.keyRight.isDown() ? HudAssets.RIGHT_LIT : HudAssets.RIGHT_UNLIT,
                centerX - HudLayout.RIGHT_ARROW_X, y, HudLayout.ARROW_SPRITE_W, HudLayout.ARROW_SPRITE_H);

        int brakeY = this.scaledHeight - HudLayout.BRAKE_THROTTLE_Y;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                opts.keyUp.isDown() ? HudAssets.FORWARD_LIT : HudAssets.FORWARD_UNLIT,
                centerX, brakeY, HudLayout.BRAKE_THROTTLE_W, HudLayout.BRAKE_THROTTLE_H);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                opts.keyDown.isDown() ? HudAssets.BACKWARD_LIT : HudAssets.BACKWARD_UNLIT,
                centerX - HudLayout.BRAKE_THROTTLE_W, brakeY, HudLayout.BRAKE_THROTTLE_W, HudLayout.BRAKE_THROTTLE_H);
    }

    private void typeCentered(
            GuiGraphicsExtractor graphics,
            String text,
            int centerX,
            int y,
            int color
    ) {
        graphics.text(this.client.font, text, centerX - this.client.font.width(text) / 2, y, color);
    }

}
