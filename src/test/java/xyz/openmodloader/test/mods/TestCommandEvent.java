package xyz.openmodloader.test.mods;

import java.util.Arrays;

import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.CommandEvent;
import xyz.openmodloader.test.TestMod;

public class TestCommandEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(CommandEvent.class, this::onCommandRan);
    }

    private void onCommandRan(CommandEvent event) {
        OpenModLoader.getLogger().info("Player: " + event.getSender().getName() + " ran command: " + event.getCommand().getCommandName() + " with arguments: " + Arrays.toString(event.getArgs()));
    }
}
