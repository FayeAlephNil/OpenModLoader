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
        NBTTagList registryList = new NBTTagList();

        Registries.getRegistryMap().forEach((registryType, registry) -> {
            NBTTagCompound registryNBT = new NBTTagCompound();

            registryNBT.setString("type", registryType);

            NBTTagList registeredObjectList = new NBTTagList();
            registry.forEach((id, key, value) -> {
                NBTTagCompound registeredObject = new NBTTagCompound();
                registeredObject.setString("key", key.toString());
                registeredObject.setInteger("value", id);
                registeredObjectList.appendTag(registeredObject);
            });
            registryNBT.setTag("Data", registeredObjectList);

            registryList.appendTag(registryNBT);
        });

        nbt.setTag("Registry", registryList);
    }

    /**
     * Loads registered IDs from the world. This allows registry objects to have
     * the same integer IDs when the world is reloaded.
     *
     * @param nbt the world data NBT
     */
    public static void load(NBTTagCompound nbt) {
        NBTTagList registryList = nbt.getTagList("Registry", 10);

        for (int i = 0; i < registryList.tagCount(); i++) {
            NBTTagCompound registryNBT = registryList.getCompoundTagAt(i);

            AutomaticNamespacedRegistry<Object, Object> registry = Registries.getRegistryMap().get(registryNBT.getString("type"));
            if (registry == null) {
                continue;
            }

            NBTTagList registeredObjectList = registryNBT.getTagList("Data", 10);
            Map<String, Integer> nameIdMap = new HashMap<>();
            for (int j = 0; j < registeredObjectList.tagCount(); j++) {
                NBTTagCompound registeredObject = registeredObjectList.getCompoundTagAt(j);
                nameIdMap.put(registeredObject.getString("key"), registeredObject.getInteger("value"));
            }
            registry.setRegistryObjects(nameIdMap);
        }
    }
}
