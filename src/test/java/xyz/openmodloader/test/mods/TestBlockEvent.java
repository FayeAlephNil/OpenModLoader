package xyz.openmodloader.test.mods;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.BlockEvent;
import xyz.openmodloader.test.TestMod;

public class TestBlockEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(BlockEvent.Place.class, this::onBlockPlace);
        OpenModLoader.getEventBus().register(BlockEvent.Destroy.class, this::onBlockDestroy);
        OpenModLoader.getEventBus().register(BlockEvent.DigSpeed.class, this::onBlockDigSpeed);
        OpenModLoader.getEventBus().register(BlockEvent.HarvestDrops.class, this::onHarvestDrops);
    }

    private void onBlockPlace(BlockEvent.Place event) {
        OpenModLoader.getLogger().info("Placed block: " + event.getBlockState() + " isRemote: " + event.getWorld().isRemote);
        if (event.getBlockState().getBlock() == Blocks.GRASS) {
            event.setBlockState(Blocks.DIRT.getDefaultState());
        }
    }

    private void onBlockDestroy(BlockEvent.Destroy event) {
        OpenModLoader.getLogger().info("Destroyed block: " + event.getBlockState() + " isRemote: " + event.getWorld().isRemote);
        if (event.getBlockState().getBlock() == Blocks.GRASS) {
            event.setCanceled(true);
        }
    }

    private void onBlockDigSpeed(BlockEvent.DigSpeed event) {
        if (event.getBlockState().getBlock() == Blocks.DIRT) {
            event.setDigSpeed(0.05F);
        }
    }

    private void onHarvestDrops(BlockEvent.HarvestDrops event) {
        OpenModLoader.getLogger().info("Dropping items: " + event.getDrops() + ", with fortune: " + event.getFortune() + ", with chance: " + event.getChance());
        event.getDrops().add(new ItemStack(Blocks.DIRT));
    }
}
