package com.kevinthegreat.slimeutils.mixin.client.minihud;

import com.kevinthegreat.slimeutils.SlimeUtils;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.minihud.event.RenderHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderHandler.class, remap = false)
public class RenderHandlerMixin {
    @Redirect(method = "addLine(Lfi/dy/masa/minihud/config/InfoToggle;)V", at = @At(value = "INVOKE", target = "Lfi/dy/masa/minihud/util/MiscUtils;canSlimeSpawnAt(IIJ)Z"))
    private boolean slimeutils$modifyCanSpawn(int x, int z, long seed, @Local World world) {
        return SlimeUtils.isSlimeChunk(x >> 4, z >> 4, seed, world);
    }
}
