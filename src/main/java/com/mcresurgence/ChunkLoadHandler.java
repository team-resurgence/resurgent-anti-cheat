package com.mcresurgence;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ChunkLoadHandler {
    private static final Random RANDOM = new Random();

    // Adjustable constants for the number of fake entities to spawn
    private static final int MIN_ENTITIES_TO_SPAWN = 5; // Minimum number of entities
    private static final int MAX_ENTITIES_TO_SPAWN = 10; // Maximum number of entities

    private static final int MIN_BLOCK_ENTITIES_TO_SPAWN = 6; // Minimum number of entities
    private static final int MAX_BLOCK_ENTITIES_TO_SPAWN = 20; // Maximum number of entities

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        World world = (World) event.getWorld();
        if (world.isRemote) {
            return; // Only run on the server side
        }

        int chunkX = event.getChunk().x;
        int chunkZ = event.getChunk().z;

        ResurgentAntiCheatMod.modLogger.info("Chunk loaded: [" + chunkX + ", " + chunkZ + "]");

        // Get the bottom Y level for spawning entities
        int bottomY = 0; // Typically bedrock level
        int entitiesToSpawn = RANDOM.nextInt(MAX_ENTITIES_TO_SPAWN - MIN_ENTITIES_TO_SPAWN + 1) + MIN_ENTITIES_TO_SPAWN;
        int blockEntitiesToSpawn = RANDOM.nextInt(MAX_BLOCK_ENTITIES_TO_SPAWN - MIN_BLOCK_ENTITIES_TO_SPAWN + 1) + MIN_BLOCK_ENTITIES_TO_SPAWN;

        ResurgentAntiCheatMod.modLogger.info("Attempting to spawn " + entitiesToSpawn + " fake entities in chunk [" + chunkX + ", " + chunkZ + "]");
        ResurgentAntiCheatMod.modLogger.info("Attempting to spawn " + blockEntitiesToSpawn + " fake block entities in chunk [" + chunkX + ", " + chunkZ + "]");

        StringBuilder livingEntityLog = new StringBuilder("Spawned fake living entities in chunk [" + chunkX + ", " + chunkZ + "]: ");

        for (int i = 0; i < entitiesToSpawn; i++) {
            EntityLiving fakeEntity = EntityPicker.pickRandomEntity(world);
            if (fakeEntity != null) {
                BlockPos randomPos = new BlockPos(
                        chunkX * 16 + RANDOM.nextInt(16),
                        bottomY,
                        chunkZ * 16 + RANDOM.nextInt(16)
                );
                fakeEntity.setPosition(randomPos.getX(), randomPos.getY(), randomPos.getZ());
                world.spawnEntity(fakeEntity);

                livingEntityLog.append(fakeEntity.getName()).append(", ");
            } else {
                ResurgentAntiCheatMod.modLogger.error("Failed to create a fake living entity for chunk [" + chunkX + ", " + chunkZ + "]");
            }
        }

        ResurgentAntiCheatMod.modLogger.info(livingEntityLog.toString());

        StringBuilder blockEntityLog = new StringBuilder("Spawned fake block entities in chunk [" + chunkX + ", " + chunkZ + "]: ");

        // Spawn fake block entities
        for (int i = 0; i < blockEntitiesToSpawn; i++) {
            BlockPos randomPos = new BlockPos(
                    chunkX * 16 + RANDOM.nextInt(16),
                    -1, // Y position below bedrock
                    chunkZ * 16 + RANDOM.nextInt(16)
            );
            String entityType = BlockEntityPicker.spawnRandomBlockEntity(world, randomPos);
            blockEntityLog.append(entityType).append(", ");
        }

        // Log all spawned block entities at once
        ResurgentAntiCheatMod.modLogger.info(blockEntityLog.toString());
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        World world = (World) event.getWorld();
        if (world.isRemote) {
            return; // Only run on the server side
        }

        int chunkX = event.getChunk().x;
        int chunkZ = event.getChunk().z;

        ResurgentAntiCheatMod.modLogger.info("Chunk unloaded: [" + chunkX + ", " + chunkZ + "]");

        // Check for fake entities and attempt to despawn them
        for (ClassInheritanceMultiMap<Entity> entityList : event.getChunk().getEntityLists()) {
            for (Entity entity : entityList) {
                if (isFakeEntity(entity)) {
                    entity.setDead(); // Mark entity for removal
                    ResurgentAntiCheatMod.modLogger.info("Despawned fake entity: " + entity.getName() + " from chunk [" + chunkX + ", " + chunkZ + "]");
                }
            }
        }

        for (TileEntity tileEntity : event.getChunk().getTileEntityMap().values()) {
            BlockPos pos = tileEntity.getPos();
            if (pos.getY() < 0 && (tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest)) {
                world.removeTileEntity(pos); // Remove the fake block entity
                ResurgentAntiCheatMod.modLogger.info("Removed fake block entity from chunk [" + chunkX + ", " + chunkZ + "]");
            }
        }
    }

    // Helper method to determine if an entity is a fake entity
    private boolean isFakeEntity(Entity entity) {
        if (!(entity instanceof EntityLiving)) return false;

        // Check if this entity is one of the types we created as fake entities
        return EntityPicker.isFakeEntityType(entity.getClass());
    }
}
