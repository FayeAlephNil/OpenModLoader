package xyz.openmodloader.test.mods;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.openmodloader.item.HorseArmor;
import xyz.openmodloader.registry.GameRegistry;
import xyz.openmodloader.test.TestMod;

public class TestHorseArmor implements TestMod {

    @Override
    public void onInitialize() {

        GameRegistry.registerHorseArmor(new ResourceLocation("oml:test_armor"), ItemTestHorseArmor.TYPE);
    }

    public static class ItemTestHorseArmor extends Item implements HorseArmor {

        public static final HorseArmorType TYPE = new HorseArmorType(5, "omltest", "omltest");

        public ItemTestHorseArmor() {
            this.setCreativeTab(CreativeTabs.COMBAT);
            this.setUnlocalizedName("oml.horsearmor");
        }

        @Override
        public HorseArmorType getArmorType(ItemStack stack) {
            return TYPE;
        }

        @Override
        public String getArmorTexture(EntityLivingBase wearer, ItemStack stack) {
            return "omltest:textures/entity/horse/armor_test.png";
        }
    }
}
