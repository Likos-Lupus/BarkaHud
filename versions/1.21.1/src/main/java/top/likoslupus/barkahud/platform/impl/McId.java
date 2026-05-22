package top.likoslupus.barkahud.platform.impl;

import net.minecraft.resources.ResourceLocation;

public final class McId {

    private McId() {
    }

    public static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

}
