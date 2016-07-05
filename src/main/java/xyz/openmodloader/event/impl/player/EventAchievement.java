package xyz.openmodloader.event.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.PlayerEvent;

public class EventAchievement extends PlayerEvent {
    public Achievement achievement;
    public int data;

    /**
     * Constructor for an achievement event
     * accessed through super calls.
     *
     * @param player The player that has fired this event.
     * @param achievement The stat (achievement) that is being added to the player
     * @param data The integer value to be logged in the statData hash with the achievement
     */
    public EventAchievement(EntityPlayer player, Achievement achievement, int data) {
        super(player);
        this.achievement = achievement;
        this.data = data;
    }

    public static EventAchievement handle(EntityPlayer player, Achievement statBase, int data) {
        EventAchievement event = new EventAchievement(player, statBase, data);
        OpenModLoader.getEventBus().post(event);
        return event;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
