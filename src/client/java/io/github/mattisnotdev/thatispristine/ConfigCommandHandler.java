package io.github.mattisnotdev.thatispristine;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class ConfigCommandHandler {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(( dispatcher, registryAccess) -> {
            dispatcher.register(literal("pristine")
                    .executes(ctx-> {
                        MinecraftClient client = MinecraftClient.getInstance();
                        client.send(() -> {
                            ConfigScreenFactory<?> configScreenFactory = new ModMenuIntegration().getModConfigScreenFactory();
                            client.setScreen(configScreenFactory.create(client.currentScreen));
                        });
                        return 1;
                    }));
        });
    }
}
