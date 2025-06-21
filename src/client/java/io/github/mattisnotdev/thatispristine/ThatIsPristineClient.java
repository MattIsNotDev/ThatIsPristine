package io.github.mattisnotdev.thatispristine;

import net.fabricmc.api.ClientModInitializer;

public class ThatIsPristineClient implements ClientModInitializer {
	public static ConfigHandler CONFIG;

	@Override
	public void onInitializeClient() {
		PristineHandler.init();

		CONFIG = ConfigHandler.init();
		ConfigCommandHandler.register();
	}
}