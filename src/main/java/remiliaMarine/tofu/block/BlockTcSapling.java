package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.world.gen.feature.WorldGenApricotTrees;
import remiliaMarine.tofu.world.gen.feature.WorldGenTcTreesBase;
import remiliaMarine.tofu.world.gen.feature.WorldGenTofuTrees;

public class BlockTcSapling extends BlockBush implements IGrowable, ITcBlockVariable{
	/** meta &3 */
	public static final PropertyEnum<EnumTcSapling> VARIANT = PropertyEnum.<EnumTcSapling>create("variant", EnumTcSapling.class);
	/** meta &8 */
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	
	public static final AxisAlignedBB TCSAPLING_AABB = new AxisAlignedBB(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
	
    public BlockTcSapling(int par1)
    {
        super();
        this.setSoundType(SoundType.GROUND);
    }
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TCSAPLING_AABB;
    }
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos);
    }
    
    @Override
    public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, @Nullable ItemStack stack)
    {
        if (stack.getItemDamage() == 1)
        {
            Block blockstate = worldIn.getBlockState(pos.down()).getBlock();
            return (worldIn.getLight(pos) >= 8 || worldIn.canBlockSeeSky(pos)) &&
                    (blockstate == TcBlocks.tofuMomen || blockstate == TcBlocks.tofuTerrain);
        }
        else
        {
            return this.canPlaceBlockOnSide(worldIn, pos, side);
        }
    }
    
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.getBlockState(pos).getValue(VARIANT) == EnumTcSapling.TOFU)
        {
            Block block = worldIn.getBlockState(pos.down()).getBlock();
            return (worldIn.getLight(pos) >= 8 || worldIn.canBlockSeeSky(pos)) &&
                    (block == TcBlocks.tofuMomen || block == TcBlocks.tofuTerrain);
        }
        else
        {
            return super.canBlockStay(worldIn, pos, state);
        }
    }
    
    /**
     * Return true if the block can sustain a Bush
     */
    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                int stage = worldIn.getBlockState(pos).getValue(STAGE);

                if (stage == 0)
                {
                    worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
                }
                else
                {
                    this.growTree(worldIn, pos, rand);
                }
            }
        }
    }
    
    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World worldIn, BlockPos pos, Random rand)
    {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        int var6 = worldIn.getBlockState(pos).getValue(VARIANT).getMeta();
        WorldGenTcTreesBase var7 = null;
        int var8 = 0;
        int var9 = 0;
        boolean flag = false;

        if (var6 != 1)
        {
            // Apricot
            var7 = new WorldGenApricotTrees(true);
            var7.setFruit(TcBlocks.tcLeaves.getDefaultState().withProperty(BlockTcLeaves.VARIANT, BlockTcLeaves.EnumLeaveTypes.APRICOT_F), 5);
        }
        else
        {
            // Tofu
            var7 = new WorldGenTofuTrees(true);
        }

        if (flag)
        {
            worldIn.setBlockToAir(pos.add(var8,  0, var9));
            worldIn.setBlockToAir(pos.add(var8+1,0, var9));
            worldIn.setBlockToAir(pos.add(var8,  0, var9+1));
            worldIn.setBlockToAir(pos.add(var8+1,0, var9+1));
        }
        else
        {
            worldIn.setBlockToAir(pos);
        }

        if (!var7.generate(worldIn, rand, pos.add(var8, 0, var9)))
        {
            if (flag)
            {
                worldIn.setBlockState(pos.add(var8,  0, var9  ), this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(var6)), 3);
                worldIn.setBlockState(pos.add(var8+1,0, var9  ), this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(var6)), 3);
                worldIn.setBlockState(pos.add(var8,  0, var9+1), this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(var6)), 3);
                worldIn.setBlockState(pos.add(var8+1,0, var9+1), this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(var6)), 3);
            }
            else
            {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(var6)), 3);
            }
        }
    }
    
    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World worldIn, BlockPos pos, IBlockState state)
    {
        return worldIn.getBlockState(pos).getBlock() == this &&
        		(worldIn.getBlockState(pos).getValue(VARIANT)) == state.getValue(VARIANT);
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.growTree(worldIn, pos, rand);
	}
	
    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return this.getDefaultState();
        return state;
    }
    
	@Override
	public String getVariantName(int meta) {
		return "." + EnumTcSapling.byMetadata(meta&7).getName();
	}
    
    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
	@Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumTcSapling)state.getValue(VARIANT)).getMeta();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (EnumTcSapling saplingtype : EnumTcSapling.getTypes())
        {
            list.add(new ItemStack(itemIn, 1, saplingtype.getMeta()));
        }
    }
	
	// ========  property  ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(VARIANT, EnumTcSapling.byMetadata(meta & 7)).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumTcSapling)state.getValue(VARIANT)).getMeta();
        i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, STAGE});
    }
	
	public static enum EnumTcSapling implements IStringSerializable {
		APRICOT(0, "apricot"),
		TOFU(1, "tofu");
		
        private static final EnumTcSapling[] META_LOOKUP = new EnumTcSapling[values().length];
		public int meta;
		public String variantName;
		
		
		EnumTcSapling(int meta, String name) {
			this.meta = meta;
			this.variantName = name;
		}
		
        public static EnumTcSapling byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }
        
        public static EnumTcSapling[] getTypes() {
        	return META_LOOKUP;
        }
        
        public static String[] getNameArray() {
        	String[] ret = new String[values().length];
        	for (EnumTcSapling variation : META_LOOKUP) {
        		ret[variation.getMeta()] = variation.getName();
        	}
        	return ret;
        }
		
		public int getMeta() {
			return this.meta;
		}
		
		@Override
		public String getName() {
			return this.variantName;
		}
		
        static
        {
            for (EnumTcSapling blocksandbag$enumtype : values())
            {
                META_LOOKUP[blocksandbag$enumtype.getMeta()] = blocksandbag$enumtype;
            }
        }
	}
}
