package xyz.openmodloader.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Registries {

    private static final Map<Block, Item> BLOCK_ITEM_MAP = new HashMap<>();
    private static final Map<String, AutomaticNamespacedRegistry<Object, Object>> REGISTRY_MAP = new HashMap<>();
    private static final Function<String, ResourceLocation> DEFAULT_KEY_FACTORY = ResourceLocation::new;

    public static <K, V> AutomaticNamespacedRegistry<K, V> get(Class<V> clazz) {
        return (AutomaticNamespacedRegistry<K, V>) REGISTRY_MAP.get(clazz.getName());
    }

    public static <V> AutomaticNamespacedRegistry<ResourceLocation, V> register(Class<V> clazz) {
        return register(clazz, DEFAULT_KEY_FACTORY);
    }

    public static <K, V> AutomaticNamespacedRegistry<K, V> register(Class<V> clazz, Function<String, K> keyFactory) {
        return register(clazz, new AutomaticNamespacedRegistry<>(keyFactory));
    }

    public static <V> AutomaticDefaultedNamespacedRegistry<ResourceLocation, V> register(Class<V> clazz, ResourceLocation defaultKey) {
        return register(clazz, defaultKey, DEFAULT_KEY_FACTORY);
    }

    public static <K, V> AutomaticDefaultedNamespacedRegistry<K, V> register(Class<V> clazz, K defaultKey, Function<String, K> keyFactory) {
        return register(clazz, new AutomaticDefaultedNamespacedRegistry<>(keyFactory, defaultKey));
    }

    public static <K, V, T extends AutomaticNamespacedRegistry<K, V>> T register(Class<V> clazz, T registry) {
        REGISTRY_MAP.put(clazz.getName(), (AutomaticNamespacedRegistry<Object, Object>) registry);
        return registry;
    }

    public static Map<Block, Item> getBlockItemMap() {
        return BLOCK_ITEM_MAP;
    }

    public static Map<String, AutomaticNamespacedRegistry<Object, Object>> getRegistryMap() {
        return REGISTRY_MAP;
    }
}
