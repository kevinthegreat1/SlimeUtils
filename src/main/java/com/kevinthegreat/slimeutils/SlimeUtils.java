package com.kevinthegreat.slimeutils;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlimeUtils implements ModInitializer {
	private static final String MOD_ID = "slimeutils";
	private static final String MOD_NAME = "Slime Utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_NAME+ "initialized");
	}
}