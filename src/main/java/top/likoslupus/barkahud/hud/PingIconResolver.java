package top.likoslupus.barkahud.hud;

import org.jspecify.annotations.NonNull;

public final class PingIconResolver {

    private PingIconResolver() {
    }

    public static @NonNull HudSprite resolve(int ping) {
        if (ping < 0) return HudAssets.PING_UNKNOWN;
        if (ping < 150) return HudAssets.PING_5;
        if (ping < 300) return HudAssets.PING_4;
        if (ping < 600) return HudAssets.PING_3;
        if (ping < 1000) return HudAssets.PING_2;
        return HudAssets.PING_1;
    }

}
