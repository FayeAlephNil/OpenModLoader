package xyz.openmodloader.test.mods;

import java.awt.Color;

import net.minecraft.init.Biomes;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.BiomeEvent.BiomeColor;
import xyz.openmodloader.test.TestMod;

public class TestBiomeEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(BiomeColor.Grass.class, this::onGrassColor);
        OpenModLoader.getEventBus().register(BiomeColor.Foliage.class, this::onFoliageColor);
        OpenModLoader.getEventBus().register(BiomeColor.Water.class, this::onWaterColor);
    }

    private void onGrassColor(BiomeColor.Grass event) {
        if (event.getBiome() == Biomes.FOREST) {
            event.setColorModifier(Color.RED.getRGB());
        }
    }

    private void onFoliageColor(BiomeColor.Foliage event) {
        if (event.getBiome() == Biomes.FOREST) {
            event.setColorModifier(Color.RED.getRGB());
        }
    }

    private void onWaterColor(BiomeColor.Water event) {
        if (event.getBiome() == Biomes.FOREST) {
            event.setColorModifier(Color.RED.getRGB());
        }
    }
}
