package xyz.openmodloader.event.impl;

import java.util.Map;

import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import xyz.openmodloader.event.Event;

public class ServerEvent extends Event {

    private final MinecraftServer server;

    public ServerEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public static class Starting extends ServerEvent {

        public Starting(MinecraftServer server) {
            super(server);
        }
    }

    public static class Started extends ServerEvent {

        public Started(MinecraftServer server) {
            super(server);
        }

        public void registerCommand(ICommand command) {
            Map<String, ICommand> map = getServer().commandManager.getCommands();
            command.getCommandAliases().stream().forEach((k) -> map.put(k, command));
        }
    }

    public static class Stopping extends ServerEvent {

        public Stopping(MinecraftServer server) {
            super(server);
        }
    }

    public static class Stopped extends ServerEvent {

        public Stopped(MinecraftServer server) {
            super(server);
        }
    }
}