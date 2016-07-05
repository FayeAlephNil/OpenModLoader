package xyz.openmodloader.registry;

import net.minecraft.util.registry.RegistryNamespaced;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Maps values with a key and an integer ID. If no ID is specified, the
 * value is automatically assigned an available ID.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class AutomaticNamespacedRegistry<K, V> extends RegistryNamespaced<K, V> {

    private final BitSet availableIds = new BitSet(Short.MAX_VALUE - 1);
    private final Function<String, K> keyFactory;

    /**
     * Creates a new registry.
     *
     * @param keyFactory a key factory that creates keys from strings
     */
    public AutomaticNamespacedRegistry(Function<String, K> keyFactory) {
        this.keyFactory = keyFactory;
    }

    /**
     * Creates a key from a string. The key is used to load registry objects
     * from the world.
     *
     * @param s the string
     * @return the key
     */
    public K createKey(String s) {
        return keyFactory.apply(s);
    }

    /**
     * Registers a value with a key and an automatically assigned integer ID.
     *
     * @param key the key
     * @param value the value
     */
    public void register(K key, V value) {
        register(-1, key, value);
    }

    @Override
    public void register(int id, K key, V object) {
        if (id < 0 || availableIds.get(id)) {
            id = availableIds.nextClearBit(0);
        }
        availableIds.set(id);
        super.register(id, key, object);
    }

    /**
     * Loads a map of keys and integer IDs, which is used to "claim" IDs. This
     * allows persistent IDs for registry objects.
     *
     * @param keyIdMap the map of keys to integer IDs
     */
    public void setRegistryObjects(Map<K, Integer> keyIdMap) {
        Map<K, V> unsorted = new HashMap<>();
        registryObjects.clear();
        underlyingIntegerMap.clear();
        keyIdMap.forEach((key, id) -> {
            V value = unsorted.get(key);
            if (value != null) {
                register(id, key, value);
                unsorted.remove(key);
            }
        });
        unsorted.forEach(this::register);
    }
}
