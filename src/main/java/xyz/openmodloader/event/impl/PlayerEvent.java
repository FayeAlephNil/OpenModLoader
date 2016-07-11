package xyz.openmodloader.event.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import xyz.openmodloader.OpenModLoader;

/**
 * Parent class for player related events. All events that fall within this
 * scope should extend this class. They should also be added as an inner class
 * however this is not required.
 */
public class PlayerEvent extends EntityEvent {

    /**
     * The player that fired this event.
     */
    protected final EntityPlayer player;

    /**
     * Constructor for the base player events. This constructor should only be
     * accessed through super calls.
     *
     * @param player The player that has fired this event.
     */
    public PlayerEvent(EntityPlayer player) {
        super(player);
        this.player = player;
    }

    /**
     * Gets the player that fired this event.
     *
     * @return the player that fired this event.
     */
    public EntityPlayer getPlayer() {
        return player;
    }

    /**
     * The base class for entity-tracking related events.
     */
    public static class Track extends PlayerEvent {

        /**
         * The entity being tracked by the player.
         */
        protected final Entity tracking;

        /**
         * Base constructor for entity-tracking related events.
         *
         * @param player the player tracking the entity.
         * @param tracking the entity being tracked.
         */
        public Track(EntityPlayer player, Entity tracking) {
            super(player);
            this.tracking = tracking;
        }

        /**
         * Gets the entity that is being tracked by the player.
         *
         * @return the entity that is being tracked by the player.
         */
        public Entity getTracking() {
            return tracking;
        }

        /**
         * This event is fired when a player starts tracking an entity.
         */
        public static class Start extends Track {

            /**
             * Constructor for new event that is fired when a player starts
             * tracking an entity.
             *
             * @param player the player tracking the entity.
             * @param tracking the entity being tracked.
             */
            public Start(EntityPlayer player, Entity tracking) {
                super(player, tracking);
            }
        }

        /**
         * This event is fired when a player stops tracking an entity.
         */
        public static class Stop extends Track {

            /**
             * Constructor for new event that is fired when a player stops
             * tracking an entity.
             *
             * @param player the player tracking the entity.
             * @param tracking the entity being tracked.
             */
            public Stop(EntityPlayer player, Entity tracking) {
                super(player, tracking);
            }
        }
    }

    /**
     * An even that is fired to check if the player can sleep at the given
     * location. Fired from {@link EntityPlayer#isInBed()}
     */
    public static class SleepCheck extends PlayerEvent {

        /**
         * The player's position
         *
         * @see #getPos()
         */
        private final BlockPos pos;

        /**
         * If the player can sleep By default, this uses the vanilla
         * implementation (if the block at the player's position is a bed, the
         * player can sleep)
         *
         * @see #getResult()
         * @see #setResult(boolean)
         */
        private boolean result;

        private SleepCheck(EntityPlayer player, BlockPos pos, boolean result) {
            super(player);
            this.pos = pos;
            this.result = result;
        }

        /**
         * @return The player's position
         */
        public BlockPos getPos() {
            return pos;
        }

        /**
         * @return If the player can sleep
         */
        public boolean getResult() {
            return result;
        }

        /**
         * Set's if the player can sleep
         *
         * @param result {@code true} if the player can sleep, {@code false} if
         *        they can't
         */
        public void setResult(boolean result) {
            this.result = result;
        }

        /**
         * Convenience method for checking if the given player can sleep at the
         * given position
         *
         * @param player The player
         * @param pos The player's position
         * @return If the player can sleep
         */
        public static boolean handle(EntityPlayer player, BlockPos pos) {
            SleepCheck event = new SleepCheck(player, pos, player.worldObj.getBlockState(pos).getBlock() == Blocks.BED);
            OpenModLoader.getEventBus().post(event);
            return event.result;
        }
    }
}
