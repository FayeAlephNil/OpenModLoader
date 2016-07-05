package xyz.openmodloader.test.mods;

import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.ExplosionEvent;
import xyz.openmodloader.test.TestMod;

public class TestExplosionEvent implements TestMod {

    @Override
    public void onInitialize() {

        OpenModLoader.getEventBus().register(ExplosionEvent.class, this::onExplosion);
    }

    private void onExplosion(ExplosionEvent event) {
        event.setCanceled(true);
    }
}
