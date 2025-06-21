package io.github.mattisnotdev.thatispristine;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PristineHandler {

    public static final SoundEvent pristineSNDFX = Registry.register(Registries.SOUND_EVENT, Identifier.of("thatispristine", "pristine"), SoundEvent.of(Identifier.of("thatispristine", "pristine")));
    public static final SoundEvent bomboSNDFX = Registry.register(Registries.SOUND_EVENT, Identifier.of("thatispristine", "bombo"), SoundEvent.of(Identifier.of("thatispristine", "bombo")));

    public static void init(){
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            checkThenPlay(message.getString());
        });
    }

    public static void checkThenPlay(String msg){
        if(ThatIsPristineClient.CONFIG.isToggled && msg.contains("PRISTINE!") && !msg.contains("notifications")){
            MinecraftClient client = MinecraftClient.getInstance();
            client.execute(()-> {
               if(client.getSoundManager() != null){
                   client.getSoundManager().play(
                           PositionedSoundInstance.master(
                                   pristineSNDFX,
                                   ThatIsPristineClient.CONFIG.pitch,
                                   ThatIsPristineClient.CONFIG.volume
                           )
                   );
               }
            });
        } else if(msg.contains("From") && msg.contains("MattproGaming") && msg.toLowerCase().contains("bomboclaat")){
            MinecraftClient client = MinecraftClient.getInstance();
            client.execute(()-> {
                if (client.getSoundManager() != null) {
                    client.getSoundManager().play(
                            PositionedSoundInstance.master(
                                    bomboSNDFX,
                                    1.0f,
                                    1.0f
                            )
                    );
                }
            });
        }
    }
}
