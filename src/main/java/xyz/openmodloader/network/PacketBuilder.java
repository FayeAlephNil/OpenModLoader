package xyz.openmodloader.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;
import xyz.openmodloader.OpenModLoader;

/**
 * Builds packets
 */
public class PacketBuilder {

    private final Channel channel;
    final PacketSpec spec;
    final Map<String, Object> values = new HashMap<>();
    final Map<String, DataType> types = new HashMap<>();

    PacketBuilder(Channel channel, PacketSpec spec) {
        this.channel = channel;
        this.spec = spec;
    }

    /**
     * Stores the given value for the given ID
     * 
     * @param id The ID of the value being stored
     * @param value The value to store
     * @return This packet builder
     */
    public PacketBuilder set(String id, Object value) {
        if (!spec.types.containsKey(id))
            throw new IllegalArgumentException("No such key " + id);
        if (!spec.types.get(id).getClazz().isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException(String.format("Key %s expected type %s, got %s", id, spec.types.get(id).getClazz(), value.getClass()));
        if (values.containsKey(id))
            throw new IllegalArgumentException(String.format("Key %s is already set to %s", id, values.get(id)));

        values.put(id, value);
        types.put(id, spec.types.get(id));

        return this;
    }

    // Client -> Server
    /**
     * Sends this packet from the client to the server
     */
    public void toServer() {
        OpenModLoader.getSidedHandler().getClientPlayer().connection.sendPacket(new PacketWrapper(channel, new Packet(this)));
    }

    // Server -> Client
    /**
     * Sends this packet to the given player
     * 
     * @param player The player to send to
     */
    public void toPlayer(EntityPlayerMP player) {
        player.connection.sendPacket(new PacketWrapper(channel, new Packet(this)));
    }

    /**
     * Sends this packet to all the players in the given list
     * 
     * @param players The players to send to
     */
    public void toAll(List<EntityPlayerMP> players) {
        PacketWrapper packet = new PacketWrapper(channel, new Packet(this));
        players.stream()
                .map(player -> player.connection)
                .forEach(connection -> connection.sendPacket(packet));
    }

    /**
     * Sends this packet to all players on the server
     */
    public void toAll() {
        toAll(OpenModLoader.getSidedHandler().getServer().getPlayerList().getPlayerList());
    }

    /**
     * Sends this packet to all players in the list where the predicate returns
     * {@code true}
     * 
     * @param players All the players to test sending to
     * @param predicate The function to test if the packet should be sent
     */
    public void toAll(List<EntityPlayerMP> players, Predicate<EntityPlayerMP> predicate) {
        toAll(players.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }

    /**
     * Sends this packet to all the players in the world within the radius
     * 
     * @param world The world
     * @param pos The base position
     * @param radius The radius around the position that players must be in to
     *        send the packet to
     */
    public void toAllInRadius(WorldServer world, Vec3d pos, double radius) {
        double maxDistance = radius * radius + radius * radius + radius * radius;
        toAll(world.getPlayers(EntityPlayerMP.class, player -> (player.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord) <= maxDistance)));
    }

    /**
     * Sends this packet to all the players in the world within the radius
     * 
     * @param world The world
     * @param pos The base position
     * @param radius The radius around the position that players must be in to
     *        send the packet to
     */
    public void toAllInRadius(WorldServer world, Vec3i pos, double radius) {
        toAllInRadius(world, new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), radius);
    }

    /**
     * Sends this packet to all the players in the dimension within the radius
     * 
     * @param dimension The dimension
     * @param pos The base position
     * @param radius The radius around the position that players must be in to
     *        send the packet to
     */
    public void toAllInRadius(int dimension, Vec3d pos, double radius) {
        toAllInRadius(OpenModLoader.getSidedHandler().getServer().worldServerForDimension(dimension), pos, radius);
    }

    /**
     * Sends this packet to all the players in the dimension within the radius
     * 
     * @param dimension The dimension
     * @param pos The base position
     * @param radius The radius around the position that players must be in to
     *        send the packet to
     */
    public void toAllInRadius(int dimension, Vec3i pos, double radius) {
        toAllInRadius(dimension, new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), radius);
    }

}
