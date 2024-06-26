package com.kevinthegreat.slimeutils.mixin.slime;

import com.kevinthegreat.slimeutils.SlimeUtils;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import slime.slime.Slime;

@Mixin(value = Slime.class, remap = false)
public class SlimeMixin {
    @Redirect(method = "lambda$onInitialize$2",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/ChunkRandom;getSlimeRandom(IIJJ)Lnet/minecraft/util/math/random/Random;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I")
    )
    private static int slimeutils$modifyCanSpawn(Random random, int bound, @Local(argsOnly = true) CommandContext<ServerCommandSource> context) {
        return SlimeUtils.isSlimeChunk(random, context.getSource().getWorld()) ? 0 : 1;
    }
}
