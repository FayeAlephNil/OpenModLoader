package xyz.openmodloader.registry;

import org.apache.commons.lang3.Validate;

import java.util.Random;
import java.util.function.Function;

/**
 * Maps values with a key and an integer ID. If no ID is specified, the
 * value is automatically assigned an available ID.<br/>
 *<br/>
 * If a value does not exist for a key or an ID, the default value is
 * returned.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class AutomaticDefaultedNamespacedRegistry<K, V> extends AutomaticNamespacedRegistry<K, V> {
    private final K defaultValueKey;
    private V defaultValue;

    /**
     * Creates a new defaulted registry.
     *
     * @param keyFactory a key factory that creates keys from strings
     * @param defaultValueKey the key of the default value
     */
    public AutomaticDefaultedNamespacedRegistry(Function<String, K> keyFactory, K defaultValueKey) {
        super(keyFactory);
        this.defaultValueKey = defaultValueKey;
    }

    @Override
    public void register(int id, K key, V value) {
        if (defaultValueKey.equals(key)) {
            defaultValue = value;
        }
        super.register(id, key, value);
    }

    /**
     * Validates the default value.
     *
     * @throws NullPointerException if the default value is null
     */
    public void validateKey() {
        Validate.notNull(defaultValue, "Missing default of DefaultedMappedRegistry: " + defaultValueKey);
    }

    @Override
    public int getIDForObject(V value) {
        int id = super.getIDForObject(value);
        return id == -1 ? super.getIDForObject(defaultValue) : id;
    }

    @Override
    public K getNameForObject(V value) {
        K key = super.getNameForObject(value);
        return key == null ? defaultValueKey : key;
    }

    @Override
    public V getObject(K key) {
        V value = super.getObject(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public V getObjectById(int id) {
        V value = super.getObjectById(id);
        return value == null ? defaultValue : value;
    }

    @Override
    public V getRandomObject(Random random) {
        V value = super.getRandomObject(random);
        return value == null ? defaultValue : value;
    }
}
