package xyz.openmodloader.test.mods;

import java.util.Objects;

import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.EquipmentEvent;
import xyz.openmodloader.test.TestMod;

public class TestEquipmentEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(EquipmentEvent.Equip.class, this::onArmorEquip);
        OpenModLoader.getEventBus().register(EquipmentEvent.Unequip.class, this::onArmorUnequip);
    }

    private void onArmorEquip(EquipmentEvent.Equip event) {
        OpenModLoader.getLogger().info("Entity: " + event.getEntity().getName() + " equipped " + Objects.toString(event.getEquipment()) + " to the " + event.getSlot().getName() + " slot");
        event.setCanceled(true);
    }

    private void onArmorUnequip(EquipmentEvent.Unequip event) {
        OpenModLoader.getLogger().info("Entity: " + event.getEntity().getName() + " unequipped " + Objects.toString(event.getEquipment()) + " to the " + event.getSlot().getName() + " slot");
        event.setCanceled(true);
    }
}
