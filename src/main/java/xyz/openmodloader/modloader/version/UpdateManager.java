package xyz.openmodloader.modloader.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.ImmutableList;

import xyz.openmodloader.modloader.ModInfo;

public class UpdateManager {
    private static final Map<ModInfo, UpdateContainer> UPDATE_CONTAINERS = new HashMap<>();
    private static final Map<ModInfo, UpdateContainer> OUTDATED_MODS = new HashMap<>();

    public static void registerUpdater(ModInfo mod, UpdateContainer container) {
        UPDATE_CONTAINERS.put(mod, container);
    }

    public static List<UpdateContainer> getOutdatedMods() {
        return ImmutableList.copyOf(OUTDATED_MODS.values());
    }

    public static boolean isModOutdated(ModInfo container) {
        return OUTDATED_MODS.containsKey(container);
    }

    public static boolean hasUpdateContainer(ModInfo container) {
        return UPDATE_CONTAINERS.keySet().contains(container);
    }

    public static UpdateContainer getUpdateContainer(ModInfo container) {
        return UPDATE_CONTAINERS.get(container);
    }

    public static void checkForUpdates() {
        OUTDATED_MODS.clear();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (Map.Entry<ModInfo, UpdateContainer> entry : UPDATE_CONTAINERS.entrySet()) {
            executor.execute(() -> {
                if (!entry.getKey().getVersion().atLeast(entry.getValue().getLatestVersion())) {
                    OUTDATED_MODS.put(entry.getKey(), entry.getValue());
                }
            });
        }
        executor.shutdown();
    }
}
