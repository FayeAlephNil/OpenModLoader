package xyz.openmodloader.test.mods;

import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import org.apache.logging.log4j.Logger;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.EntityEvent;
import xyz.openmodloader.event.impl.PlayerEvent;
import xyz.openmodloader.event.impl.player.EventAchievement;
import xyz.openmodloader.event.impl.player.EventAnvilRepair;
import xyz.openmodloader.event.impl.player.EventArrow;
import xyz.openmodloader.test.TestMod;

public class TestPlayerEvent implements TestMod {

    private static final Logger LOGGER = OpenModLoader.getLogger();

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
        OpenModLoader.getEventBus().register(EventArrow.Nock.class, this::onNock);
        OpenModLoader.getEventBus().register(EventArrow.Loose.class, this::onLoose);
    }

    private void onCraft(PlayerEvent.Craft event) {
        LOGGER.info(event.getPlayer().getName() + " crafted " + event.getResult());
    }

    private void onSmelt(PlayerEvent.Smelt event) {
        LOGGER.info(event.getPlayer().getName() + " smelted " + event.getResult());
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
        LOGGER.info(event.getPlayer().getName() + " started tracking " + event.getTracking());
    }

    private void onStopTracking(PlayerEvent.Track.Stop event) {
        LOGGER.info(event.getPlayer().getName() + " stopped tracking " + event.getTracking());
    }

    private void onSleepCheck(PlayerEvent.SleepCheck event) {
        LOGGER.info("Sleep check occurred for %s at %s, default result is %s", event.getPlayer(), event.getPos(), event.getResult());
    }

    private void onRepair(EventAnvilRepair e) {
        LOGGER.info("%s repaired %s into %s", e.getPlayer(), e.toRepair, e.output);
        if (e.toRepair.getItem() instanceof ItemSword && !e.book.isPresent() && e.output.getDisplayName() == "Test") {
            e.setCanceled(true);
            LOGGER.info("The repair was cancelled");
        }
    }


    private void onAchievement(EventAchievement e) {
        if (e.achievement.getStatName().getUnformattedText().equals("Getting Wood")) {
            e.setCanceled(true);
        }
    }

    private void onNock(EventArrow.Nock e) {
        if (e.bow.getDisplayName().equals("TestCancelNock")) {
            LOGGER.info("Canceled a nocking event");
            e.setCanceled(true);
        }
    }

    private void onLoose(EventArrow.Loose e) {
        final String name = e.bow.getDisplayName();
        String tolog = String.format("Bow fired (display name is %s): ", name);
        if (name.equals("TestCancelLoose")) {
            tolog += "Canceled";
            e.setCanceled(true);
        } else if (name.equals("TestFast")) {
            tolog += "Hastened";
            e.vel *= 5F;
        } else if (name.equals("TestSlow")) {
            tolog += "Slowed";
            e.vel *= 0.2F;
        }
        LOGGER.info(tolog);
    }
}
