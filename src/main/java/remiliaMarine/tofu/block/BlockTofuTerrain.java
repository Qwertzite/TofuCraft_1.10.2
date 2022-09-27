package remiliaMarine.tofu.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;

public class BlockTofuTerrain extends BlockTofuBase {

	   public BlockTofuTerrain(SoundType sound)
	    {
	        super();
	        this.setSoundType(sound);
	    }

	    @Override
	    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
	    {
	        IBlockState plant = plantable.getPlant(world, pos.offset(direction));
	        if (plant.getBlock() == TcBlocks.tcSapling && plant.getValue(BlockTcSapling.VARIANT) == BlockTcSapling.EnumTcSapling.TOFU)
	        {
	            return true;
	        }
	        else
	        {
	            return super.canSustainPlant(state, world, pos, direction, plantable);
	        }
	    }

	    @Override
	    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	    {
	        return TcItems.tofuMomen;
	    }
	    
	    @Override
	    public ItemStack createScoopedBlockStack()
	    {
	        return new ItemStack(TcBlocks.tofuMomen);
	    }
	    
	    @Override
	    @Nullable
	    @Deprecated
	    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	    {
	        return new ItemStack(Item.getItemFromBlock(TcBlocks.tofuMomen));
	    }
}
