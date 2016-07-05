package xyz.openmodloader.test.mods;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import xyz.openmodloader.dictionary.ShapedMaterialRecipe;
import xyz.openmodloader.dictionary.ShapelessMaterialRecipe;
import xyz.openmodloader.test.TestMod;

public class TestDictionary implements TestMod {

    @Override
    public void onInitialize() {

        CraftingManager.getInstance().addRecipe(new ShapedMaterialRecipe(new ItemStack(Items.STICK, 16), "L", "L", 'L', "logWood"));
        CraftingManager.getInstance().addRecipe(new ShapelessMaterialRecipe(new ItemStack(Items.STICK, 32), "logWood", "logWood", "logWood"));
    }
}
