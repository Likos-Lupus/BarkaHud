package top.likoslupus.barkahud;

import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.hud.HudSprite;

public enum SpeedBar {

    PACKED(
            0,
            40,
            4.55,
            HudSprite.barkahud("bar_1_lit"),
            HudSprite.barkahud("bar_1_unlit")
    ),
    MIXED(
            8,
            70,
            182d / 62d,
            HudSprite.barkahud("bar_2_lit"),
            HudSprite.barkahud("bar_2_unlit")
    ),
    BLUE(
            40,
            70,
            182d / 30d,
            HudSprite.barkahud("bar_3_lit"),
            HudSprite.barkahud("bar_3_unlit")
    ),
    PROGRESSIVE(
            0,
            70,
            0,
            HudSprite.barkahud("bar_4_lit"),
            HudSprite.barkahud("bar_4_unlit")
    ) {
        @Override
        public int getProgress(double displayedSpeed) {
            if (displayedSpeed < this.minV) return 0;
            if (displayedSpeed > this.maxV) return -1;
            if (displayedSpeed < 8) return (int) (displayedSpeed * 22.75);
            if (displayedSpeed < 40) return (int) ((displayedSpeed - 8) * 5.6875);
            return (int) ((displayedSpeed - 40) * 6.0666);
        }
    };

    public final double minV, maxV, scaleV;
    public final HudSprite litBar, unlitBar;

    SpeedBar(
            double minV,
            double maxV,
            double scaleV,
            @NonNull HudSprite litBar,
            @NonNull HudSprite unlitBar
    ) {
        this.minV = minV;
        this.maxV = maxV;
        this.scaleV = scaleV;
        this.litBar = litBar;
        this.unlitBar = unlitBar;
    }

    public int getProgress(double displayedSpeed) {
        if (displayedSpeed < this.minV) return 0;
        if (displayedSpeed > this.maxV) return -1;
        return (int) ((displayedSpeed - this.minV) * this.scaleV);
    }

}
