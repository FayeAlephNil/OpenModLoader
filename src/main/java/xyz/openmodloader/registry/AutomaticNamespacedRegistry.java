package xyz.openmodloader.registry;

import net.minecraft.util.registry.RegistryNamespaced;
import xyz.openmodloader.util.TriConsumer;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
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
     * Registers a value with a key and an automatically assigned integer ID.
     *
     * @param key the key
     * @param value the value
     */
    public void register(K key, V value) {
        register(availableIds.nextClearBit(0), key, value);
    }

    @Override
    public void register(int id, K key, V value) {
        availableIds.set(id);
        super.register(id, key, value);
    }

    /**
     * Performs the given action for each object in this registry.
     *
     * @param action the action to be performed for each object
     * @throws NullPointerException if the specified action is null
     */
    public void forEach(BiConsumer<K, V> action) {
        registryObjects.forEach(action);
    }

    /**
     * Performs the given action for each object in this registry.
     *
     * @param action the action to be performed for each object
     * @throws NullPointerException if the specified action is null
     */
    public void forEach(TriConsumer<Integer, K, V> action) {
        Objects.requireNonNull(action);
        forEach((key, value) -> action.accept(getIDForObject(value), key, value));
    }

    /**
     * Loads a map of key strings and integer IDs, which is used to "claim"
     * IDs. This allows persistent IDs for registry objects.
     *
     * @param nameIdMap the map of key strings to integer IDs
     */
    public void setRegistryObjects(Map<String, Integer> nameIdMap) {
        Map<K, V> unsorted = new HashMap<>();
        unsorted.putAll(registryObjects);
        registryObjects.clear();
        underlyingIntegerMap.clear();
        availableIds.clear();
        nameIdMap.forEach((name, id) -> {
            K key = keyFactory.apply(name);
            V value = unsorted.get(key);
            if (value != null) {
                register(id, key, value);
                unsorted.remove(key);
            }
        });
        unsorted.forEach(this::register);
    }
}
