package top.likoslupus.barkahud.config;

import net.minecraft.util.Mth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

final class LegacyPropertiesMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyPropertiesMigrator.class);

    private LegacyPropertiesMigrator() {
    }

    static void migrate(Path oldPath, Path migratedPath) {
        if (!Files.isRegularFile(oldPath)) {
            return;
        }
        if (Files.exists(migratedPath)) {
            LOGGER.debug("Migration already completed, migrated marker exists");
            return;
        }

        try {
            Properties prop = loadProperties(oldPath);
            if (prop.isEmpty()) {
                LOGGER.debug("Empty legacy config, skipping migration");
                renameOldFile(oldPath, migratedPath);
                return;
            }

            BarkaHudConfig config = new BarkaHudConfig();

            if (prop.get("enabled") instanceof String val) {
                config.hudEnabled = Boolean.parseBoolean(val);
            }
            if (prop.get("extended") instanceof String val) {
                config.extendedHud = Boolean.parseBoolean(val);
            }
            if (prop.get("barType") instanceof String val) {
                int index = Integer.parseInt(val);
                if (index >= 0 && index < SpeedBarProfile.values().length) {
                    config.speedBarProfile = SpeedBarProfile.values()[index];
                }
            }
            if (prop.get("speedUnit") instanceof String val) {
                int index = Integer.parseInt(val);
                if (index >= 0 && index < SpeedUnit.values().length) {
                    config.speedUnit = SpeedUnit.values()[index];
                }
            }
            if (prop.get("cameraControl") instanceof String val) {
                config.cameraEnabled = Boolean.parseBoolean(val);
            }
            if (prop.get("cameraAggressiveness") instanceof String val) {
                float oldValue = Mth.clamp(Float.parseFloat(val), 0.2f, 3.5f);
                config.cameraAggressivenessMetersPerSecond = oldValue * 20.0f;
            }
            if (prop.get("cameraSmoothing") instanceof String val) {
                float oldValue = Mth.clamp(Float.parseFloat(val), 0f, 0.9f);
                config.cameraSmoothingPercent = oldValue / 0.009f;
            }

            ConfigManager.replace(config);
            renameOldFile(oldPath, migratedPath);
            LOGGER.info("Legacy config migrated to barkahud.json5");
        } catch (Exception e) {
            LOGGER.warn("Failed to migrate legacy config, keeping original file", e);
        }
    }

    private static Properties loadProperties(Path path) throws IOException {
        Properties prop = new Properties();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            prop.load(br);
        }
        return prop;
    }

    private static void renameOldFile(Path oldPath, Path migratedPath) throws IOException {
        Files.move(oldPath, migratedPath);
    }

}
