package top.likoslupus.barkahud.platform.impl;

import net.minecraft.resources.Identifier;

public final class McId {

    private McId() {
    }

    public static Identifier id(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

}
