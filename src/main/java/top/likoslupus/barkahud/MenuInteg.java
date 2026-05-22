package top.likoslupus.barkahud;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MenuInteg implements ModMenuApi {

    private static final MutableComponent
            TITLE = Component.translatable("barkahud.config.title"),
            CAT = Component.translatable("barkahud.config.cat"),
            ENABLED = Component.translatable("barkahud.option.enabled"),
            EXTENDED = Component.translatable("barkahud.option.extended"),
            CAMERA_CONTROL = Component.translatable("barkahud.option.camera_control"),
            CAMERA_AGGRESSIVENESS = Component.translatable("barkahud.option.camera_aggressiveness"),
            CAMERA_SMOOTHING = Component.translatable("barkahud.option.camera_smoothing"),
            BAR_TYPE = Component.translatable("barkahud.option.bar_type"),
            SPEED_FORMAT = Component.translatable("barkahud.option.speed_format"),
            TIP_EXTENDED = Component.translatable("barkahud.tooltip.extended"),
            TIP_CAMERA_CONTROL = Component.translatable("barkahud.tooltip.camera_control"),
            TIP_CAMERA_AGGRESSIVENESS = Component.translatable("barkahud.tooltip.camera_aggressiveness"),
            TIP_CAMERA_SMOOTHING = Component.translatable("barkahud.tooltip.camera_smoothing"),
            TIP_BAR = Component.translatable("barkahud.tooltip.bar_type"),
            TIP_BAR_PACKED = Component.translatable("barkahud.tooltip.bar_type.packed"),
            TIP_BAR_MIXED = Component.translatable("barkahud.tooltip.bar_type.mixed"),
            TIP_BAR_BLUE = Component.translatable("barkahud.tooltip.bar_type.blue"),
            TIP_BAR_PROGRESSIVE = Component.translatable("barkahud.tooltip.bar_type.progressive");

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(TITLE);
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            builder.getOrCreateCategory(CAT)

                    .addEntry(entryBuilder.startBooleanToggle(ENABLED, Config.enabled)
                            .setDefaultValue(true)
                            .setSaveConsumer(newVal -> Config.enabled = newVal)
                            .build())

                    .addEntry(entryBuilder.startBooleanToggle(EXTENDED, Config.extended)
                            .setDefaultValue(true)
                            .setTooltip(TIP_EXTENDED)
                            .setSaveConsumer(newVal -> Config.extended = newVal)
                            .build())

                    .addEntry(entryBuilder.startEnumSelector(SPEED_FORMAT, SpeedFormat.class, SpeedFormat.values()[Config.configSpeedType])
                            .setDefaultValue(SpeedFormat.KMPH)
                            .setSaveConsumer(newVal -> Config.setUnit(newVal.ordinal()))
                            .setEnumNameProvider(value -> Component.translatable("barkahud.option.speed_format." + value.toString()))
                            .build())

                    .addEntry(entryBuilder.startEnumSelector(BAR_TYPE, SpeedBar.class, Config.barType)
                            .setDefaultValue(SpeedBar.PACKED)
                            .setTooltip(TIP_BAR, TIP_BAR_PACKED, TIP_BAR_MIXED, TIP_BAR_BLUE, TIP_BAR_PROGRESSIVE)
                            .setSaveConsumer(newVal -> Config.barType = newVal)
                            .setEnumNameProvider(value -> Component.translatable("barkahud.option.bar_type." + value.toString()))
                            .build())

                    .addEntry(entryBuilder.startBooleanToggle(CAMERA_CONTROL, Config.cameraControl)
                            .setDefaultValue(true)
                            .setTooltip(TIP_CAMERA_CONTROL)
                            .setSaveConsumer(newVal -> Config.cameraControl = newVal)
                            .build())

                    .addEntry(entryBuilder.startFloatField(CAMERA_AGGRESSIVENESS, Config.cameraAggressiveness * 20)
                            .setDefaultValue(60)
                            .setTooltip(TIP_CAMERA_AGGRESSIVENESS)
                            .setMin(4).setMax(70)
                            .setSaveConsumer(newVal -> Config.cameraAggressiveness = newVal / 20)
                            .build())

                    .addEntry(entryBuilder.startFloatField(CAMERA_SMOOTHING, Config.cameraSmoothing / 0.009f)
                            .setDefaultValue(50)
                            .setTooltip(TIP_CAMERA_SMOOTHING)
                            .setMin(0).setMax(100)
                            .setSaveConsumer(newVal -> Config.cameraSmoothing = newVal * 0.009f)
                            .build());

            builder.setSavingRunnable(() -> Config.save());
            return builder.build();
        };
    }

    public enum SpeedFormat {
        MS,
        KMPH,
        MPH,
        KT
    }

}
