package com.kevinthegreat.slimeutils.mixin;

import com.kevinthegreat.slimeutils.SlimeUtils;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(SlimeEntity.class)
public class SlimeEntityMixin {
    @Redirect(method = "canSpawn", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/ChunkRandom;getSlimeRandom(IIJJ)Lnet/minecraft/util/math/random/Random;")), at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0))
    private static int slimeutils$modifyCanSpawn(Random random, int bound) {
        return SlimeUtils.isSlimeChunk(random, 10) ? 0 : 1;
    }
}
