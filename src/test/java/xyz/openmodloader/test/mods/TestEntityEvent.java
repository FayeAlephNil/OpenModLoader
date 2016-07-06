package xyz.openmodloader.test.mods;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.EntityEvent;
import xyz.openmodloader.test.TestMod;

public class TestEntityEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(EntityEvent.Constructing.class, this::onEntityConstruct);
        OpenModLoader.getEventBus().register(EntityEvent.Join.class, this::onEntityJoinWorld);
        OpenModLoader.getEventBus().register(EntityEvent.ChangeDimension.class, this::onChangeDimension);
        OpenModLoader.getEventBus().register(EntityEvent.Mount.class, this::onMount);
        OpenModLoader.getEventBus().register(EntityEvent.Dismount.class, this::onDismount);
        OpenModLoader.getEventBus().register(EntityEvent.LightningStruck.class, this::onLightningStrike);
    }

    private void onLightningStrike(EntityEvent.LightningStruck event) {
        if (event.getEntity() instanceof EntityCreeper) {
            event.setCanceled(true);
        }
    }

    private void onEntityConstruct(EntityEvent.Constructing event) {
        if (event.getEntity() instanceof EntityPlayer) {
            OpenModLoader.getLogger().info("A player was constructed.");
        }
    }

    private void onEntityJoinWorld(EntityEvent.Join event) {
        if (event.getEntity() instanceof EntityPlayer) {
            OpenModLoader.getLogger().info(String.format("A player joined the world on side %s.", event.getWorld().isRemote ? "client" : "server"));
        }
        if (event.getEntity() instanceof EntityPig) {
            event.setCanceled(true);
        }
    }

    private void onChangeDimension(EntityEvent.ChangeDimension event) {
        OpenModLoader.getLogger().info("Entity: %s is travelling from dimension %d to %d", event.getEntity(), event.getPreviousDimension(), event.getNewDimension());
        if (event.getNewDimension() == -1) {
            event.setNewDimension(1);
        }
    }

    private void onMount(EntityEvent.Mount event) {
        if (event.getRiding() instanceof EntityPig) {
            event.setCanceled(true);
        }
    }

    private void onDismount(EntityEvent.Dismount event) {
        if (event.getRiding() instanceof EntityHorse) {
            event.setCanceled(true);
        }
    }
}
