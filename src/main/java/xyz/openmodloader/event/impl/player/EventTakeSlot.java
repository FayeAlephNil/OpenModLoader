package xyz.openmodloader.event.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.PlayerEvent;

import javax.annotation.Nullable;
import java.util.Optional;

public class EventTakeSlot extends PlayerEvent {
    /**
     * Slot being taken from
     */
    protected final Slot slot;

    /**
     * Item that is being taken
     */
    public final ItemStack result;

    /**
     * Constructor for the TakeSlot events. This constructor should only be
     * accessed through super calls.
     *
     * @param player The player that has taken from slot
     * @param slot slot being taken
     */
    protected EventTakeSlot(EntityPlayer player, Slot slot) {
        super(player);
        this.slot = slot;
        this.result = slot.getStack();
    }

    /**
     * Event to be fired upon a player repairing an item in an anvil
     */
    public static class Repair extends EventTakeSlot {
        /**
         * ItemStack that is being repaired, on the left side of the standard anvil gui
         */
        public final ItemStack toRepair;

        /**
         * Optional ItemStack that symbolizes whether an enchantment book is being used and holds an ItemStack for that book
         */
        public final Optional<ItemStack> book;

        /**
         * Experience cost incurred upon the repair if it is not canceled
         */
        public int cost;

        /**
         * Constructor for an anvil repair event
         *
         * @param player The player that has fired this event by repairing an item
         * @param repair container this repair is taking place in
         */
        public Repair(EntityPlayer player, ContainerRepair repair) {
            super(player, repair.getSlot(2));

            this.cost = repair.maximumCost;
            this.toRepair = repair.getSlot(0).getStack();

            Slot bookSlot = repair.getSlot(1);
            if (bookSlot.getHasStack()) {
                this.book = Optional.of(bookSlot.getStack());
            } else {
                this.book = Optional.empty();
            }
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        public static Repair handle(EntityPlayer player, ContainerRepair repair) {
            Repair event = new Repair(player, repair);
            OpenModLoader.getEventBus().post(event);
            return event;
        }

        /**
         * Handles the firing of the event, MC codebase is patched so that ContainerRepair extends this instead of Container
         */
        public static abstract class OMLContainerRepair extends Container {
            protected int maximumCost;

            /**
             * Fires the event upon the output being taken, taking care to check if the event was canceled
             */
            @Override
            @Nullable
            public ItemStack slotClick(int var1, int var2, ClickType var3, EntityPlayer var4) {
                if (var1 == 2 && this instanceof ContainerRepair) {
                    Repair event = handle(var4, (ContainerRepair) this);
                    if (this.getSlot(2).canTakeStack(var4) && !event.isCanceled()) {
                        this.maximumCost = event.cost;
                        return super.slotClick(var1, var2, var3, var4);
                    } else {
                        return null;
                    }
                } else {
                    return super.slotClick(var1, var2, var3, var4);
                }
            }
        }
    }

    /**
     * Event fired upon a furnace output being taken
     */
    public static class Smelt extends EventTakeSlot {
        /**
         * Experience gained upon smelting
         */
        public float xp;

        /**
         * Creates an appropriate smelting event taking care of experience
         * @param xp experience gained upon smelt
         */
        public Smelt(EntityPlayer player, SlotFurnaceOutput slot, float xp) {
            super(player, slot);
            this.xp = xp;
        }

        public static float handle(EntityPlayer player, SlotFurnaceOutput slot, float xp) {
            EventTakeSlot.Smelt event = new EventTakeSlot.Smelt(player, slot, xp);
            OpenModLoader.getEventBus().post(event);
            return event.xp;
        }
    }

    /**
     * This event is fired when a player takes a crafted item.
     */
    public static class Craft extends EventTakeSlot {
        /**
         * Constructor for the new event that is fired when a player crafts an
         * item with a crafting table
         */
        public Craft(EntityPlayer player, SlotCrafting slot) {
            super(player, slot);
        }

        public static void handle(EntityPlayer player, SlotCrafting slot) {
            OpenModLoader.getEventBus().post(new Craft(player, slot));
        }
    }
}
