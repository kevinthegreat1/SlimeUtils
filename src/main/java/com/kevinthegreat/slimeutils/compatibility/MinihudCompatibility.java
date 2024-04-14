package com.kevinthegreat.slimeutils.compatibility;

import fi.dy.masa.minihud.renderer.OverlayRendererSlimeChunks;
import net.fabricmc.loader.api.FabricLoader;

public class MinihudCompatibility {
    public static void setSlimeChunkOverlayNeedsUpdate() {
        if (FabricLoader.getInstance().isModLoaded("minihud")) {
            OverlayRendererSlimeChunks.setNeedsUpdate();
        }
    }
}
