package xyz.openmodloader.test.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.text.TextComponentString;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.client.gui.GuiModInfo;
import xyz.openmodloader.client.gui.GuiModList;
import xyz.openmodloader.event.impl.GuiEvent;
import xyz.openmodloader.modloader.version.UpdateManager;
import xyz.openmodloader.test.TestMod;

public class TestGuiEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(GuiEvent.Open.class, this::onGuiOpen);
        OpenModLoader.getEventBus().register(GuiEvent.Draw.class, this::onGuiDraw);
        OpenModLoader.getEventBus().register(GuiEvent.SplashLoad.class, this::onSplashLoad);
    }

    private void onGuiOpen(GuiEvent.Open event) {
        OpenModLoader.getLogger().info("Opening gui: " + event.getGui());
        if (event.getGui() instanceof GuiLanguage) {
            event.setCanceled(true);
        } else if (event.getGui() instanceof GuiMainMenu) {
            if (!UpdateManager.getOutdatedMods().isEmpty()) {
                OpenModLoader.getSidedHandler().openSnackbar(new TextComponentString("Mod updates found!"));
            }
        }
    }

    private void onGuiDraw(GuiEvent.Draw event) {
        if (!(event.getGui() instanceof GuiMainMenu) && !(event.getGui() instanceof GuiModList) && !(event.getGui() instanceof GuiModInfo)) {
            Minecraft.getMinecraft().fontRendererObj.drawString("Open Mod Loader", 5, 5, 0xFFFFFFFF);
        }
    }

    private void onSplashLoad(GuiEvent.SplashLoad event) {
        event.getSplashTexts().clear();
        event.getSplashTexts().add("OpenModLoader Test!");
    }
}
