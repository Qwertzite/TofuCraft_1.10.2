package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemFoodSet.EnumSetFood;
import remiliaMarine.tofu.util.ModLog;

public class BlockSprouts extends BlockCrops {
    
    private static final AxisAlignedBB[] SPROUTS_AABB = new AxisAlignedBB[] {
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D)};

	public BlockSprouts()
    {
        super();
    }
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SPROUTS_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
	
    /**
     * Ticks the block if it's been scheduled
     */
	@Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(par1World, pos, state);

        int i = this.getAge(state);

        if (this.getAge(state) < 7)
        {
            float f = getGrowthRate(par1World, pos);
            ModLog.debug("rate: %.1f", f);

            if (rand.nextInt((int)(25.0F / f) + 1) == 0)
            {
                i++;
                par1World.setBlockState(pos, this.withAge(i), 2);
                //ModLog.debug("growth: %d", l);
            }
        }
    }
    
    /**
     * Gets the growth rate for the crop. Setup to encourage rows by halving growth rate if there is diagonals, crops on
     * different sides that aren't opposing, and by adding growth for every crop next to this one (and for crop below
     * this one). Args: x, y, z
     */
    private float getGrowthRate(World world, BlockPos pos)
    {
        float f = 1.0F;

        Block j3 = world.getBlockState(pos.down()).getBlock();

        if (j3 == Blocks.WOOL)
        {
            f = 3.0F;
            
            boolean hasWater = (
                    world.getBlockState(pos.down().north()).getMaterial() == Material.WATER ||
                    world.getBlockState(pos.down().south()).getMaterial() == Material.WATER ||
                    world.getBlockState(pos.down().east()).getMaterial() == Material.WATER ||
                    world.getBlockState(pos.down().west()).getMaterial() == Material.WATER);

            if (hasWater)
            {
                f = 25.0F;
            }
        }

        return f;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World par1World, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            Block soil = par1World.getBlockState(pos.down()).getBlock();
            return (par1World.getLight(pos) < 10) &&
                   (soil != null && soil == Blocks.WOOL);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return this.getAge(state) >= 7 ? 2 : 1;
    }
    
    @Override
    protected Item getSeed()
    {
        return TcItems.soybeans;
    }

    @Override
    protected Item getCrop()
    {
        return TcItems.foodSet;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.getAge(state) >= 2 ? this.getCrop() : this.getSeed();
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getAge(state) >= 2 ? EnumSetFood.SPROUTS.getId() : 0;
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }
    
    /**
     * Return true if the block can sustain a Bush
     */
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.WOOL;
    }
}
