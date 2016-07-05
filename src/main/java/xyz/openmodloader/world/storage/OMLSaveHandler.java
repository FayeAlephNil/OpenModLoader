package xyz.openmodloader.world.storage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import xyz.openmodloader.registry.AutomaticNamespacedRegistry;
import xyz.openmodloader.registry.Registries;

/**
 * Handles saving and loading OML data (items, blocks, etc.)
 */
public class OMLSaveHandler {

    /**
     * Saves registered IDs to the world. This allows registry objects to have
     * the same integer IDs when the world is reloaded.
     *
     * @param nbt the world data NBT
     */
    public static void save(NBTTagCompound nbt) {
//        NBTTagList registryList = new NBTTagList();
//
//        Registries.getRegistryMap().forEach((registryType, registry) -> {
//            NBTTagCompound registryNBT = new NBTTagCompound();
//
//            registryNBT.setString("type", registryType);
//
//            NBTTagList registeredObjectList = new NBTTagList();
//            for (Object key : registry.getKeys()) {
//                 NBTTagCompound registeredObject = new NBTTagCompound();
//                registeredObject.setString("key", key.toString());
//                registeredObject.setInteger("value", registry.getIDForObject(registry.getObject(key)));
//                registeredObjectList.appendTag(registeredObject);
//            }
//            registryNBT.setTag("Data", registeredObjectList);
//
//            registryList.appendTag(registryNBT);
//        });
//
//        nbt.setTag("Registry", registryList);
    }

    /**
     * Loads registered IDs from the world. This allows registry objects to have
     * the same integer IDs when the world is reloaded.
     *
     * @param nbt the world data NBT
     */
    public static void load(NBTTagCompound nbt) {
//        NBTTagList registryList = nbt.getTagList("Registry", 10);
//
//        for (int i = 0; i < registryList.tagCount(); i++) {
//            NBTTagCompound registryNBT = registryList.getCompoundTagAt(i);
//
//            AutomaticNamespacedRegistry<Object, Object> registry = Registries.getRegistryMap().get(registryNBT.getString("type"));
//            if (registry == null) {
//                continue;
//            }
//
//            NBTTagList registeredObjectList = registryNBT.getTagList("Data", 10);
//            Map<Object, Integer> keyIdMap = new HashMap<>();
//            for (int j = 0; j < registeredObjectList.tagCount(); j++) {
//                NBTTagCompound registeredObject = registeredObjectList.getCompoundTagAt(j);
//                keyIdMap.put(registry.createKey(registeredObject.getString("key")), registeredObject.getInteger("value"));
//            }
//            registry.setRegistryObjects(keyIdMap);
//        }
    }

    /**
     * Writes modded blocks to the chunk NBT.
     *
     * @param chunk the chunk
     * @param nbt the chunk NBT
     */
    public static void writeChunkToNBT(Chunk chunk, NBTTagCompound nbt) {
//        NBTTagList blockStorageList = new NBTTagList();
//
//        for (ExtendedBlockStorage blockStorage : chunk.getBlockStorageArray()) {
//            if (blockStorage == Chunk.NULL_BLOCK_STORAGE) {
//                continue;
//            }
//
//            NBTTagCompound blockStorageNBT = new NBTTagCompound();
//            blockStorageNBT.setByte("Y", (byte) (blockStorage.getYLocation() >> 4 & 255));
//
//            int[] ids = new int[4096];
//            byte[] states = new byte[4096];
//            for (int i = 0; i < 4096; ++i) {
//                int x = i & 15;
//                int y = i >> 8 & 15;
//                int z = i >> 4 & 15;
//                IBlockState state = blockStorage.get(x, y, z);
//                int id = Block.BLOCK_STATE_IDS.get(state);
//                ResourceLocation name = Block.REGISTRY.getNameForObject(state.getBlock());
//                if (!name.getResourceDomain().equals("minecraft")) {
//                    ids[i] = id >> 4;
//                    states[i] = (byte) (id & 15);
//                } else {
//                    ids[i] = -1;
//                    states[i] = -1;
//                }
//            }
//            blockStorageNBT.setIntArray("Blocks", ids);
//            blockStorageNBT.setByteArray("Data", states);
//
//            blockStorageList.appendTag(blockStorageNBT);
//        }
//
//        nbt.setTag("OMLBlocks", blockStorageList);
    }

    /**
     * Reads modded blocks from the chunk NBT.
     *
     * @param chunk the chunk
     * @param nbt the chunk NBT
     */
    public static void readChunkFromNBT(Chunk chunk, NBTTagCompound nbt) {
//        IBlockState defaultState = Block.BLOCK_STATE_IDS.getByValue(0);
//        NBTTagList blockStorageList = nbt.getTagList("OMLBlocks", 10);
//
//        for (int i = 0; i < blockStorageList.tagCount(); ++i) {
//            NBTTagCompound blockStorageNBT = blockStorageList.getCompoundTagAt(i);
//            int blockStorageY = blockStorageNBT.getByte("Y") << 4;
//
//            ExtendedBlockStorage blockStorage = null;
//            for (ExtendedBlockStorage s : chunk.getBlockStorageArray()) {
//                if (s.getYLocation() == blockStorageY) {
//                    blockStorage = s;
//                    break;
//                }
//            }
//            if (blockStorage == null) {gu
//                throw new RuntimeException("Chunk Loading Failed");
//            }
//
//            int[] ids = blockStorageNBT.getIntArray("Blocks");
//            byte[] states = blockStorageNBT.getByteArray("Data");
//            for (int j = 0; j < 4096; ++j) {
//                int x = j & 15;
//                int y = j >> 8 & 15;
//                int z = j >> 4 & 15;
//                if (states[j] != -1) {
//                    int id = (ids[j] << 4) + states[j];
//                    IBlockState state = Block.BLOCK_STATE_IDS.getByValue(id);
//                    blockStorage.set(x, y, z, state != null ? state : defaultState);
//                }
//            }
//
//            blockStorage.removeInvalidBlocks();
//        }
    }
}
