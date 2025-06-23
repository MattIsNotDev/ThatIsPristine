package io.github.mattisnotdev.thatispristine;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Pristine Settings"))
                    .setSavingRunnable(ThatIsPristineClient.CONFIG::saveConfig);
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory category = builder.getOrCreateCategory(Text.literal("category"));

            category.addEntry(entryBuilder.startBooleanToggle(
                            Text.literal("Toggle On"), ThatIsPristineClient.CONFIG.isToggled)
                    .setSaveConsumer(value -> ThatIsPristineClient.CONFIG.isToggled = value)
                    .setDefaultValue(true)
                    .build());

            category.addEntry(entryBuilder.startFloatField(
                            Text.literal("Volume"), ThatIsPristineClient.CONFIG.volume)
                    .setSaveConsumer(value -> ThatIsPristineClient.CONFIG.volume = value)
                    .setDefaultValue(1.0f)
                    .setMin(0.0f)
                    .setMax(2.0f)
                    .build());

            category.addEntry(entryBuilder.startFloatField(
                            Text.literal("Pitch"), ThatIsPristineClient.CONFIG.pitch)
                    .setSaveConsumer(value -> ThatIsPristineClient.CONFIG.pitch = value)
                    .setDefaultValue(1.0f)
                    .setMin(0.0f)
                    .setMax(2.0f)
                    .build());

            return builder.build();
        };
    }
}
