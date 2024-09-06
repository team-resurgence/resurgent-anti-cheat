package com.mcresurgence;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockEntityPicker {
    private static final Random RANDOM = new Random();

    // List of possible block entities to spawn as fake entities
    private static final List<Block> BLOCK_ENTITY_TYPES = Arrays.asList(
            Blocks.FURNACE,
            Blocks.CHEST,
            Blocks.ENDER_CHEST,
            Blocks.HOPPER,
            Blocks.DROPPER,
            Blocks.DISPENSER,
            Blocks.BREWING_STAND,
            Blocks.BEACON,
            Blocks.MOB_SPAWNER,
            Blocks.STANDING_SIGN, // for TileEntitySign
            Blocks.ENCHANTING_TABLE,
            Blocks.BED,
            Blocks.END_GATEWAY,
//            Shulkers
            Blocks.WHITE_SHULKER_BOX,
            Blocks.ORANGE_SHULKER_BOX,
            Blocks.MAGENTA_SHULKER_BOX,
            Blocks.LIGHT_BLUE_SHULKER_BOX,
            Blocks.YELLOW_SHULKER_BOX,
            Blocks.LIME_SHULKER_BOX,
            Blocks.PINK_SHULKER_BOX,
            Blocks.GRAY_SHULKER_BOX,
            Blocks.SILVER_SHULKER_BOX,
            Blocks.CYAN_SHULKER_BOX,
            Blocks.PURPLE_SHULKER_BOX,
            Blocks.BLUE_SHULKER_BOX,
            Blocks.BROWN_SHULKER_BOX,
            Blocks.GREEN_SHULKER_BOX,
            Blocks.RED_SHULKER_BOX,
            Blocks.BLACK_SHULKER_BOX
    );

    /**
     * Picks a random block entity type and spawns it at the specified position.
     *
     * @param world The world where the block entity will be spawned.
     * @param pos   The position where the block entity will be placed.
     * @return The type of block entity spawned, for logging purposes.
     */
    public static String spawnRandomBlockEntity(World world, BlockPos pos) {
        // Select a random block type from the list
        Block blockType = BLOCK_ENTITY_TYPES.get(RANDOM.nextInt(BLOCK_ENTITY_TYPES.size()));

        // Set the block state at the specified position
        world.setBlockState(pos, blockType.getDefaultState());

        // Create and customize the tile entity at this position
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityFurnace) {
            ((TileEntityFurnace) tileEntity).clear();
        } else if (tileEntity instanceof TileEntityChest) {
            ((TileEntityChest) tileEntity).clear();
        }

        // Return the block type's registry name for logging
        try {
            return blockType.getRegistryName().toString();
        } catch (Exception e) {
            return "Unknown Entity";
        }
    }
}
