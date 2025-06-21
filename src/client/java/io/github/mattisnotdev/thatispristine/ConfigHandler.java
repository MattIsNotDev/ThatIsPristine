package io.github.mattisnotdev.thatispristine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class ConfigHandler {
    // ---   Default Values   --- \\
    boolean isToggled = true;
    float volume = 1.0f;
    float pitch = 1.0f;
    //  -------------------------  \\

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("ThatIsPristine_config.json");

    public void saveConfig(){
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public static ConfigHandler init() {
        File configFile = CONFIG_PATH.toFile();

        try {
            if (configFile.createNewFile()) {
                // New file created - save defaults
                ConfigHandler newConfig = new ConfigHandler();
                newConfig.saveConfig();
                return newConfig;
            }

            try (FileReader reader = new FileReader(configFile)) {
                return GSON.fromJson(reader, ConfigHandler.class);
            }
        } catch (Exception e) {
            System.err.println("Failed to load config: " + e.getMessage());
            return new ConfigHandler(); // Return defaults on error
        }
    }
}
