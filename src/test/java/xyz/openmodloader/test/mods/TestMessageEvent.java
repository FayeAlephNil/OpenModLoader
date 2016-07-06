package xyz.openmodloader.test.mods;

import net.minecraft.client.resources.I18n;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.MessageEvent;
import xyz.openmodloader.launcher.strippable.Side;
import xyz.openmodloader.test.TestMod;

public class TestMessageEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(MessageEvent.Chat.class, event -> {
            if (event.getSide() == Side.CLIENT) {
                String message = event.getMessage().getUnformattedText();
                if (message.equals(I18n.format("tile.bed.occupied")) || message.equals(I18n.format("tile.bed.noSleep")) || message.equals(I18n.format("tile.bed.notSafe")) || message.equals(I18n.format("tile.bed.notValid"))) {
                    OpenModLoader.getSidedHandler().openSnackbar(event.getMessage());
                    event.setCanceled(true);
                }
            }
        });
    }
}
