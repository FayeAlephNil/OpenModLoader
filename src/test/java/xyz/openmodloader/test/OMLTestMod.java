package xyz.openmodloader.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.openmodloader.modloader.Mod;

public class OMLTestMod implements Mod {

    private static final Logger LOGGER = LogManager.getFormatterLogger("Open Mod Tests");

    @Override
    public void onInitialize() {
        LOGGER.info("Loading test mods");

        try {
            final List<Class<?>> testMods = Mirrorpool.getClasses("xyz.openmodloader.test.mods");
            final Method init = TestMod.class.getMethod("onInitialize", new Class[] {});
            for (Class<?> mod : testMods) {
                if (TestMod.class.isAssignableFrom(mod)) {
                    init.invoke(mod.newInstance(), null);
                    LOGGER.info("Loading " + mod.getName());
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
            LOGGER.warn(e);
        }
    }
}
