package xyz.openmodloader.test;

/**
 * An interface for defining a test mod. Allows multiple test mods to be loaded
 * without causing merge conflicts.
 */
public interface TestMod {

    /**
     * Called during the main test mods initialize phase.
     */
    void onInitialize();
}