package com.kevinthegreat.slimeutils.mixin;

import com.kevinthegreat.slimeutils.SlimeUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(SlimeEntity.class)
public class SlimeEntityMixin {
    @Redirect(method = "canSpawn",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/ChunkRandom;getSlimeRandom(IIJJ)Lnet/minecraft/util/math/random/Random;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0)
    )
    private static int slimeutils$modifyCanSpawn(Random random, int bound, @Local(argsOnly = true) WorldAccess world) {
        return SlimeUtils.isSlimeChunk(random, ((World) world)) ? 0 : 1;
    }
}
