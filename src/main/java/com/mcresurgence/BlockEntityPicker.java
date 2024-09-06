package com.mcresurgence;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class BlockEntityPicker {
    private static final Random RANDOM = new Random();

    // Map groups of similar blocks together
    private static final Map<String, List<Block>> BLOCK_ENTITY_GROUPS = new HashMap<>();

    static {
        // Add groups of block entities
        BLOCK_ENTITY_GROUPS.put("Furnace", Collections.singletonList(Blocks.FURNACE));
        BLOCK_ENTITY_GROUPS.put("Chest", Collections.singletonList(Blocks.CHEST));
        BLOCK_ENTITY_GROUPS.put("Ender Chest", Collections.singletonList(Blocks.ENDER_CHEST));
        BLOCK_ENTITY_GROUPS.put("Hopper", Collections.singletonList(Blocks.HOPPER));
        BLOCK_ENTITY_GROUPS.put("Dropper", Collections.singletonList(Blocks.DROPPER));
        BLOCK_ENTITY_GROUPS.put("Dispenser", Collections.singletonList(Blocks.DISPENSER));
        BLOCK_ENTITY_GROUPS.put("Brewing Stand", Collections.singletonList(Blocks.BREWING_STAND));
        BLOCK_ENTITY_GROUPS.put("Beacon", Collections.singletonList(Blocks.BEACON));
        BLOCK_ENTITY_GROUPS.put("Mob Spawner", Collections.singletonList(Blocks.MOB_SPAWNER));
        BLOCK_ENTITY_GROUPS.put("Sign", Collections.singletonList(Blocks.STANDING_SIGN));
        BLOCK_ENTITY_GROUPS.put("Enchantment Table", Collections.singletonList(Blocks.ENCHANTING_TABLE));
        BLOCK_ENTITY_GROUPS.put("Bed", Collections.singletonList(Blocks.BED));
        BLOCK_ENTITY_GROUPS.put("End Gateway", Collections.singletonList(Blocks.END_GATEWAY));

        // Add all colors of shulker boxes to a single group
        BLOCK_ENTITY_GROUPS.put("Shulker Box", Arrays.asList(
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
        ));
    }


    /**
     * Picks a random block entity type and spawns it at the specified position.
     *
     * @param world The world where the block entity will be spawned.
     * @param pos   The position where the block entity will be placed.
     * @return The type of block entity spawned, for logging purposes.
     */
    public static String spawnRandomBlockEntity(World world, BlockPos pos) {
        // Select a random block type from the list
//        Block blockType = BLOCK_ENTITY_TYPES.get(RANDOM.nextInt(BLOCK_ENTITY_TYPES.size()));

        List<String> groupNames = new ArrayList<>(BLOCK_ENTITY_GROUPS.keySet());
        String selectedGroup = groupNames.get(RANDOM.nextInt(groupNames.size()));

        // Select a random block within the selected group
        List<Block> selectedBlocks = BLOCK_ENTITY_GROUPS.get(selectedGroup);
        Block blockType = selectedBlocks.get(RANDOM.nextInt(selectedBlocks.size()));

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
