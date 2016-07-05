package xyz.openmodloader.test.mods;

import java.io.File;

import xyz.openmodloader.config.Config;
import xyz.openmodloader.test.TestMod;

public class TestConfiguration implements TestMod {

    @Override
    public void onInitialize() {

        Config config = new Config(new File("./config/test.conf"));
        Config category1 = config.getConfig("category1", "configures stuff");
        category1.getBoolean("boolean1", true, "this is a boolean");
        category1.getBoolean("boolean2", true, "this is another boolean");
        Config category2 = category1.getConfig("category2", "configures more stuff");
        category2.getString("string1", "string", "this is a string");
        config.save();
    }
}
