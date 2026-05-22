package top.likoslupus.barkahud.config;

import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.hud.HudSprite;

public enum SpeedBarProfile {

    PACKED(
            0,
            40,
            4.55,
            HudSprite.barkahud("bar_1_lit"),
            HudSprite.barkahud("bar_1_unlit"),
            "barkahud.enum.speed_bar_profile.PACKED"
    ),
    MIXED(
            8,
            70,
            182.0 / 62.0,
            HudSprite.barkahud("bar_2_lit"),
            HudSprite.barkahud("bar_2_unlit"),
            "barkahud.enum.speed_bar_profile.MIXED"
    ),
    BLUE(
            40,
            70,
            182.0 / 30.0,
            HudSprite.barkahud("bar_3_lit"),
            HudSprite.barkahud("bar_3_unlit"),
            "barkahud.enum.speed_bar_profile.BLUE"
    ),
    PROGRESSIVE(
            0,
            70,
            0,
            HudSprite.barkahud("bar_4_lit"),
            HudSprite.barkahud("bar_4_unlit"),
            "barkahud.enum.speed_bar_profile.PROGRESSIVE"
    ) {
        @Override
        public int getProgress(double displayedSpeed, int barWidth) {
            double ratio = (double) barWidth / 182.0;
            if (displayedSpeed < this.minV) return 0;
            if (displayedSpeed > this.maxV) return -1;
            if (displayedSpeed < 8) return (int) (displayedSpeed * 22.75 * ratio);
            if (displayedSpeed < 40) return (int) ((displayedSpeed - 8) * 5.6875 * ratio);
            return (int) ((displayedSpeed - 40) * 6.0666 * ratio);
        }
    };

    public final double minV;
    public final double maxV;
    public final double scaleV;
    public final HudSprite litBar;
    public final HudSprite unlitBar;
    public final String translationKey;

    SpeedBarProfile(
            double minV,
            double maxV,
            double scaleV,
            @NonNull HudSprite litBar,
            @NonNull HudSprite unlitBar,
            @NonNull String translationKey
    ) {
        this.minV = minV;
        this.maxV = maxV;
        this.scaleV = scaleV;
        this.litBar = litBar;
        this.unlitBar = unlitBar;
        this.translationKey = translationKey;
    }

    public int getProgress(double displayedSpeed, int barWidth) {
        double ratio = (double) barWidth / 182.0;
        if (displayedSpeed < this.minV) return 0;
        if (displayedSpeed > this.maxV) return -1;
        return (int) ((displayedSpeed - this.minV) * this.scaleV * ratio);
    }

}
