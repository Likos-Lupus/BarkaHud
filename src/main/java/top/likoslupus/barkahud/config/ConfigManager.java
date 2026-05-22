package top.likoslupus.barkahud.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    private static BarkaHudConfig instance;
    private static boolean initialized = false;

    private ConfigManager() {
    }

    public static void save() {
        if (instance != null) {
            BarkaHudConfig.HANDLER.save();
        }
    }

    public static BarkaHudConfig get() {
        if (!initialized) {
            load();
        }
        return instance;
    }

    public static void load() {
        if (!initialized) {
            BarkaHudConfig.HANDLER.load();
            instance = BarkaHudConfig.HANDLER.instance();
            initialized = true;
        }
    }

    public static void resetToDefaults() {
        BarkaHudConfig defaults = new BarkaHudConfig();
        replace(defaults);
    }

    static void replace(BarkaHudConfig config) {
        instance = config;
        BarkaHudConfig.HANDLER.save();
        LOGGER.info("Replaced config with provided instance");
    }

    public static void migrateLegacyIfNecessary() {
        if (!initialized) {
            load();
        }
        var configDir = FabricLoader.getInstance().getConfigDir();
        LegacyPropertiesMigrator.migrate(
                configDir.resolve("barkahud.properties"),
                configDir.resolve("barkahud.properties.migrated"));
    }

}
