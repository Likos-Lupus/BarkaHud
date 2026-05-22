package top.likoslupus.barkahud;

import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.config.BarkaHudConfig;
import top.likoslupus.barkahud.core.snapshot.HudSnapshot;
import top.likoslupus.barkahud.hud.HudAssets;
import top.likoslupus.barkahud.hud.HudLayout;
import top.likoslupus.barkahud.hud.PingIconResolver;
import top.likoslupus.barkahud.hud.SpeedBarRenderer;
import top.likoslupus.barkahud.platform.IHudDrawContext;

public class HudRenderer {

    private static final String ANGLE_FORMAT = "%03.0f \u00b0";
    private static final String G_FORMAT = "%+.1f g";

    private double displayedSpeed = 0.0d;

    public void render(
            @NonNull IHudDrawContext context,
            @NonNull HudSnapshot snapshot,
            @NonNull BarkaHudConfig config
    ) {
        int scaledWidth = context.guiWidth();
        int scaledHeight = context.guiHeight();
        int centerX = scaledWidth / 2;

        this.displayedSpeed = Mth.lerp(
                context.tickDelta(),
                this.displayedSpeed,
                snapshot.speedMS()
        );

        if (config.extendedHud) {
            renderExtended(context, centerX, scaledHeight, snapshot, config);
        } else {
            renderCompact(context, centerX, scaledHeight, snapshot, config);
        }
    }

    private void renderExtended(
            @NonNull IHudDrawContext context,
            int centerX,
            int scaledHeight,
            @NonNull HudSnapshot snapshot,
            @NonNull BarkaHudConfig config
    ) {
        int x = centerX - HudLayout.HUD_X_OFFSET;
        int y = scaledHeight - HudLayout.HUD_Y_OFFSET;

        context.drawSprite(
                HudAssets.BACKGROUND_EXTENDED,
                x,
                y,
                HudLayout.BAR_WIDTH,
                HudLayout.EXTENDED_HEIGHT
        );
        SpeedBarRenderer.render(
                context,
                x,
                y,
                this.displayedSpeed,
                config.speedBarProfile,
                context.gameTime()
        );

        if (snapshot.isDriver()) {
            renderInputKeys(context, centerX, snapshot);
        }

        int nameLen = context.textWidth(snapshot.playerName());
        int pingX = centerX + HudLayout.PING_X_OFFSET - nameLen;
        int pingY = scaledHeight - HudLayout.ARROW_ROW_Y;
        context.drawSprite(
                PingIconResolver.resolve(snapshot.ping()),
                pingX,
                pingY,
                HudLayout.PING_ICON_SIZE,
                HudLayout.PING_ICON_H
        );

        int textY = scaledHeight - HudLayout.TEXT_ROW_Y;
        typeCentered(
                context,
                String.format(config.speedUnit.format, this.displayedSpeed * config.speedUnit.multiplier),
                centerX - HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                context,
                String.format(ANGLE_FORMAT, snapshot.driftAngle()),
                centerX,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                context,
                String.format(G_FORMAT, snapshot.gForce()),
                centerX + HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );

        int nameX = centerX + HudLayout.NAME_X_OFFSET - nameLen;
        int nameY = scaledHeight - HudLayout.ARROW_ROW_Y;
        context.drawText(
                snapshot.playerName(),
                nameX,
                nameY,
                HudLayout.TEXT_COLOR
        );
    }

    private void renderCompact(
            @NonNull IHudDrawContext context,
            int centerX,
            int scaledHeight,
            @NonNull HudSnapshot snapshot,
            @NonNull BarkaHudConfig config
    ) {
        int x = centerX - HudLayout.HUD_X_OFFSET;
        int y = scaledHeight - HudLayout.HUD_Y_OFFSET;

        context.drawSprite(
                HudAssets.BACKGROUND_COMPACT,
                x,
                y,
                HudLayout.BAR_WIDTH,
                HudLayout.COMPACT_HEIGHT
        );
        SpeedBarRenderer.render(
                context,
                x,
                y,
                this.displayedSpeed,
                config.speedBarProfile,
                context.gameTime()
        );

        int textY = scaledHeight - HudLayout.TEXT_ROW_Y;
        typeCentered(
                context,
                String.format(config.speedUnit.format, this.displayedSpeed * config.speedUnit.multiplier),
                centerX - HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
        typeCentered(
                context,
                String.format(ANGLE_FORMAT, snapshot.driftAngle()),
                centerX + HudLayout.SPEED_TEXT_X,
                textY,
                HudLayout.TEXT_COLOR
        );
    }

    private void renderInputKeys(
            @NonNull IHudDrawContext context,
            int centerX,
            @NonNull HudSnapshot snapshot
    ) {
        int y = context.guiHeight() - HudLayout.ARROW_ROW_Y;

        context.drawSprite(
                snapshot.keyLeft() ? HudAssets.LEFT_LIT : HudAssets.LEFT_UNLIT,
                centerX - HudLayout.LEFT_ARROW_X,
                y,
                HudLayout.ARROW_SPRITE_W,
                HudLayout.ARROW_SPRITE_H
        );
        context.drawSprite(
                snapshot.keyRight() ? HudAssets.RIGHT_LIT : HudAssets.RIGHT_UNLIT,
                centerX - HudLayout.RIGHT_ARROW_X,
                y,
                HudLayout.ARROW_SPRITE_W,
                HudLayout.ARROW_SPRITE_H
        );

        int brakeY = context.guiHeight() - HudLayout.BRAKE_THROTTLE_Y;
        context.drawSprite(
                snapshot.keyForward() ? HudAssets.FORWARD_LIT : HudAssets.FORWARD_UNLIT,
                centerX,
                brakeY,
                HudLayout.BRAKE_THROTTLE_W,
                HudLayout.BRAKE_THROTTLE_H
        );
        context.drawSprite(
                snapshot.keyBackward() ? HudAssets.BACKWARD_LIT : HudAssets.BACKWARD_UNLIT,
                centerX - HudLayout.BRAKE_THROTTLE_W,
                brakeY,
                HudLayout.BRAKE_THROTTLE_W,
                HudLayout.BRAKE_THROTTLE_H
        );
    }

    private void typeCentered(
            @NonNull IHudDrawContext context,
            @NonNull String text,
            int centerX,
            int y,
            int color
    ) {
        context.drawText(text, centerX - context.textWidth(text) / 2, y, color);
    }

}
