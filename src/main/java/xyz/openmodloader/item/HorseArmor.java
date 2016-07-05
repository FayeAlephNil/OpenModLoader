package xyz.openmodloader.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.ItemStack;

/**
 * An interface that can be implemented by an Item to allow the item to function
 * as a piece of horse armor.
 */
public interface HorseArmor {

    /**
     * Gets the HorseArmorType to use for this horse armor.
     * 
     * @param stack The ItemStack instance of the armor.
     * @return The HorseArmorType to use for the armor item.
     */
    HorseArmorType getArmorType(ItemStack stack);

    /**
     * Gets the texture to use for the armor texture. The texture string follows
     * standard resource location format.
     * 
     * @param wearer The entity wearing the armor. May not be a horse.
     * @param stack The ItemStack instance of the armor.
     * @return The texture to use for the armor.
     */
    String getArmorTexture(EntityLivingBase wearer, ItemStack stack);
}
