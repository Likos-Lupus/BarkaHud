package top.likoslupus.barkahud.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import top.likoslupus.barkahud.config.BarkaHudConfig;
import top.likoslupus.barkahud.config.ConfigManager;
import top.likoslupus.barkahud.config.SpeedBarProfile;
import top.likoslupus.barkahud.config.SpeedUnit;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuIntegration::createScreen;
    }

    private static Screen createScreen(Screen parent) {
        var defaults = new BarkaHudConfig();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("barkahud.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("barkahud.config.category.hud"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("barkahud.option.enabled"))
                                .binding(defaults.hudEnabled,
                                        () -> ConfigManager.get().hudEnabled,
                                        val -> ConfigManager.get().hudEnabled = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("barkahud.option.extended"))
                                .description(OptionDescription.of(Component.translatable("barkahud.tooltip.extended")))
                                .binding(defaults.extendedHud,
                                        () -> ConfigManager.get().extendedHud,
                                        val -> ConfigManager.get().extendedHud = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<SpeedUnit>createBuilder()
                                .name(Component.translatable("barkahud.option.speed_format"))
                                .binding(defaults.speedUnit,
                                        () -> ConfigManager.get().speedUnit,
                                        val -> ConfigManager.get().speedUnit = val)
                                .controller(opt -> EnumControllerBuilder.create(opt)
                                        .enumClass(SpeedUnit.class)
                                        .valueFormatter(v -> Component.translatable(v.translationKey)))
                                .build())
                        .option(Option.<SpeedBarProfile>createBuilder()
                                .name(Component.translatable("barkahud.option.bar_type"))
                                .description(OptionDescription.of(
                                        Component.translatable("barkahud.tooltip.bar_type"),
                                        Component.translatable("barkahud.tooltip.bar_type.packed"),
                                        Component.translatable("barkahud.tooltip.bar_type.mixed"),
                                        Component.translatable("barkahud.tooltip.bar_type.blue"),
                                        Component.translatable("barkahud.tooltip.bar_type.progressive")))
                                .binding(defaults.speedBarProfile,
                                        () -> ConfigManager.get().speedBarProfile,
                                        val -> ConfigManager.get().speedBarProfile = val)
                                .controller(opt -> EnumControllerBuilder.create(opt)
                                        .enumClass(SpeedBarProfile.class)
                                        .valueFormatter(v -> Component.translatable(v.translationKey)))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("barkahud.config.category.camera"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("barkahud.option.camera_control"))
                                .description(OptionDescription.of(Component.translatable("barkahud.tooltip.camera_control")))
                                .binding(defaults.cameraEnabled,
                                        () -> ConfigManager.get().cameraEnabled,
                                        val -> ConfigManager.get().cameraEnabled = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("barkahud.option.camera_aggressiveness"))
                                .description(OptionDescription.of(Component.translatable("barkahud.tooltip.camera_aggressiveness")))
                                .binding(defaults.cameraAggressivenessMetersPerSecond,
                                        () -> ConfigManager.get().cameraAggressivenessMetersPerSecond,
                                        val -> ConfigManager.get().cameraAggressivenessMetersPerSecond = val)
                                .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                        .min(4.0f)
                                        .max(70.0f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("barkahud.option.camera_smoothing"))
                                .description(OptionDescription.of(Component.translatable("barkahud.tooltip.camera_smoothing")))
                                .binding(defaults.cameraSmoothingPercent,
                                        () -> ConfigManager.get().cameraSmoothingPercent,
                                        val -> ConfigManager.get().cameraSmoothingPercent = val)
                                .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                        .min(0.0f)
                                        .max(100.0f))
                                .build())
                        .build())
                .save(ConfigManager::save)
                .build()
                .generateScreen(parent);
    }

}
