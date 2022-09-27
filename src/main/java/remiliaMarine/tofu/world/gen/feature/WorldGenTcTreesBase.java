package remiliaMarine.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTcTreesBase extends WorldGenAbstractTree {

    /** The minimum height of a generated tree. */
    protected final int minTreeHeight;

    /** True if this tree should grow Vines. */
    protected final boolean vinesGrow;

    protected final IBlockState blockWood;
    protected final IBlockState stateLeaves;

    /** The metadata value of the leaves to use in tree generation. */
    protected IBlockState stateFruit = null;
    protected int fruitChance = 0;
    
    public WorldGenTcTreesBase(boolean notify, int minheight, IBlockState blockWood, IBlockState blockLeaves, boolean vine)
    {
        super(notify);
        this.minTreeHeight = minheight;
        this.blockWood = blockWood;
        this.stateLeaves = blockLeaves;
        this.vinesGrow = vine;
    }
    
    public void setFruit(IBlockState metaFruit, int chance)
    {
        this.stateFruit = metaFruit;
        this.fruitChance = chance;
    }

    public boolean isSoil(IBlockState state, World world, BlockPos pos)
    {
        return state.getBlock().canSustainPlant(state, world, pos.down(), EnumFacing.UP, (BlockSapling) Blocks.SAPLING);
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		// var6
        int treeHeight = rand.nextInt(3) + this.minTreeHeight;
        //var7
        boolean flag = true;
        
        if (position.getY() >= 1 && position.getY() + treeHeight + 1 <= worldIn.getHeight())
        {
            for (int i = position.getY(); i <= position.getY() + 1 + treeHeight; i++)
            {
                byte var9 = 1;

                if (i == position.getY())
                {
                    var9 = 0;
                }

                if (i >= position.getY() + 1 + treeHeight - 2)
                {
                    var9 = 2;
                }

                BlockPos.MutableBlockPos blockposmutable = new BlockPos.MutableBlockPos();
                
                for (int j = position.getX() - var9; j <= position.getX() + var9 && flag; ++j)
                {
                    for (int k = position.getZ() - var9; k <= position.getZ() + var9 && flag; ++k)
                    {
                        if (i >= 0 && i < worldIn.getHeight())
                        {
                            if (!this.isReplaceable(worldIn, blockposmutable.setPos(j, i, k)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                IBlockState blockstate2 = worldIn.getBlockState(position.down());

                boolean isSoil = isSoil(blockstate2, worldIn, position);
                if (isSoil && position.getY() < 256 - treeHeight - 1)
                {
                    blockstate2.getBlock().onPlantGrow(blockstate2, worldIn, position.down(), position);

                    this.putLeaves(position.getX(), position.getY(), position.getZ(), treeHeight, worldIn, rand);

                    for (int k = 0; k < treeHeight; ++k)
                    {
                    	BlockPos posUpK = position.up(k); 
                    	IBlockState state = worldIn.getBlockState(posUpK);
                        Block block = state.getBlock();

                        if (block.isAir(state, worldIn, posUpK) || block.isLeaves(state, worldIn, posUpK))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, posUpK, blockWood);

                            if (this.vinesGrow && k > 0)
                            {
                            	{
                            		BlockPos posUpKdX = posUpK.add(-1, 0, 0);//west
                                	if (rand.nextInt(3) > 0 && worldIn.isAirBlock(posUpKdX))
                                	{
                                    	this.growVines(worldIn, posUpKdX, BlockVine.EAST);
                                	}
                            	}
                            	{
                            		BlockPos posUpKaX = posUpK.add(1, 0, 0);//east
                            		if (rand.nextInt(3) > 0 && worldIn.isAirBlock(posUpKaX))
                                	{
                                		this.growVines(worldIn, posUpKaX, BlockVine.WEST);
                                	}
                            	}
                            	{
                            		BlockPos posUpKdZ = posUpK.add(0, 0, -1);//north
                            		if (rand.nextInt(3) > 0 && worldIn.isAirBlock(posUpKdZ))
                                	{
                                    	this.growVines(worldIn, posUpKdZ, BlockVine.SOUTH);
                                	}
                            	}
                            	{
                            		BlockPos posUpKaZ = posUpK.add(0, 0, 1);//south
                            		if (rand.nextInt(3) > 0 && worldIn.isAirBlock(posUpKaZ))
                                	{
                                    	this.growVines(worldIn, posUpKaZ, BlockVine.NORTH);
                                	}
                            	}
                            }
                        }
                    }

                    if (this.vinesGrow)
                    {
                        for (int k2 = position.getY() - 3 + treeHeight; k2 <= position.getY() + treeHeight; ++k2)
                        {
                            int i3 = k2 - (position.getY() + treeHeight);
                            int l1 = 2 - i3 / 2;
                            BlockPos.MutableBlockPos blockposmutable = new BlockPos.MutableBlockPos();

                            for (int i2 = position.getX() - l1; i2 <= position.getX() + l1; ++i2)
                            {
                                for (int j2 = position.getZ() - l1; j2 <= position.getZ() + l1; ++j2)
                                {
                                	blockposmutable.setPos(i2, k2, j2);
                                	
                                	IBlockState state = worldIn.getBlockState(blockposmutable);
                                    if (state.getBlock().isLeaves(state, worldIn, blockposmutable))
                                    {
                                        BlockPos blockposW = blockposmutable.west();
                                        BlockPos blockposE = blockposmutable.east();
                                        BlockPos blockposN = blockposmutable.north();
                                        BlockPos blockposS = blockposmutable.south();
                                        
                                        if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockposW))
                                        {
                                            this.growVines(worldIn, blockposW, BlockVine.EAST);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockposE)) 
                                        {
                                            this.growVines(worldIn, blockposE, BlockVine.WEST);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockposN))
                                        {
                                            this.growVines(worldIn, blockposN, BlockVine.SOUTH);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockposS))
                                        {
                                            this.growVines(worldIn, blockposS, BlockVine.NORTH);
                                        }
                                    }
                                }
                            }
                        }

//                        if (par2Random.nextInt(5) == 0 && var6 > 5)
//                        {
//                            for (var11 = 0; var11 < 2; ++var11)
//                            {
//                                for (var12 = 0; var12 < 4; ++var12)
//                                {
//                                    if (par2Random.nextInt(4 - var11) == 0)
//                                    {
//                                        var13 = par2Random.nextInt(3);
//                                        this.setBlockAndMetadata(par1World, par3 + Direction.offsetX[Direction.footInvisibleFaceRemap[var12]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.footInvisibleFaceRemap[var12]], Block.cocoaPlant, var13 << 2 | var12);
//                                    }
//                                }
//                            }
//                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
        
        
        
	}
	
    protected void putLeaves(int ox, int oy, int oz, int height, World worldIn, Random par2Random)
    {
        byte zero = 0;
        int var9 = 3;
        int leavesY, blocksHighFromTop, radius, leavesX, relX;
        for (leavesY = oy - var9 + height; leavesY <= oy + height; ++leavesY)
        {
            blocksHighFromTop = leavesY - (oy + height);
            radius = zero + 1 - blocksHighFromTop / 2;

            for (leavesX = ox - radius; leavesX <= ox + radius; ++leavesX)
            {
                relX = leavesX - ox;

                for (int leavesZ = oz - radius; leavesZ <= oz + radius; ++leavesZ)
                {
                    int relZ = leavesZ - oz;
                    
                    BlockPos leavesPos = new BlockPos(leavesX, leavesY, leavesZ);
                    IBlockState state = worldIn.getBlockState(leavesPos);
                    Block block = state.getBlock();

                    if ((Math.abs(relX) != radius || Math.abs(relZ) != radius || par2Random.nextInt(2) != 0 && blocksHighFromTop != 0) &&
                        (block == null || block.canBeReplacedByLeaves(state, worldIn, leavesPos)))
                    {
                        IBlockState metadata;
                        if (fruitChance > 0 && par2Random.nextInt(fruitChance) == 0)
                        {
                            metadata = this.stateFruit;
                        }
                        else
                        {
                            metadata = this.stateLeaves;
                        }
                        this.setBlockAndNotifyAdequately(worldIn, leavesPos, metadata);
                    }
                }
            }
        }
    }
	
	
	
	
	
	
	
    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-facing
     */
    private void growVines(World par1World, BlockPos pos, PropertyBool property)
    {
        this.setBlockAndNotifyAdequately(par1World, pos, Blocks.VINE.getDefaultState().withProperty(property, true));
        int var6 = 4;

        while (true)
        {
            pos = pos.down();

            if (par1World.isAirBlock(pos) || var6 <= 0)
            {
                return;
            }

            this.setBlockAndNotifyAdequately(par1World, pos, Blocks.VINE.getDefaultState().withProperty(property, true));
            --var6;
        }
    }

}
