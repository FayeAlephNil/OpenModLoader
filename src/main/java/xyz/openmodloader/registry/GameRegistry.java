package xyz.openmodloader.registry;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenMinable;
import xyz.openmodloader.world.generation.WorldGenerator;

/**
 * The anchor point for registering content. Blocks, items, world generators,
 * etc are registered here.
 */
public class GameRegistry {

    /**
     * Initializes the registry.
     */
    public static void init() {
        Registries.register(HorseArmorType.class);
        registerHorseArmor(new ResourceLocation("none"), HorseArmorType.NONE);
        registerHorseArmor(new ResourceLocation("iron"), HorseArmorType.IRON);
        registerHorseArmor(new ResourceLocation("gold"), HorseArmorType.GOLD);
        registerHorseArmor(new ResourceLocation("diamond"), HorseArmorType.DIAMOND);
        Registries.register(WorldGenerator.class);
    }

    /**
     * Registers a horse armor.
     *
     * @param id the ID of the horse armor. Must be unique.
     * @param type the horse armor type
     */
    public static void registerHorseArmor(ResourceLocation id, HorseArmorType type) {
        AutomaticNamespacedRegistry<ResourceLocation, HorseArmorType> registry = Registries.get(HorseArmorType.class);
        if (type == null) {
            throw new NullPointerException("Attempted to register a null horse armor");
        } else if (id == null) {
            throw new NullPointerException("Attempted to register a horse armor with a null ID");
        } else if (registry.containsKey(id)) {
            throw new IllegalArgumentException(String.format("The horse armor ID \"%s\" has already been registered", id));
        }
        registry.register(id, type);
    }

    /**
     * Registers a block with a block item.
     *
     * @param id the ID of the block. Must be unique.
     * @param block the block
     */
    public static void registerBlock(ResourceLocation id, Block block) {
        registerBlock(id, block, new ItemBlock(block));
    }

    /**
     * Registers a block.
     *
     * @param id the ID of the block. Must be unique.
     * @param block the block
     * @param item the block item.
     *             null if the block does not have an item.
     */
    public static void registerBlock(ResourceLocation id, Block block, ItemBlock item) {
        AutomaticNamespacedRegistry<ResourceLocation, Block> blockRegistry = Registries.get(Block.class);
        AutomaticNamespacedRegistry<ResourceLocation, Item> itemRegistry = Registries.get(Item.class);
        if (block == null) {
            throw new NullPointerException("Attempted to register a null block");
        } else if (id == null) {
            throw new NullPointerException("Attempted to register a block with a null ID");
        } else if (blockRegistry.containsKey(id)) {
            throw new IllegalArgumentException(String.format("The block ID \"%s\" has already been registered", id));
        } else if (itemRegistry.containsKey(id)) {
            throw new IllegalArgumentException(String.format("The item ID \"%s\" has already been registered", id));
        }
        blockRegistry.register(id, block);
        int integerID = blockRegistry.getIDForObject(block);
        for (IBlockState state : block.getBlockState().getValidStates()) {
            Block.BLOCK_STATE_IDS.put(state, integerID << 4 | block.getMetaFromState(state));
        }
        if (item != null) {
            Registries.get(Item.class).register(integerID, id, item);
            Registries.getBlockItemMap().put(block, item);
        }
    }

    /**
     * Registers an item.
     *
     * @param id the ID of the item. Must be unique.
     * @param item the item
     */
    public static void registerItem(ResourceLocation id, Item item) {
        AutomaticNamespacedRegistry<ResourceLocation, Item> registry = Registries.get(Item.class);
        if (item == null) {
            throw new NullPointerException("Attempted to register a null item");
        } else if (id == null) {
            throw new NullPointerException("Attempted to register an item with a null ID");
        } else if (registry.containsKey(id)) {
            throw new IllegalArgumentException(String.format("The item ID %s has already been registered", id));
        }
        registry.register(id, item);
    }

    /**
     * Registers a world generator.
     *
     * @param id the ID of the generator. Must be unique.
     * @param generator the generator
     */
    public static void registerWorldGen(ResourceLocation id, WorldGenerator generator) {
        AutomaticNamespacedRegistry<ResourceLocation, WorldGenerator> registry = Registries.get(WorldGenerator.class);
        if (generator == null) {
            throw new NullPointerException("Attempted to register a null world generator");
        } else if (id == null) {
            throw new NullPointerException("Attempted to register a world generator with a null ID");
        } else if (registry.containsKey(id)) {
            throw new IllegalArgumentException(String.format("The world generator ID \"%s\" has already been registered", id));
        }
        registry.register(id, generator);
    }

    /**
     * Registers an ore generator to the world generator registry.
     *
     * @param id the generator ID. Must be unique.
     * @param ore the ore
     * @param replaceables the blocks the ore may spawn in, usually stone for
     *        the overworld and netherrack for the nether
     * @param veinSize the vein size
     * @param minY the minimum Y level the ore may spawn at
     * @param maxY the maximum Y level the ore may spawn at
     * @param dimensions the dimensions the ore may spawn in. May be
     *        {@link Short#MAX_VALUE} as a wildcard.
     * @param attempts the number of attempts at spawning the ore per chunk
     */
    public static void registerOreGen(ResourceLocation id, IBlockState ore, IBlockState[] replaceables, int veinSize, int minY, int maxY, int[] dimensions, int attempts) {
        List<IBlockState> replaceableList = Arrays.asList(replaceables);
        WorldGenMinable generator = new WorldGenMinable(ore, veinSize, replaceableList::contains);
        registerWorldGen(id, (biome, world, random, chunkPos) -> {
            for (int dimension : dimensions) {
                if (dimension != world.provider.getDimensionType().getId() && dimension != Short.MAX_VALUE) {
                    continue;
                }
                for (int attempt = 0; attempt < attempts; attempt++) {
                    int xOffset = world.rand.nextInt(16);
                    int yOffset = world.rand.nextInt(maxY - minY + 1) + minY;
                    int zOffset = world.rand.nextInt(16);
                    BlockPos pos = chunkPos.add(xOffset, yOffset, zOffset);
                    generator.generate(world, world.rand, pos);
                }
                return;
            }
        });
    }

    /**
     * Registers an ore generator to the world generator registry.
     *
     * @param id the generator ID. Must be unique.
     * @param ore the ore
     * @param replaceable the block the ore may spawn in, usually stone for
     *        the overworld and netherrack for the nether
     * @param veinSize the vein size
     * @param minY the minimum Y level the ore may spawn at
     * @param maxY the maximum Y level the ore may spawn at
     * @param dimension the dimension the ore may spawn in. May be
     *        {@link Short#MAX_VALUE} as a wildcard.
     * @param attempts the number of attempts at spawning the ore per chunk
     */
    public static void registerOreGen(ResourceLocation id, IBlockState ore, IBlockState replaceable, int veinSize, int minY, int maxY, int dimension, int attempts) {
        registerOreGen(id, ore, new IBlockState[]{replaceable}, veinSize, minY, maxY, new int[]{dimension}, attempts);
    }
}
