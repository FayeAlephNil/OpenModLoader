package xyz.openmodloader.test.mods;

import org.lwjgl.input.Keyboard;

import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.BlockEvent;
import xyz.openmodloader.event.impl.InputEvent;
import xyz.openmodloader.network.Channel;
import xyz.openmodloader.network.ChannelManager;
import xyz.openmodloader.network.DataTypes;
import xyz.openmodloader.test.TestMod;

public class TestPackets implements TestMod {

    private Channel channel;

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(BlockEvent.Place.class, this::onBlockPlace);
        OpenModLoader.getEventBus().register(InputEvent.Keyboard.class, this::onKeyPressed);
        testNetwork();
    }

    private void onBlockPlace(BlockEvent.Place event) {
        if (event.getWorld() instanceof WorldServer && event.getBlockState().getBlock() == Blocks.BEDROCK) {
<<<<<<< HEAD
            channel.send("test").set("str", "Hello, Client!").toAllInRadius((WorldServer) event.getWorld(), event.getPos(), 8);
=======
            channel.send("test")
                    .set("str", "Hello, Client!")
                    .toAllInRadius((WorldServer) event.getWorld(), event.getPos(), 8);
>>>>>>> a738e0a42e34673d786d40e44b372bd723ca46a6
        }
    }

    private void testNetwork() {
<<<<<<< HEAD
        channel = ChannelManager.create("OMLTest").createPacket("test").with("str", DataTypes.STRING).handle((context, packet) -> {
            System.out.println("PHYSICAL SIDE: " + OpenModLoader.getSidedHandler().getSide());
            System.out.println("THREAD: " + Thread.currentThread().getName());
            System.out.println("DATA: " + packet.get("str", DataTypes.STRING));
        }).build();
=======
        channel = ChannelManager.create("OMLTest")
                .createPacket("test")
                .with("str", DataTypes.STRING)
                .handle((context, packet) -> {
                    System.out.println("PHYSICAL SIDE: " + OpenModLoader.getSidedHandler().getSide());
                    System.out.println("THREAD: " + Thread.currentThread().getName());
                    System.out.println("DATA: " + packet.get("str", DataTypes.STRING));
                })
                .build();
>>>>>>> a738e0a42e34673d786d40e44b372bd723ca46a6
    }

    private void onKeyPressed(InputEvent.Keyboard event) {
        if (event.getKey() == Keyboard.KEY_SEMICOLON) {
<<<<<<< HEAD
            channel.send("test").set("str", "Hello, Server!").toServer();
=======
            channel.send("test")
                    .set("str", "Hello, Server!")
                    .toServer();
>>>>>>> a738e0a42e34673d786d40e44b372bd723ca46a6
        }
    }
}
