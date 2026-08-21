package com.infinix.visual;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinixVisual implements ModInitializer {
    public static final String MOD_ID = "infinix-visual";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Infinix Visual: инициализация (common)");
    }
}
