package xyz.openmodloader.event.impl;

import net.minecraft.item.ItemStack;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.Event;

/**
 * Fired when the burn time of a fuel is retrieved.
 */
public class FuelEvent extends Event {

    /**
     * The item stack of the fuel
     */
    private final ItemStack stack;

    /**
     * The burn time duration of the fuel
     */
    private int duration;

    /**
     * Creates a new fuel event instance.
     *
     * @param stack the item stack
     */
    public FuelEvent(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * @return the item stack of the fuel
     */
    public ItemStack getStack() {
        return stack;
    }

    /**
     * Sets the burn time duration of the fuel
     *
     * @param duration the burn time duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Retrieves the burn time of a fuel.
     *
     * @param stack the item stack of the fuel
     * @return the burn time of the fuel
     */
    public static int handle(ItemStack stack) {
        if (stack == null) return 0;
        FuelEvent event = new FuelEvent(stack);
        OpenModLoader.getEventBus().post(event);
        return event.duration;
    }
}
