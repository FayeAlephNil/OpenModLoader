package xyz.openmodloader.test.mods;

import org.lwjgl.input.Keyboard;

import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.InputEvent;
import xyz.openmodloader.test.TestMod;

public class TestInputEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(InputEvent.Keyboard.class, this::onKeyPressed);
        OpenModLoader.getEventBus().register(InputEvent.Mouse.class, this::onMouseClick);
    }

    private void onKeyPressed(InputEvent.Keyboard event) {
        OpenModLoader.getLogger().info(String.format("Key pressed %c (%d)", event.getCharacter(), event.getKey()));
    }

    private void onMouseClick(InputEvent.Mouse event) {
        OpenModLoader.getLogger().info(String.format("Mouse clicked, %d", event.getButton()));
        if (event.getButton() == Keyboard.KEY_S) {
            event.setCanceled(true);
        }
    }
}
