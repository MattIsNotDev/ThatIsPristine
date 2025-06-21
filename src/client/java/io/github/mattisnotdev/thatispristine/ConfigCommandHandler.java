package io.github.mattisnotdev.thatispristine;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import static net.minecraft.server.command.CommandManager.*;

public class ConfigCommandHandler {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> register = dispatcher.register(literal("pristine")
                    .executes(ctx -> {
                        toggleNotif(ctx.getSource());
                        return 1;
                    })
                    .then(literal("config")
                            .executes(ctx -> {
                                Text Msg = Text.empty()
                                        .append(Text.literal("[Pristine] ").formatted(Formatting.BOLD, Formatting.DARK_PURPLE)).append(Text.literal("Current Config:").formatted(Formatting.RESET));
                                ctx.getSource().sendMessage(Msg);
                                ctx.getSource().sendMessage(Text.literal("▪ Toggled On: ").append(String.valueOf(ThatIsPristineClient.CONFIG.isToggled)).formatted(Formatting.DARK_GRAY));
                                ctx.getSource().sendMessage(Text.literal("▪ Volume: ").append(String.valueOf(ThatIsPristineClient.CONFIG.volume)).formatted(Formatting.DARK_GRAY));
                                ctx.getSource().sendMessage(Text.literal("▪ Pitch: ").append(String.valueOf(ThatIsPristineClient.CONFIG.pitch)).formatted(Formatting.DARK_GRAY));
                                return 1;
                            })
                    )
                    .then(literal("help")
                            .executes(ctx -> {
                                Text Msg = Text.empty()
                                .append(Text.literal("[Pristine] ").formatted(Formatting.BOLD, Formatting.DARK_PURPLE)).append(Text.literal("/pristine").formatted(Formatting.UNDERLINE)).append(Text.literal(" plus...\n").formatted(Formatting.RESET))
                                        .append(Text.literal("▪ config:").formatted(Formatting.BOLD)).append(Text.literal(" tells you current settings\n").formatted(Formatting.RESET).formatted(Formatting.GRAY))
                                        .append(Text.literal("▪ toggle:").formatted(Formatting.BOLD)).append(Text.literal(" toggles the notification (so does just '/pristine')\n").formatted(Formatting.RESET).formatted(Formatting.GRAY))
                                        .append(Text.literal("▪ volume {1-100}:").formatted(Formatting.BOLD)).append(Text.literal(" sets volume to ___ percent\n").formatted(Formatting.RESET).formatted(Formatting.GRAY))
                                        .append(Text.literal("▪ pitch {1-200}:").formatted(Formatting.BOLD)).append(Text.literal(" sets pitch to ___ percent").formatted(Formatting.RESET).formatted(Formatting.GRAY));
                                ctx.getSource().sendMessage(Msg);
                                return 1;
                            }))
                    .then(literal("toggle").executes(ctx -> {
                        toggleNotif(ctx.getSource());
                        return 1;
                    }))
                    .then(literal("volume")
                            .then(argument("percentage", IntegerArgumentType.integer(0, 100))
                                    .executes(ctx -> {
                                        ThatIsPristineClient.CONFIG.volume = (IntegerArgumentType.getInteger(ctx,"percentage") / 100.0f);
                                        ThatIsPristineClient.CONFIG.saveConfig();
                                        Text msg = Text.empty()
                                                .append(Text.literal("[Pristine] ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                                                .append(Text.literal("Set volume to " + IntegerArgumentType.getInteger(ctx, "percentage") + "%").formatted(Formatting.RESET).formatted(Formatting.GRAY));
                                        ctx.getSource().sendMessage(msg);
                                        return 1;
                    })))
                    .then(literal("pitch")
                            .then(argument("percentage", IntegerArgumentType.integer(0, 200))
                                    .executes(ctx -> {
                                        ThatIsPristineClient.CONFIG.pitch = (IntegerArgumentType.getInteger(ctx, "percentage") / 100.0f);
                                        ThatIsPristineClient.CONFIG.saveConfig();
                                        Text msg = Text.empty()
                                                .append(Text.literal("[Pristine] ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                                                .append(Text.literal("Set pitch to " + IntegerArgumentType.getInteger(ctx, "percentage") + "%").formatted(Formatting.RESET).formatted(Formatting.GRAY));
                                        ctx.getSource().sendMessage(msg);
                                        return 1;
                                    }))));
        });
    }

    private static void toggleNotif(ServerCommandSource source){
        ThatIsPristineClient.CONFIG.isToggled = !ThatIsPristineClient.CONFIG.isToggled;
        ThatIsPristineClient.CONFIG.saveConfig();
        Text Msg;
        if(ThatIsPristineClient.CONFIG.isToggled){
            Msg = Text.empty()
                    .append(Text.literal("[Pristine]").formatted(Formatting.BOLD, Formatting.DARK_PURPLE))
                    .append(Text.literal(" Notifications are now ").formatted(Formatting.RESET))
                    .append(Text.literal("ON!").formatted(Formatting.BOLD, Formatting.GREEN));
        } else {
            Msg = Text.empty()
                    .append(Text.literal("[Pristine]").formatted(Formatting.BOLD, Formatting.DARK_PURPLE))
                    .append(Text.literal(" Notifications are now ").formatted(Formatting.RESET))
                    .append(Text.literal("OFF!").formatted(Formatting.BOLD, Formatting.RED));
        }
        source.sendMessage(Msg);
    }
}
