package xyz.openmodloader.test.mods;

import net.minecraft.init.Enchantments;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.EnchantmentEvent;
import xyz.openmodloader.test.TestMod;

public class TestEnchantmentEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(EnchantmentEvent.Item.class, this::onItemEnchanted);
        OpenModLoader.getEventBus().register(EnchantmentEvent.Level.class, this::onEnchantmentLevelCheck);
    }

    private void onItemEnchanted(EnchantmentEvent.Item event) {
        OpenModLoader.getLogger().info(event.getItemStack().getDisplayName() + " " + event.getEnchantments().toString());
    }

    private void onEnchantmentLevelCheck(EnchantmentEvent.Level event) {
        if (event.getEnchantment() == Enchantments.FORTUNE && event.getEntityLiving().isSneaking()) {
            int oldLevel = event.getLevel();
            int newLevel = (oldLevel + 1) * 10;
            event.setLevel(newLevel);
            OpenModLoader.getLogger().info("Set fortune level from " + oldLevel + " to " + newLevel);
        }
    }
}
