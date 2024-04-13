package com.kevinthegreat.slimeutils.mixin.client.minihud;

import com.kevinthegreat.slimeutils.SlimeUtils;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.minihud.renderer.OverlayRendererSlimeChunks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = OverlayRendererSlimeChunks.class, remap = false)
public class OverlayRendererSlimeChunksMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lfi/dy/masa/minihud/util/MiscUtils;canSlimeSpawnInChunk(IIJ)Z"))
    private boolean slimeutils$modifyCanSpawn(int chunkX, int chunkZ, long seed, @Local World world) {
        return SlimeUtils.isSlimeChunk(chunkX, chunkZ, seed, world);
    }
}
