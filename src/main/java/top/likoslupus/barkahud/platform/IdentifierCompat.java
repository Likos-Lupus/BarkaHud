package top.likoslupus.barkahud.platform;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public final class IdentifierCompat {

    private IdentifierCompat() {
    }

    public static Identifier id(@NonNull String namespace, @NonNull String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

}
