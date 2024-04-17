package com.kevinthegreat.slimeutils;

import com.kevinthegreat.gamerulelib.api.v1.SyncedGameRuleRegistry;
import com.kevinthegreat.slimeutils.compatibility.MinihudCompatibility;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class SlimeUtils implements ModInitializer {
    private static final String MOD_ID = "slimeutils";
    private static final String MOD_NAME = "Slime Utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final long SCRAMBLER = 987234911L;
    public static final int BOUND = 2147483640;

    public static final GameRules.Key<DoubleRule> SLIME_CHUNK_PERCENTAGE = SyncedGameRuleRegistry.register("slimeChunkPercentage", GameRules.Category.SPAWNING, GameRuleFactory.createDoubleRule(10, 0, 100, (server, rule) -> MinihudCompatibility.setSlimeChunkOverlayNeedsUpdate()));

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("slime").executes(SlimeUtils::getSlime)));
        LOGGER.info(MOD_NAME + " initialized");
    }

    @SuppressWarnings("SameReturnValue")
    private static int getSlime(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null) {
            source.sendError(Text.literal("You must be a player to use this command"));
            return Command.SINGLE_SUCCESS;
        }
        int chunkX = player.getChunkPos().x;
        int chunkZ = player.getChunkPos().z;
        ServerWorld world = source.getWorld();
        if (isSlimeChunk(chunkX, chunkZ, world.getSeed(), world)) {
            source.sendFeedback(() -> Text.translatable("slimeutils.slimeChunk", chunkX, chunkZ).formatted(Formatting.GREEN), false);
        } else {
            source.sendFeedback(() -> Text.translatable("slimeutils.notSlimeChunk", chunkX, chunkZ), false);
        }
        return Command.SINGLE_SUCCESS;
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
