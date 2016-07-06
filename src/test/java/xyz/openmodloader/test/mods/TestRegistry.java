package xyz.openmodloader.test.mods;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.openmodloader.registry.GameRegistry;
import xyz.openmodloader.test.TestMod;

public class TestRegistry implements TestMod {

    @Override
    public void onInitialize() {

        GameRegistry.registerBlock(new ResourceLocation("oml:blank"), new BlockEmpty());
        GameRegistry.registerBlock(new ResourceLocation("oml:test"), new BlockTest());
        GameRegistry.registerItem(new ResourceLocation("oml:blank_item"), new Item());
    }

    public class BlockEmpty extends Block {

        public BlockEmpty() {
            super(Material.ROCK);
        }
    }

    public class BlockTest extends Block {

        public BlockTest() {
            super(Material.ROCK);
            this.setCreativeTab(CreativeTabs.BREWING);
            this.setUnlocalizedName("test");
        }

        @Override
        public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumHand var5, @Nullable ItemStack var6, EnumFacing var7, float var8, float var9, float var10) {
            var4.addChatMessage(new TextComponentString("You right clicked the block"));
            return true;
        }
    }
}
