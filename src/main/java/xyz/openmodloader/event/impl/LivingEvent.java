package xyz.openmodloader.event.impl;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldEntitySpawner;
import xyz.openmodloader.OpenModLoader;
import xyz.openmodloader.event.Event;

/**
 * Parent class for world related events. All events that fall within this scope
 * should extend this class. They should usually also be added as inner classes,
 * although this is not an absolute requirement.
 */
public abstract class LivingEvent extends Event {

    /**
     * The living entity that fired this event.
     */
    protected EntityLivingBase living;

    /**
     * Creates a new LivingEvent. For internal use only.
     * 
     * @param living the living entity that fired this event
     */
    public LivingEvent(EntityLivingBase living) {
        this.living = living;
    }

    /**
     * Gets the living entity that fired this event.
     * 
     * @return the living entity that fired this event
     */
    public EntityLivingBase getLiving() {
        return living;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    /**
     * Fired when an entity is about to spawn. Fired from
     * {@link WorldEntitySpawner#findChunksForSpawning(net.minecraft.world.WorldServer, boolean, boolean, boolean)}
     * .
     * 
     * This event is cancelable.
     */
    public static class Spawn extends LivingEvent {

        /**
         * Creates a new spawn event.
         * 
         * @param living the living that is about to spawn
         */
        public Spawn(EntityLivingBase living) {
            super(living);
        }

        /**
         * Sets the living entity to spawn.
         * 
         * @param living the living to spawn
         */
        public void setLiving(EntityLivingBase living) {
            this.living = living;
        }

        /**
         * Checks if is cancelable.
         *
         * @return true, if is cancelable
         */
        @Override
        public boolean isCancelable() {
            return true;
        }

        /**
         * Internal method for handling spawn events. Helps minimize patch
         * sizes.
         *
         * @param living the living
         * @return the spawn
         */
        public static Spawn handle(EntityLivingBase living) {
            Spawn event = new Spawn(living);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }

    /**
     * Fired when an entity is about to despawn. Fired from
     * {@link EntityLiving#despawnEntity()}.
     * 
     * This event is cancelable.
     */
    public static class Despawn extends LivingEvent {

        /**
         * Creates a new despawn event.
         *
         * @param living the living that is about to despawn
         */
        public Despawn(EntityLiving living) {
            super(living);
        }

        @Override
        public EntityLiving getLiving() {
            return (EntityLiving) super.getLiving();
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        /**
         * Internal method for handling despawn events. Helps minimize patch
         * sizes.
         *
         * @param living the living
         * @return the despawn
         */
        public static Despawn handle(EntityLiving living) {
            Despawn event = new Despawn(living);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }

    /**
     * Fired when an entity is being hurt. Fired from
     * {@link EntityLivingBase#damageEntity(DamageSource,float)}.
     * 
     * This event is cancelable.
     */
    public static class Hurt extends LivingEvent {

        /**
         * The source of the damage.
         */
        private DamageSource source;

        /**
         * The amount of damage.
         */
        private float damage;

        /**
         * Creates a new hurt event.
         *
         * @param living the living
         * @param source the source
         * @param damage the damage
         */
        public Hurt(EntityLivingBase living, DamageSource source, float damage) {
            super(living);
            this.source = source;
            this.damage = damage;
        }

        /**
         * Gets the damage source.
         *
         * @return the source
         */
        public DamageSource getSource() {
            return source;
        }

        /**
         * Sets the damage source.
         *
         * @param source the new source
         */
        public void setSource(DamageSource source) {
            this.source = source;
        }

        /**
         * Gets the damage amount.
         *
         * @return the damage amount
         */
        public float getDamage() {
            return damage;
        }

        /**
         * Sets the damage amount.
         *
         * @param damage the new damage amount
         */
        public void setDamage(float damage) {
            this.damage = damage;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        /**
         * Internal method for handling hurt events. Helps minimize patch sizes.
         *
         * @param entity the entity
         * @param source the source
         * @param damage the damage
         * @return the hurt
         */
        public static Hurt handle(EntityLivingBase entity, DamageSource source, float damage) {
            Hurt event = new Hurt(entity, source, damage);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }

    /**
     * Fired when an entity is about to heal. Fired from
     * {@link EntityLivingBase#heal(float)}.
     * 
     * This event is cancelable.
     */
    public static class Heal extends LivingEvent {

        /**
         * The amount of health to heal.
         */
        private float healAmount;

        /**
         * Creates a new heal event.
         *
         * @param living the living
         * @param healAmount the amount of health to heal
         */
        public Heal(EntityLivingBase living, float healAmount) {
            super(living);
            this.healAmount = healAmount;
        }

        /**
         * Gets the amount of health to heal.
         *
         * @return the amount of health to heal
         */
        public float getHealAmount() {
            return healAmount;
        }

        /**
         * Sets the amount of health to heal.
         *
         * @param health the amount of health to heal
         */
        public void setHealAmount(float health) {
            this.healAmount = health;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        /**
         * Internal method for handling heal events. Helps minimize patch sizes.
         *
         * @param entity the entity
         * @param damage the damage
         * @return the heal
         */
        public static Heal handle(EntityLivingBase entity, float damage) {
            Heal event = new Heal(entity, damage);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }

    /**
     * Fired when an entity is about to despawn. Fired from
     * {@link EntityLivingBase#jump()}.
     * 
     * This event is cancelable.
     */
    public static class Jump extends LivingEvent {

        /**
         * The motion of the jump.
         */
        private float motion;

        /**
         * Creates a new jump event.
         *
         * @param living the living
         * @param motion the motion
         */
        public Jump(EntityLivingBase living, float motion) {
            super(living);
            this.motion = motion;
        }

        /**
         * Gets the motion of the jump.
         *
         * @return the motion
         */
        public float getMotion() {
            return motion;
        }

        /**
         * Sets the motion of the jump. This can be used to change the jump
         * height of the entity.
         *
         * @param motion the new motion
         */
        public void setMotion(float motion) {
            this.motion = motion;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }

        /**
         * Internal method for handling jump events. Helps minimize patch sizes.
         *
         * @param entity the entity
         * @param motion the motion
         * @return the jump
         */
        public static Jump handle(EntityLivingBase entity, float motion) {
            Jump event = new Jump(entity, motion);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }

    /**
     * Fired when an entity is falling. Fired from
     * {@link EntityLivingBase#fall(float, float)}.
     * 
     * This event is not cancelable.
     */
    public static class Fall extends LivingEvent {

        /**
         * The distance the entity is falling. This does not affect the actual
         * physics, but rather the following effects, i.e. damage taken.
         */
        private float distance;

        /**
         * The multiplier to the amount of damage taken when hitting the ground.
         */
        private float damageMultiplier;

        /**
         * Creates a new fall event.
         *
         * @param living the living
         * @param distance the distance
         * @param damageMultiplier the damage multiplier
         */
        public Fall(EntityLivingBase living, float distance, float damageMultiplier) {
            super(living);
            this.distance = distance;
            this.damageMultiplier = damageMultiplier;
        }

        /**
         * Gets the fall distance.
         *
         * @return the distance
         */
        public float getDistance() {
            return distance;
        }

        /**
         * Sets the fall distance.
         *
         * @param distance the new distance
         */
        public void setDistance(float distance) {
            this.distance = distance;
        }

        /**
         * Gets the damage multiplier.
         *
         * @return the damage multiplier
         */
        public float getDamageMultiplier() {
            return damageMultiplier;
        }

        /**
         * Sets the damage multiplier.
         *
         * @param damageMultiplier the new damage multiplier
         */
        public void setDamageMultiplier(float damageMultiplier) {
            this.damageMultiplier = damageMultiplier;
        }

        /**
         * Internal method for handling fall events. Helps minimize patch sizes.
         *
         * @param living the living
         * @param distance the distance
         * @param damageMultiplier the damage multiplier
         * @return the fall
         */
        public static Fall handle(EntityLivingBase living, float distance, float damageMultiplier) {
            Fall event = new Fall(living, distance, damageMultiplier);
            OpenModLoader.getEventBus().post(event);
            return event;
        }
    }
}