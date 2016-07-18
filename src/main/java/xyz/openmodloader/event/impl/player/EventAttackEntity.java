package xyz.openmodloader.event.impl.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.impl.LivingEvent;
import xyz.openmodloader.event.impl.PlayerEvent;

public class EventAttackEntity extends PlayerEvent {
    public DamageSource source;
    public float damage;
    public final EntityLivingBase target;

    public EventAttackEntity(EntityPlayer player, DamageSource source, float damage, EntityLivingBase target) {
        super(player);
        this.source = source;
        this.damage = damage;
        this.target = target;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    private LivingEvent.Hurt setAll(LivingEvent.Hurt e) {
        e.setSource(source);
        e.setDamage(damage);
        e.setCanceled(this.isCanceled());
        return e;
    }

    private static void handle(LivingEvent.Hurt e) {
        EntityPlayer player = (EntityPlayer) e.getSource().getEntity();
        EventAttackEntity attack = new EventAttackEntity(player, e.getSource(), e.getDamage(), e.getLiving());
        OpenModLoader.getEventBus().post(attack);
        attack.setAll(e);
    }

    public static void handler(LivingEvent.Hurt e) {
        Entity entity = e.getSource().getEntity();

        // Leave space in case other instances show up
        if (entity instanceof EntityPlayer) {
            handle(e);
        }
    }
}
