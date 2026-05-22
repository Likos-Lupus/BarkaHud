package top.likoslupus.barkahud.hud;

import net.minecraft.resources.Identifier;

public final class HudAssets {

    public static final Identifier BACKGROUND_EXTENDED = id("background_extended");
    public static final Identifier BACKGROUND_COMPACT = id("background_compact");
    public static final Identifier LEFT_UNLIT = id("left_unlit");
    public static final Identifier LEFT_LIT = id("left_lit");
    public static final Identifier RIGHT_UNLIT = id("right_unlit");
    public static final Identifier RIGHT_LIT = id("right_lit");
    public static final Identifier FORWARD_UNLIT = id("forward_unlit");
    public static final Identifier FORWARD_LIT = id("forward_lit");
    public static final Identifier BACKWARD_UNLIT = id("backward_unlit");
    public static final Identifier BACKWARD_LIT = id("backward_lit");
    public static final Identifier PING_5 = id("ping_5");
    public static final Identifier PING_4 = id("ping_4");
    public static final Identifier PING_3 = id("ping_3");
    public static final Identifier PING_2 = id("ping_2");
    public static final Identifier PING_1 = id("ping_1");
    public static final Identifier PING_UNKNOWN = id("ping_unknown");

    private HudAssets() {
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath("barkahud", path);
    }

}
