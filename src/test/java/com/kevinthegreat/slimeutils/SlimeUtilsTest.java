package com.kevinthegreat.slimeutils;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import org.junit.jupiter.api.*;

import java.util.function.Supplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SlimeUtilsTest {
    private static final Supplier<Random> RANDOM_SUPPLIER = () -> Random.create(2358574256742911494L);
    private static final int CASES = 100000;
    private LongSet seeds;

    @Order(10)
    @Test
    void testIsSlimeChunkNegative1Percent() {
        testIsSlimeChunk(-1, 0);
    }

    @Order(0)
    @Test
    void testIsSlimeChunk0Percent() {
        testIsSlimeChunk(0, 0);
    }

    @Order(-1)
    @Test
    void testIsSlimeChunk0Point1Percent() {
        testIsSlimeChunk(0.1, 98);
    }

    @Order(-10)
    @Test
    void testIsSlimeChunk1Percent() {
        testIsSlimeChunk(1, 944);
    }

    @Order(-20)
    @Test
    void testIsSlimeChunk2Percent() {
        testIsSlimeChunk(2, 1908);
    }

    @Order(-90)
    @Test
    void testIsSlimeChunk9PercentDefault() {
        testIsSlimeChunk(9, 8994);
    }

    @Order(-100)
    @Test
    void testIsSlimeChunk10PercentDefault() {
        testIsSlimeChunk(10, 9992);
    }

    @Order(-110)
    @Test
    void testIsSlimeChunk11PercentDefault() {
        testIsSlimeChunk(11, 11046);
    }

    @Order(-200)
    @Test
    void testIsSlimeChunk20Percent() {
        testIsSlimeChunk(20, 20094);
    }

    @Order(-500)
    @Test
    void testIsSlimeChunk50Percent() {
        testIsSlimeChunk(50, 49987);
    }

    @Order(-990)
    @Test
    void testIsSlimeChunk99Percent() {
        testIsSlimeChunk(99, 98998);
    }

    @Order(-999)
    @Test
    void testIsSlimeChunk99Point9Percent() {
        testIsSlimeChunk(99.9, 99896);
    }

    @Order(-1000)
    @Test
    void testIsSlimeChunk100Percent() {
        testIsSlimeChunk(100, 100000);
    }

    @Order(-1001)
    @Test
    void testIsSlimeChunk100Point1Percent() {
        testIsSlimeChunk(100.1, 100000);
    }

    @Order(-2000)
    @Test
    void testIsSlimeChunk200Percent() {
        testIsSlimeChunk(200, 100000);
    }

    private void testIsSlimeChunk(double percent, int expectedCount) {
        Random random = RANDOM_SUPPLIER.get();
        LongSet newSeeds = new LongOpenHashSet();
        for (int i = 0; i < CASES; i++) {
            long seed = random.nextLong();
            boolean expected = ChunkRandom.getSlimeRandom(0, 0, seed, SlimeUtils.SCRAMBLER).nextInt(10) == 0;
            boolean actual = SlimeUtils.isSlimeChunk(0, 0, seed, percent);
            if (percent < 10) {
                if (actual) {
                    newSeeds.add(seed);
                    Assertions.assertTrue(expected);
                    Assertions.assertTrue(expectedCount == CASES || seeds.contains(seed));
                }
            } else if (percent > 10) {
                if (expected) {
                    Assertions.assertTrue(actual);
                }
                if (actual) {
                    newSeeds.add(seed);
                    Assertions.assertTrue(expectedCount == CASES || seeds.contains(seed));
                }
            } else {
                Assertions.assertEquals(expected, actual);
                if (actual) {
                    newSeeds.add(seed);
                    Assertions.assertTrue(expectedCount == CASES || seeds.contains(seed));
                }
            }
        }
        seeds = newSeeds;
        Assertions.assertEquals(expectedCount, seeds.size());
    }
}
