package com.kevinthegreat.slimeutils;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.random.ChunkRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlimeUtils implements ModInitializer {
    private static final String MOD_ID = "slimeutils";
    private static final String MOD_NAME = "Slime Utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    static final long SCRAMBLER = 987234911L;
    static final int BOUND = 2147483640;

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_NAME + "initialized");
    }

    /**
     * Check if a chunk is a slime chunk with a given percent chance deterministically, using an algorithm compatible with vanilla.
     *
     * <p>Compatible means that if given a chance of 10 percent, the result will be the same as vanilla.
     * If given a smaller than 10 percent chance, only a subset of vanilla slime chunks will be slime chunks, and the given percent of all chunks will be slime chunks.
     * If given a greater than 10 percent chance, all vanilla slime chunks will be slime chunks, and the given percent of all chunks will be slime chunks.
     */
    public static boolean isSlimeChunk(int chunkX, int chunkZ, long seed, int percent) {
        int randomInt = ChunkRandom.getSlimeRandom(chunkX, chunkZ, seed, SCRAMBLER).nextInt(BOUND);
        return randomInt % 10 < (percent - 1) / 10 || randomInt % 10 == (percent - 1) / 10 && randomInt < ((percent - 1) % 10 + 1) * (BOUND / 10);
    }
}
