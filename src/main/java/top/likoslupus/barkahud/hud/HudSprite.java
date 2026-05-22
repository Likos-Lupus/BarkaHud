package top.likoslupus.barkahud.hud;

import org.jspecify.annotations.NonNull;

public record HudSprite(
        String namespace,
        String path
) {

    public static @NonNull HudSprite barkahud(String path) {
        return new HudSprite("barkahud", path);
    }

}
