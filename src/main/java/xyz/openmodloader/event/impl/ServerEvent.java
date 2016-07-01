package xyz.openmodloader.event.impl;

import java.util.Map;

import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import xyz.openmodloader.event.Event;

/**
 * Parent class for server related events. All events that fall within this
 * scope should extend this class. They should also be added as an inner class
 * however this is not required.
 */
public abstract class ServerEvent extends Event {

    /**
     * The server that has fired this event.
     */
    private final MinecraftServer server;

    public ServerEvent(MinecraftServer server) {
        this.server = server;
    }

    /**
     * The server that has fired this event.
     */
    public MinecraftServer getServer() {
        return server;
    }

    /**
     * Fired when the server is about to start.
     * Fired from {@link DedicatedServer#startServer()}
     * and {@link IntegratedServer#startServer()}.
     * 
     * This event is not cancelable.
     */
    public static class Starting extends ServerEvent {

        public Starting(MinecraftServer server) {
            super(server);
        }
    }

    /**
     * Fired immediately after the server has started.
     * Fired from {@link DedicatedServer#startServer()}
     * and {@link IntegratedServer#startServer()}.
     * 
     * This event is not cancelable.
     */
    public static class Started extends ServerEvent {

        public Started(MinecraftServer server) {
            super(server);
        }

        public void registerCommand(ICommand command) {
            Map<String, ICommand> map = getServer().commandManager.getCommands();
            map.put(command.getCommandName(), command);
            command.getCommandAliases().stream().forEach((k) -> map.put(k, command));
        }
    }

    /**
     * Fired when the server is about to stop.
     * Fired from {@link MinecraftServer#stopServer()}.
     * 
     * This event is not cancelable.
     */
    public static class Stopping extends ServerEvent {

        public Stopping(MinecraftServer server) {
            super(server);
        }
    }

    /**
     * Fired immediately after the server has stopped.
     * Fired from {@link MinecraftServer#stopServer()}.
     * 
     * This event is not cancelable.
     */
    public static class Stopped extends ServerEvent {

        public Stopped(MinecraftServer server) {
            super(server);
        }
    }
}