package top.likoslupus.barkahud.platform;

import net.minecraft.client.Minecraft;
import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.platform.impl.FabricCameraPlatform;
import top.likoslupus.barkahud.platform.impl.FabricHudRegistrar;
import top.likoslupus.barkahud.platform.impl.MinecraftClientSnapshotProvider;

public final class PlatformServices {

    private PlatformServices() {
    }

    public static @NonNull IHudRegistrar hudRegistrar() {
        return new FabricHudRegistrar();
    }

    public static @NonNull IClientSnapshotProvider clientSnapshotProvider() {
        return new MinecraftClientSnapshotProvider(Minecraft.getInstance());
    }

    public static @NonNull ICameraPlatform cameraPlatform() {
        return new FabricCameraPlatform();
    }

}
