package xyz.openmodloader.event.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.PlayerEvent;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Event to be fired upon a player repairing an item in an anvil
 */
public class EventAnvilRepair extends PlayerEvent {
    /**
     * ItemStack that will be outputted if the event is not canceled
     */
    public final ItemStack output;

    /**
     * ItemStack that is being repaired, on the left side of the standard anvil gui
     */
    public final ItemStack toRepair;

    /**
     * Optional ItemStack that symbolizes whether an enchantment book is being used and holds an ItemStack for that book
     */
    public final Optional<ItemStack> book;

    /**
     * Constructor for an anvil repair event
     *
     * @param player The player that has fired this event by repairing an item
     * @param repair container this repair is taking place in
     */
    public EventAnvilRepair(EntityPlayer player, ContainerRepair repair) {
        super(player);
        this.toRepair = repair.getSlot(0).getStack();

        Slot bookSlot = repair.getSlot(1);
        if (bookSlot.getHasStack()) {
            this.book = Optional.of(bookSlot.getStack());
        } else {
            this.book = Optional.empty();
        }

        this.output = repair.getSlot(2).getStack();

    }

    /**
     * Creates this event, fires it, and returns it
     * @param player player repairing the item
     * @param repair container repair is taking place in
     * @return fired event
     */
    public static EventAnvilRepair handle(EntityPlayer player, ContainerRepair repair) {
        EventAnvilRepair event = new EventAnvilRepair(player, repair);
        OpenModLoader.getEventBus().post(event);
        return event;
    }

    /**
     * Override to allow this event to be cancelable
     * @return true
     */
    @Override
    public boolean isCancelable() {
        return true;
    }

    /**
     * Handles the firing of the event, MC codebase is patched so that ContainerRepair extends this instead of Container
     */
    public static abstract class OMLContainerRepair extends Container {
        /**
         * Fires the event upon the output being taken, taking care to check if the event was canceled
         */
        @Override
        @Nullable
        public ItemStack slotClick(int var1, int var2, ClickType var3, EntityPlayer var4) {
            if (var1 == 2 && this instanceof ContainerRepair) {
                if (this.getSlot(2).canTakeStack(var4) && !handle(var4, (ContainerRepair) this).isCanceled()) {
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
