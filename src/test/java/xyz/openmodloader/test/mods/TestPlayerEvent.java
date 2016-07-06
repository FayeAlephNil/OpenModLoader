package xyz.openmodloader.test.mods;

import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.Event;
import xyz.openmodloader.event.impl.EntityEvent;
import xyz.openmodloader.event.impl.PlayerEvent;
import xyz.openmodloader.event.impl.player.EventAchievement;
import xyz.openmodloader.event.impl.player.EventAnvilRepair;
import xyz.openmodloader.test.TestMod;

public class TestPlayerEvent implements TestMod {

    @Override
    public void onInitialize() {
        OpenModLoader.getEventBus().register(PlayerEvent.Craft.class, this::onCraft);
        OpenModLoader.getEventBus().register(PlayerEvent.Smelt.class, this::onSmelt);
        OpenModLoader.getEventBus().register(PlayerEvent.ItemPickup.class, this::onPickup);
        OpenModLoader.getEventBus().register(PlayerEvent.SleepCheck.class, this::onSleepCheck);
        OpenModLoader.getEventBus().register(PlayerEvent.Track.Start.class, this::onStartTracking);
        OpenModLoader.getEventBus().register(PlayerEvent.Track.Stop.class, this::onStopTracking);
        OpenModLoader.getEventBus().register(EventAnvilRepair.class, this::onRepair);
        OpenModLoader.getEventBus().register(EventAchievement.class, this::onAchievement);
    }

    private void onCraft(PlayerEvent.Craft event) {
        OpenModLoader.getLogger().info(event.getPlayer().getName() + " crafted " + event.getResult());
    }

    private void onSmelt(PlayerEvent.Smelt event) {
        OpenModLoader.getLogger().info(event.getPlayer().getName() + " smelted " + event.getResult());
        if (event.getResult().getItem() == Items.IRON_INGOT) {
            event.setXP(1.0F);
        }
    }

    private void onPickup(EntityEvent.ItemPickup event) {
        if (event.getItem().getEntityItem().getItem() == Items.APPLE) {
            event.setCanceled(true);
        }
    }

    private void onStartTracking(PlayerEvent.Track.Start event) {
        OpenModLoader.getLogger().info(event.getPlayer().getName() + " started tracking " + event.getTracking());
    }

    private void onStopTracking(PlayerEvent.Track.Stop event) {
        OpenModLoader.getLogger().info(event.getPlayer().getName() + " stopped tracking " + event.getTracking());
    }

    private void onSleepCheck(PlayerEvent.SleepCheck event) {
        OpenModLoader.getLogger().info("Sleep check occurred for %s at %s, default result is %s", event.getPlayer(), event.getPos(), event.getResult());
    }

    private void onRepair(EventAnvilRepair e) {
        OpenModLoader.getLogger().info("%s repaired %s into %s", e.getPlayer(), e.toRepair, e.output);
        if (e.toRepair.getItem() instanceof ItemSword && !e.book.isPresent() && e.output.getDisplayName() == "Test") {
            e.setCanceled(true);
            OpenModLoader.getLogger().info("The repair was cancelled");
        }
    }


    private void onAchievement(EventAchievement e) {
        if (e.achievement.getStatName().getUnformattedText().equals("Getting Wood")) {
            e.setCanceled(true);
        }
    }
}
