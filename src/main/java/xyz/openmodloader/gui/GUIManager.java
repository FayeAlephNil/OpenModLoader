package xyz.openmodloader.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {

    private static final Map<String, GUIHandler> handlers = new HashMap<>();

    public static void register(String owner, GUIHandler handler) {
        handlers.put(owner, handler);
    }

    public static GUIHandler get(String owner) {
        return handlers.get(owner);
    }

    public static void open(EntityPlayer player, String owner, String guiID, Context context) {
        if (player.worldObj.isRemote) {
            openClient(player, owner, guiID, context);
        } else {
            openServer(player, owner, guiID, context);
        }
    }

    private static void openClient(EntityPlayer player, String owner, String guiID, Context context) {
        Minecraft.getMinecraft().displayGuiScreen(handlers.get(owner).getGUI(guiID, context));
    }

    private static void openServer(EntityPlayer player, String modID, String owner, Context context) {
        player.openContainer = handlers.get(modID).getContainer(owner, context);
    }

}
