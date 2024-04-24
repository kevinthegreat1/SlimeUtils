package com.kevinthegreat.slimeutils;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SlimeUtilsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(SlimeUtilsEnglishLangProvider::new);
    }

    private static class SlimeUtilsEnglishLangProvider extends FabricLanguageProvider {
        protected SlimeUtilsEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add("gamerule.slimeChunkPercentage", "Slime chunk percentage");
            translationBuilder.add("slimeutils.slimeChunk", "Chunk %d, %d is a slime chunk");
            translationBuilder.add("slimeutils.notSlimeChunk", "Chunk %d, %d is not a slime chunk");
        }
    }
}
