package xyz.openmodloader.world.generation;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import xyz.openmodloader.registry.AutomaticNamespacedRegistry;
import xyz.openmodloader.registry.Registries;

/**
 * Helper class for world generation hooks.
 */
public final class WorldGenHooks {

    private static AutomaticNamespacedRegistry<ResourceLocation, WorldGenerator> registry;

    private static AutomaticNamespacedRegistry<ResourceLocation, WorldGenerator> getRegistry() {
        if (registry == null) {
            registry = Registries.get(WorldGenerator.class);
        }
        return registry;
    }

    /**
     * Generates features for a chunk. This should be called from custom
     * {@link BiomeDecorator}s that override the default
     * {@link BiomeDecorator#decorate(World, Random, Biome, BlockPos)} method
     * without calling {@code super.decorate()}.
     *
     * @param biome the biome
     * @param world the world
     * @param random the random
     * @param chunkPos the chunk pos
     */
    public static void runGenerators(Biome biome, World world, Random random, BlockPos chunkPos) {
        for (WorldGenerator generator : getRegistry()) {
            generator.generate(biome, world, random, chunkPos);
        }
    }
}