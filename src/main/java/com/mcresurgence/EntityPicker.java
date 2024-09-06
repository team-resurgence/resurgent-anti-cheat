package com.mcresurgence;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EntityPicker {
    private static final Random RANDOM = new Random();

    // List of possible entity types for the fake entities
    private static final List<Class<? extends EntityLiving>> ENTITY_TYPES = Arrays.asList(
            // Hostile Mobs
            EntityZombie.class,
            EntitySkeleton.class,
            EntityCreeper.class,
            EntitySpider.class,
            EntityCaveSpider.class,
            EntityEnderman.class,
            EntityWitch.class,
            EntityBlaze.class,
            EntityMagmaCube.class,
            EntitySlime.class,
            EntitySilverfish.class,
            EntityPigZombie.class,
            EntityWitherSkeleton.class,

            // Passive and Neutral Mobs
            EntityPig.class,
            EntityCow.class,
            EntitySheep.class,
            EntityChicken.class,
            EntityHorse.class,
            EntityDonkey.class,
            EntityMule.class,
            EntityLlama.class,
            EntityBat.class,
            EntityVillager.class,
            EntityMooshroom.class,
            EntityPolarBear.class,

            // Miscellaneous Entities
            EntityIronGolem.class,
            EntitySnowman.class,

            // NPCs
            EntityVillager.class
    );

    /**
     * Picks a random entity type from the list and creates an instance of it.
     *
     * @param world The world where the entity will be spawned.
     * @return A new instance of the chosen entity type.
     */
    public static EntityLiving pickRandomEntity(World world) {
        // Select a random entity class from the list
        Class<? extends EntityLiving> entityClass = ENTITY_TYPES.get(RANDOM.nextInt(ENTITY_TYPES.size()));
        try {
            // Create a new instance of the chosen entity class
            EntityLiving entity = entityClass.getConstructor(World.class).newInstance(world);

            // Set entity properties to make it invisible, non-interactable, etc.
            entity.setInvisible(true);
            entity.setNoAI(true); // No AI behavior
            entity.setEntityInvulnerable(true); // Cannot die
            entity.setSilent(true); // Prevents any sound from the entity

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            ResurgentAntiCheatMod.modLogger.error("Failed to create fake entity of type " + entityClass.getSimpleName());
            return null; // Return null if there is an error creating the entity
        }
    }

    /**
     * Checks if the given entity class is one of the fake entity types.
     *
     * @param entityClass The class of the entity to check.
     * @return True if the entity class is in the list of fake entity types, false otherwise.
     */
    public static boolean isFakeEntityType(Class<?> entityClass) {
        return ENTITY_TYPES.contains(entityClass);
    }
}
