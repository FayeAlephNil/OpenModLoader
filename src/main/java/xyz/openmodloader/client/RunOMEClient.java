package xyz.openmodloader.client;

import com.google.common.base.Strings;
import net.minecraft.client.main.Main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RunOMEClient {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("./.gradle/minecraft/natives/").getAbsolutePath());
        Main.main(getArgs());
    }

    private static String[] getArgs() {
        Map<String, String> argMap = new HashMap<>();
        argMap.put("version", "1.10");
        argMap.put("accessToken", "GRASS");
        argMap.put("tweakClass", "xyz.openmodloader.launcher.OMLTweaker");

        ArrayList list = new ArrayList();
        Iterator out = argMap.entrySet().iterator();
        while (out.hasNext()) {
            Map.Entry b = (Map.Entry) out.next();
            String x = (String) b.getValue();
            if (!Strings.isNullOrEmpty(x)) {
                list.add("--" + (String) b.getKey());
                list.add(x);
            }
        }
        String[] var5 = (String[]) list.toArray(new String[list.size()]);
        return var5;
    }
}
