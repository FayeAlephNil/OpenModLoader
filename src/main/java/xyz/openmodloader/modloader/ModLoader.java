package xyz.openmodloader.modloader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.SidedHandler;
import xyz.openmodloader.launcher.OMLTweaker;
import xyz.openmodloader.launcher.strippable.Side;
import xyz.openmodloader.modloader.version.JsonUpdateContainer;
import xyz.openmodloader.modloader.version.UpdateManager;

/**
 * The class responsible for registering and loading mods. Loads mods from the
 * mods folder and the classpath. Mods are defined by the MANIFEST.MF file of
 * the mod jar. See {@link ManifestModInfo} for more info.
 */
public class ModLoader {

    /**
     * A list all detected mod info.
     */
    private static final List<ModInfo> MODS = new ArrayList<>();

    /**
     * A map of all loaded mods. Key is the mod class and value is the ModInfo.
     */
    private static final Map<Mod, ModInfo> MODS_MAP = new HashMap<>();

    /**
     * A map of all loaded mods. Key is the mod id and value is the ModInfo.
     */
    private static final Map<String, ModInfo> ID_MAP = new HashMap<>();

    /**
     * The running directory for the game.
     */
    private static final File RUN_DIRECTORY = new File(".");

    /**
     * The directory to load mods from.
     */
    private static final File MOD_DIRECTORY = new File(RUN_DIRECTORY, "mods");

    /**
     * Attempts to detect mods from the specified mods directory and the class
     * path. This should only be used internally! Called from
     * {@link OMLTweaker#injectIntoClassLoader(LaunchClassLoader)}}.
     */
    public static void registerMods() throws Exception {
        LoadHandler load = new LoadHandler(MOD_DIRECTORY, true, OpenModLoader.getLogger());
        // if everything is fine, register the mods
        if (load.load()) {
            MODS.addAll(load.getModList());
            ID_MAP.putAll(load.getIdMap());
        }
    }

    /**
     * Iterates through all registered mods and loads them. If the mod can not
     * be loaded it will be disabled. Called from
     * {@link OpenModLoader#minecraftConstruction(SidedHandler)}.
     */
    public static void loadMods() {
        // load the instances
        for (ModInfo mod : MODS) {
            // if this is the wrong side, skip the mod
            if (mod.getSide() != Side.UNIVERSAL && mod.getSide() != OpenModLoader.getSidedHandler().getSide()) {
                continue;
            }
            Mod instance = mod.getInstance();
            if (instance != null) {
                MODS_MAP.put(instance, mod);
                // populate @Instance fields
                for (Field field : instance.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Instance.class)) {
                        try {
                            field.setAccessible(true);
                            if (Modifier.isFinal(field.getModifiers())) {
                                Field modifiersField = Field.class.getDeclaredField("modifiers");
                                modifiersField.setAccessible(true);
                                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                            }
                            field.set(null, instance);
                        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                            OpenModLoader.getLogger().error("Could not set @Instance field", e);
                        }
                    }
                }
            }
        }
        // initialize the mods and start update checker
        for (ModInfo mod : MODS) {
            // if this is the wrong side, skip the mod
            if (mod.getSide() != Side.UNIVERSAL && mod.getSide() != OpenModLoader.getSidedHandler().getSide()) {
                continue;
            }
            Mod instance = mod.getInstance();
            if (instance != null) {
                instance.onInitialize();
            }
            // run the update checker
            if (!UpdateManager.hasUpdateContainer(mod) && mod.getUpdateURL() != null) {
                try {
                    UpdateManager.registerUpdater(mod, new JsonUpdateContainer(new URL(mod.getUpdateURL())));
                } catch (MalformedURLException e) {
                    OpenModLoader.getLogger().catching(e);
                }
            }
        }
    }

    /**
     * Returns an immutable and sorted list of mods.
     *
     * @return an immutable and sorted list of mods
     */
    public static List<ModInfo> getModList() {
        return MODS;
    }

    /**
     * Gets a map of mods and their mod ID. This map is not immutable, but
     * should not be edited externally!
     * 
     * @return A map of mods and their mod ID.
     */
    public static Map<String, ModInfo> getIndexedModList() {
        return ID_MAP;
    }

    /**
     * Gets a map of mods and their info. This map is not immutable, but should
     * not be edited externally!
     * 
     * @return A map of mods and their info.
     */
    public static Map<Mod, ModInfo> getModMap() {
        return MODS_MAP;
    }

    /**
     * Gets the {@link ModInfo} for a mod. This can be null if the mod does not
     * have info.
     * 
     * @param mod The mod to get info for.
     * @return The ModInfo for the mod.
     */
    public static ModInfo getModInfo(Mod mod) {
        return MODS_MAP.get(mod);
    }

    /**
     * Gets the {@link ModInfo} for a mod. This can be null if no mod is found.
     * 
     * @param id The ID of the mod being searched for.
     * @return The ModInfo for the specified ID.
     */
    public static ModInfo getModInfo(String id) {
        return ID_MAP.get(id);
    }

    /**
     * Checks if a mod is loaded.
     * 
     * @param id The ID of the mod being searched for.
     * @return Whether or not a mod with the specified ID is loaded.
     */
    public static boolean isModLoaded(String id) {
        return ID_MAP.containsKey(id);
    }

    /**
     * Adds a new mod info to the loader. This will not load a new mod, but
     * allows new mod list entries to be added.
     * 
     * @param info The mod info to add.
     */
    public static void addModInfo(ModInfo info) {
        MODS.add(info);
        ID_MAP.put(info.getModID(), info);
    }
}
