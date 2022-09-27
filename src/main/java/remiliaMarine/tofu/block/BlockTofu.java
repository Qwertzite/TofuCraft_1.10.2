package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.util.TofuBlockUtils;

public class BlockTofu extends BlockTofuBase {
    public static final PropertyInteger DRAIN_STEP = PropertyInteger.create("drainstep", 0, 7);
	
    private TofuMaterial tofuMaterial;
    private boolean isFragile = false;
    private boolean canDrain = false;
    private boolean canFreeze = false;
    /**drains when rand.nextInt(rate) == 0*/
    private int drainRate;

    public BlockTofu(TofuMaterial tofuMaterial, SoundType sound)
    {
        super(tofuMaterial);
        this.tofuMaterial = tofuMaterial;
        this.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
        this.setSoundType(sound);
    }
    
    @Override
    public BlockTofu  setSoundType(SoundType sound) {
    	super.setSoundType(sound);
		return this;
    	
    }
    
    public BlockTofu setFragile()
    {
        isFragile = true;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setDrain(int rate)
    {
        this.canDrain = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setFreeze(int rate)
    {
        this.canFreeze = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    public boolean canDrain()
    {
        return this.canDrain;
    }

    public int getDrainRate()
    {
        return drainRate;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random par2Random, int par3)
    {
        return tofuMaterial.getItem();
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, BlockPos pos, Entity par5Entity, float par6)
    {
        if (isFragile)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        if (isFragile || canDrain)
        {
           if (isUnderWeight(worldIn, pos))
           {
                if (isFragile)
                {
                    dropBlockAsItemWithChance(worldIn, pos, state, 0.4f, 0);
                    worldIn.setBlockToAir(pos);
                }
                else if (canDrain)
                {
                    this.drainOneStep(worldIn, pos, rand);
                }
            }
        }
        if (canFreeze)
        {
            if (isValidPlaceForDriedTofu(worldIn, pos))
            {
            	IBlockState myState = worldIn.getBlockState(pos);
                int freezeStep = myState.getValue(DRAIN_STEP);
                
                if (freezeStep < 7 && rand.nextInt((drainRate)) == 0)
                {
                    ++freezeStep;
                    worldIn.setBlockState(pos, this.withFreezeStep(freezeStep), 2);
                }
                else if (freezeStep == 7)
                {
                    Block newBlock = TcBlocks.tofuDried;
                    worldIn.setBlockState(pos, newBlock.getDefaultState(), 2);
                }
            }
        }
    }

    public boolean isUnderWeight(World world, BlockPos pos)
    {
        IBlockState weightBlock = world.getBlockState(pos.up());
        IBlockState baseBlock = world.getBlockState(pos.down());

        boolean isWeightValid = weightBlock != null
                && (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON);

        float baseHardness = baseBlock.getBlockHardness(world, pos.down());
        boolean isBaseValid = baseBlock.isNormalCube() &&
                (baseBlock.getMaterial() == Material.ROCK || baseBlock.getMaterial() == Material.IRON || baseHardness >= 1.0F || baseHardness < 0.0F);

        return isWeightValid && isBaseValid;
    }

    public void drainOneStep(World par1World, BlockPos pos, Random par5Random)
    {
    	IBlockState state = par1World.getBlockState(pos);
        int drainStep = state.getValue(DRAIN_STEP);

        if (drainStep < 7 && par5Random.nextInt(drainRate) == 0)
        {
            ++drainStep;
            //ModLog.debug(drainStep);
            par1World.setBlockState(pos, this.withFreezeStep(drainStep), 2);
        }
        else if (drainStep == 7 && par5Random.nextInt(2 * drainRate) == 0)
        {
            IBlockState newBlock;
            if (this == TcBlocks.tofuMomen)
            {
                newBlock = TcBlocks.tofuIshi.getDefaultState();
            }
            else if (this == TcBlocks.tofuIshi)
            {
                newBlock = TcBlocks.tofuMetal.getDefaultState();
            }
            else
            {
                newBlock = this.getDefaultState();
            }

            par1World.setBlockState(pos, newBlock);
        }

    }

//    @SideOnly(Side.CLIENT)
//
//    /**
//     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
//     */
//    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
//    {
//        for (int var4 = 0; var4 < 3; ++var4)
//        {
//            par3List.add(new ItemStack(par1, 1, var4));
//        }
//    }

    public static boolean isValidPlaceForDriedTofu(World world, BlockPos pos)
    {
        return world.getBiome(pos).getFloatTemperature(pos) < 0.15F
                && world.getHeightmapHeight(pos.getX(), pos.getZ()) - 10 < pos.getY()
                && world.isAirBlock(pos.up());
    }
    
    public IBlockState withFreezeStep(int step) {
    	return this.getDefaultState().withProperty(DRAIN_STEP, Integer.valueOf(step));
    }
    
    @SuppressWarnings("static-access")
	public int getFreezeStep(IBlockState state) {
    	return state.getValue(this.DRAIN_STEP);
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withFreezeStep(meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return this.getFreezeStep(state);
    }
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {DRAIN_STEP});
    }
    
}
