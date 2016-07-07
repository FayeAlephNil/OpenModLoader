package xyz.openmodloader.event.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.PlayerEvent;

import javax.annotation.Nonnull;

public abstract class EventArrow extends PlayerEvent {
    @Nonnull
    public final ItemStack bow;
    public final ItemStack ammo;
    public final Type type;
    public final World world;

    /**
     * Constructor for an Arrow Event
     *
     * @param player The player involved with the Arrow Event
     * @param ammo the ammo being used by the bow
     */
    private EventArrow(EntityPlayer player, ItemStack ammo, Type type, World world) {
        super(player);
        this.bow = player.getHeldItemMainhand();
        this.ammo = ammo;
        this.type = type;
        this.world = world;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public enum Type {
        LOOSE,
        NOCK,
    }

    public static class Loose extends EventArrow {
        public float vel;
        public final int useTime;
        public final float relativeUseTime;

        Loose(EntityPlayer player, ItemStack ammo, World world, float vel, int useTime) {
            super(player, ammo, Type.LOOSE, world);
            this.vel = vel;
            this.useTime = useTime;
            this.relativeUseTime = useTime / bow.getMaxItemUseDuration();
        }

        public static EventArrow.Loose handle(EntityPlayer player, ItemStack ammo, World world, float vel, int usetime) {
            Loose e = new Loose(player, ammo, world, vel, usetime);
            OpenModLoader.getEventBus().post(e);
            return e;
        }
    }

    public static class Nock extends EventArrow {
        Nock(EntityPlayer player, ItemStack ammo, World world) {
            super(player, ammo, Type.NOCK, world);
        }

        public static EventArrow.Nock handle(EntityPlayer player, ItemStack ammo, World world) {
            Nock e = new Nock(player, ammo, world);
            OpenModLoader.getEventBus().post(e);
            return e;
        }
    }
}
