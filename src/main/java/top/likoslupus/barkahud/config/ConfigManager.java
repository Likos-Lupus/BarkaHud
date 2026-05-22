package top.likoslupus.barkahud.config;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration access and persistence. All methods are expected to be called exclusively from the client/render
 * thread.
 */
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

    static void replace(@NonNull BarkaHudConfig config) {
        BarkaHudConfig target = BarkaHudConfig.HANDLER.instance();

        target.schemaVersion = config.schemaVersion;
        target.hudEnabled = config.hudEnabled;
        target.extendedHud = config.extendedHud;
        target.speedUnit = config.speedUnit;
        target.speedBarProfile = config.speedBarProfile;
        target.cameraEnabled = config.cameraEnabled;
        target.cameraAggressivenessMetersPerSecond = config.cameraAggressivenessMetersPerSecond;
        target.cameraSmoothingPercent = config.cameraSmoothingPercent;

        instance = target;
        BarkaHudConfig.HANDLER.save();
        LOGGER.info("Replaced config with provided instance");
    }

}
