package com.kevinthegreat.slimeutils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlimeUtils implements ModInitializer {
    private static final String MOD_ID = "slimeutils";
    private static final String MOD_NAME = "Slime Utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final long SCRAMBLER = 987234911L;
    public static final int BOUND = 2147483640;

    public static final GameRules.Key<DoubleRule> SLIME_CHUNK_PERCENTAGE = GameRuleRegistry.register("slimeChunkPercentage", GameRules.Category.SPAWNING, GameRuleFactory.createDoubleRule(10, 0, 100));

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_NAME + "initialized");
    }

    public static boolean isSlimeChunk(int chunkX, int chunkZ, long seed, World world) {
        return isSlimeChunk(chunkX, chunkZ, seed, world.getGameRules().get(SLIME_CHUNK_PERCENTAGE).get());
    }

    public static boolean isSlimeChunk(int chunkX, int chunkZ, long seed, double percent) {
        return isSlimeChunk(ChunkRandom.getSlimeRandom(chunkX, chunkZ, seed, SCRAMBLER), percent);
    }

    public static boolean isSlimeChunk(Random random, World world) {
        return isSlimeChunk(random, world.getGameRules().get(SLIME_CHUNK_PERCENTAGE).get());
    }

    /**
     * Check if a chunk is a slime chunk with a given percent chance deterministically, using an algorithm compatible with vanilla.
     *
     * <p>Compatible means that if given a chance of 10 percent, the result will be the same as vanilla.
     * If given a smaller than 10 percent chance, only a subset of vanilla slime chunks will be slime chunks, and the given percent of all chunks will be slime chunks.
     * If given a greater than 10 percent chance, all vanilla slime chunks will be slime chunks, and the given percent of all chunks will be slime chunks.
     */
    public static boolean isSlimeChunk(Random random, double percent) {
        int randomInt = random.nextInt(BOUND);
        return randomInt % 10 < (int) percent / 10 || randomInt % 10 < percent / 10 && randomInt < percent % 10 * BOUND / 10;
    }
}
