package top.likoslupus.barkahud.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class BarkaHudConfig {

    public static final ConfigClassHandler<BarkaHudConfig> HANDLER =
            ConfigClassHandler.createBuilder(BarkaHudConfig.class)
                    .id(Identifier.fromNamespaceAndPath("barkahud", "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve("barkahud.json5"))
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    public int schemaVersion = 1;

    @SerialEntry
    public boolean hudEnabled = true;

    @SerialEntry
    public boolean extendedHud = true;

    @SerialEntry
    public SpeedUnit speedUnit = SpeedUnit.KILOMETERS_PER_HOUR;

    @SerialEntry
    public SpeedBarProfile speedBarProfile = SpeedBarProfile.PACKED;

    @SerialEntry
    public boolean cameraEnabled = false;

    @SerialEntry
    public float cameraAggressivenessMetersPerSecond = 60.0f;

    @SerialEntry
    public float cameraSmoothingPercent = 50.0f;

}
