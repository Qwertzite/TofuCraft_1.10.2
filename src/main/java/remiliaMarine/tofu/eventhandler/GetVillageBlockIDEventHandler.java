package remiliaMarine.tofu.eventhandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.block.BlockSaltFurnace;
import remiliaMarine.tofu.block.BlockSoybean;
import remiliaMarine.tofu.block.BlockTofuFarmland;
import remiliaMarine.tofu.block.BlockTofuGrilled;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.world.biome.BiomeTofu;

public class GetVillageBlockIDEventHandler {
	@SubscribeEvent
	public void onVillageBlockID(BiomeEvent.GetVillageBlockID event)
	{
		if(event.getBiome() instanceof BiomeTofu)
		{
			IBlockState originalState = event.getOriginal();
			Block originalBlock = originalState.getBlock();

            if (originalState.getMaterial() == TcMaterial.TOFU)
            {
                event.setReplacement(originalState);
            }
            else if (originalBlock == Blocks.OAK_STAIRS)
            {
            	event.setReplacement(TcBlocks.tofuStairsMomen.getDefaultState()
            			.withProperty(BlockStairs.FACING, originalState.getValue(BlockStairs.FACING))
            			.withProperty(BlockStairs.HALF, originalState.getValue(BlockStairs.HALF)));
            }
            else if (originalBlock == Blocks.STONE_STAIRS)
            {
            	event.setReplacement(TcBlocks.tofuStairsMomen.getDefaultState()
            			.withProperty(BlockStairs.FACING, originalState.getValue(BlockStairs.FACING))
            			.withProperty(BlockStairs.HALF, originalState.getValue(BlockStairs.HALF)));
            }
            else if (originalBlock == Blocks.GRAVEL)
            {
            	event.setReplacement(TcBlocks.tofuGrilled.getDefaultState()
            			.withProperty(BlockTofuGrilled.FACING, EnumFacing.UP));
            }
            else if (originalBlock == Blocks.FURNACE)
            {
            	event.setReplacement(TcBlocks.saltFurnaceIdle.getDefaultState()
            			.withProperty(BlockSaltFurnace.FACING, originalState.getValue(BlockFurnace.FACING)));
            }
            else if (originalBlock == Blocks.STONE_SLAB)
            {
                event.setReplacement(TcBlocks.tofuSingleSlab1.getDefaultState());
            }
            else if (originalBlock == Blocks.DOUBLE_STONE_SLAB)
            {
                event.setReplacement(TcBlocks.tofuMomen.getDefaultState());
            }
            else if (originalBlock == Blocks.FARMLAND)
            {
                event.setReplacement(TcBlocks.tofuFarmland.getDefaultState()
                		.withProperty(BlockTofuFarmland.MOISTURE, originalState.getValue(BlockFarmland.MOISTURE)));
            }
            else if (originalBlock instanceof BlockFence)
            {
                event.setReplacement(TcBlocks.tofuWalls.get(TofuMaterial.MOMEN).getDefaultState());
            }
            else if (originalBlock == Blocks.CHEST)
            {
                event.setReplacement(Blocks.AIR.getDefaultState()); // not working...
            }
            else if (originalBlock instanceof BlockPressurePlate)
            {
                event.setReplacement(Blocks.AIR.getDefaultState());
            }
            else if (originalBlock instanceof BlockPane)
            {
                event.setReplacement(Blocks.AIR.getDefaultState());
            }
            else if (originalBlock instanceof BlockCrops)
            {
                event.setReplacement(TcBlocks.soybean.getDefaultState()
                		.withProperty(BlockSoybean.AGE, originalState.getValue(BlockCrops.AGE)));
            }
            else if (originalState.getMaterial() == Material.WOOD)
            {
                event.setReplacement(TcBlocks.tofuMomen.getDefaultState());
            }
            else if (originalState.getMaterial() == Material.ROCK)
            {
                event.setReplacement(TcBlocks.tofuMomen.getDefaultState());
            }
            else if (originalState.getMaterial() == Material.GROUND)
            {
                event.setReplacement(TcBlocks.tofuDried.getDefaultState());
            }
            else if (originalState.getMaterial().isLiquid())
            {
                event.setReplacement(TcBlocks.soymilkStill.getDefaultState());
            }
            else if (!originalState.getMaterial().isOpaque() || !originalState.getMaterial().isSolid())
            {
                event.setReplacement(Blocks.AIR.getDefaultState());
            }
            else
            {
                event.setReplacement(TcBlocks.tofuMomen.getDefaultState());
            }

            ModLog.debug("Village block replaced: %s -> %s", originalBlock.getLocalizedName(), event.getReplacement().getBlock().getLocalizedName());
            event.setResult(Event.Result.DENY);
		}
	}

//    @SubscribeEvent
//    public void onVillageBlockMeta(BiomeEvent.GetVillageBlockMeta event)
//    {
//        if(event.biome instanceof BiomeTofu)
//        {
//            if (event.original == Blocks.stone_slab)
//            {
//                event.replacement = 1; // momen tofu slab
//            }
//        }
//    }
}
