package xyz.openmodloader.modloader;

import java.io.File;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.ResourceLocation;
import xyz.openmodloader.launcher.strippable.Side;
import xyz.openmodloader.modloader.version.UpdateManager;
import xyz.openmodloader.modloader.version.Version;

/**
 * Defines the basic properties that can be known about a mod.
 */
public interface ModInfo {

    /**
     * Gets the texture to use for the mods logo.
     * 
     * @return The texture to use for the mods logo.
     */
    ResourceLocation getLogoTexture();

    /**
     * Gets an instance of the {@link MOD} that is being represented. This can
     * be null!
     * 
     * @return Instance of the mod being represented.
     */
    Mod getInstance();

    /**
     * Gets the version of the mod.
     * 
     * @return The version of the mod.
     */
    Version getVersion();

    /**
     * Gets the version of Minecraft that the mod expects. Mods may not run on
     * unfamiliar versions.
     * 
     * @return The expected version of Minecraft.
     */
    Version getMinecraftVersion();

    /**
     * Gets the identifier for the mod. A valid mod ID is a unique string that
     * can contain a-z, 0-9, _ and - characters. It is critical that this return
     * a constant value!
     * 
     * @return The mods identifier.
     */
    String getModID();

    /**
     * Gets the name of the mod. This can not be an empty or null string!
     * 
     * @return The name of the mod.
     */
    String getName();

    /**
     * Gets a short description of the mod.
     * 
     * @return A description of the mod.
     */
    String getDescription();

    /**
     * The expected environment for the mod. If a mod is not in its expected
     * environment it will not load. For example, a client side mod will not be
     * loaded in a server environment.
     * 
     * @return The expected side environment.
     */
    Side getSide();

    /**
     * Gets the author of the mod. If multiple authors are involved they can be
     * seperated using a comma.
     * 
     * @return The author(s) of the mod.
     */
    String getAuthor();

    /**
     * Gets the home URL of the mod. This must be either null or a valid URL.
     * This is not actually used for anything, however users will be able to
     * access the site from the mod list.
     * 
     * @return The URL of the mod.
     */
    String getURL();

    /**
     * Gets the update URL of the mod. This is used by the built in update
     * checker to read update information. This can be null if you wish to opt
     * out. Further specifications at {@link UpdateManager#registerUpdater()}.
     * 
     * @return The update URL for the mod.
     */
    String getUpdateURL();

    /**
     * Gets the file that the mod was loaded from. This can be null!
     * 
     * @return The mod file.
     */
    File getModFile();

    /**
     * Gets an array of transformers for the mod. Each string represents a full
     * name of a class that implements {@link IClassTransformer}.
     * 
     * @return An array of transformer class names.
     */
    String[] getTransformers();

    /**
     * Gets an array of class names to prevent transforming. Classes in this
     * array can not be edited.
     * 
     * @return An array of classes to prevent transforming.
     */
    String[] getTransformerExclusions();

    /**
     * Gets an array of dependencies. Each string should contain the ID of the
     * dependency. Minimum versions can be defined by adding the version to the
     * end of the string, using a colon to divide them. You may also add the
     * word optional to the start of the string to make the dependency optional.
     * Example `optional modid:1.2.4`
     * 
     * @return An array of dependency info.
     */
    String[] getDependencies();
}