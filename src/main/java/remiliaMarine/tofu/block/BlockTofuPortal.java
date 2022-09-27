package remiliaMarine.tofu.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.cache.LoadingCache;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.achievement.TcAchievementMgr.Key;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.data.PortalTripInfo;
import remiliaMarine.tofu.dimension.DimensionTeleportation;
import remiliaMarine.tofu.entity.ParticleTofuPortal;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketDimTrip;

public class BlockTofuPortal extends BlockBreakable {
	
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});
    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    public static final int[][] field_150001_a = new int[][] {new int[0], {3, 1}, {2, 0}};

    private final DimensionTeleportation teleportHandler = new DimensionTeleportation();

    public BlockTofuPortal()
    {
        super(Material.PORTAL, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
        this.setTickRandomly(true);
        this.setSoundType(SoundType.GLASS);
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId())
        {
            int var6;

            for (var6 = pos.getY(); !worldIn.getBlockState(pos.down()).isSideSolid(worldIn, new BlockPos(pos.down()), net.minecraft.util.EnumFacing.UP) && var6 > 0; --var6)
            {
                ;
            }
            BlockPos posTarg = new BlockPos(pos.getX(), var6+1, pos.getZ());
            if (var6 > 0 && !worldIn.getBlockState(posTarg).isNormalCube())
            {
                Entity var7 = ItemMonsterPlacer.spawnCreature(worldIn, TcEntity.IdTofuSlime, pos.getX() + 0.5D, var6 + 1.1D, pos.getZ() + 0.5D);

                if (var7 != null)
                {
                    var7.timeUntilPortal = var7.getPortalCooldown();
                }
            }
        }
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    
    public static int getMetaForAxis(EnumFacing.Axis axis)
    {
        return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing.Axis)state.getValue(AXIS))
        {
            case X:
                return X_AABB;
            case Y:
            default:
                return Y_AABB;
            case Z:
                return Z_AABB;
        }
    }
    
    /**
     * Checks to see if this location is valid to create a portal and will return True if it is. Args: world, x, y, z
     */
    public boolean tryToCreatePortal(World worldIn, BlockPos pos)
    {
        BlockTofuPortal.Size blockportal$size = new BlockTofuPortal.Size(worldIn, pos, EnumFacing.Axis.X);

        if (blockportal$size.isValid() && blockportal$size.portalBlockCount == 0)
        {
            blockportal$size.placePortalBlocks();
            return true;
        }
        else
        {
            BlockTofuPortal.Size blockportal$size1 = new BlockTofuPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

            if (blockportal$size1.isValid() && blockportal$size1.portalBlockCount == 0)
            {
                blockportal$size1.placePortalBlocks();
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know register neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
    	EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue(AXIS);

    	if (enumfacing$axis == EnumFacing.Axis.X)
        {
            BlockTofuPortal.Size blockportal$size = new BlockTofuPortal.Size(worldIn, pos, EnumFacing.Axis.X);
            
            if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if (enumfacing$axis == EnumFacing.Axis.Z)
        {
            BlockTofuPortal.Size blockportal$size1 = new BlockTofuPortal.Size(worldIn, pos, EnumFacing.Axis.Z);
            
            if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        pos = pos.offset(side);
        EnumFacing.Axis enumfacing$axis = null;

        if (blockState.getBlock() == this)
        {
            enumfacing$axis = (EnumFacing.Axis)blockState.getValue(AXIS);

            if (enumfacing$axis == null)
            {
                return false;
            }

            if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
            {
                return false;
            }

            if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
            {
                return false;
            }
        }

        boolean flag = blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2)).getBlock() != this;
        boolean flag1 = blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2)).getBlock() != this;
        boolean flag2 = blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2)).getBlock() != this;
        boolean flag3 = blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2)).getBlock() != this;
        boolean flag4 = flag || flag1 || enumfacing$axis == EnumFacing.Axis.X;
        boolean flag5 = flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z;
        return flag4 && side == EnumFacing.WEST ? true : (flag4 && side == EnumFacing.EAST ? true : (flag5 && side == EnumFacing.NORTH ? true : flag5 && side == EnumFacing.SOUTH));
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    	
        if (entityIn.getRidingEntity() == null && !entityIn.isBeingRidden())
        {
        	

            if (!worldIn.isRemote && entityIn.worldObj instanceof WorldServer)
            {
                byte dest = worldIn.provider.getDimension() == TofuCraftCore.TOFU_DIMENSION.getId() ? (byte)0 : (byte)TofuCraftCore.TOFU_DIMENSION.getId() ;//TofuCraftCore.TOFU_DIMENSION.getId()
                
                BlockTofuPortal.setPortalWithNoSideEffect(entityIn, pos);
                
                if (entityIn instanceof EntityPlayerMP)
                {
                	
                    EntityPlayerMP playermp = (EntityPlayerMP)entityIn;
                    EntityInfo pinfo = EntityInfo.instance();
                    if (!pinfo.doesDataExist(playermp.getEntityId(), DataType.TicksPortalCooldown))
                    {
                    	
                        this.travelToDimension(dest, playermp);

                        // Make a sound on client side
                        PacketDispatcher.packet(new PacketDimTrip()).sendToPlayer(playermp);

                        TcAchievementMgr.achieve(playermp, Key.tofuWorld);
                    }
                    pinfo.set(playermp.getEntityId(), DataType.TicksPortalCooldown, this.getNewTripInfo(playermp.dimension));
                }
                else
                {
                    EntityInfo pinfo = EntityInfo.instance();
                    if (!pinfo.doesDataExist(entityIn.getEntityId(), DataType.TicksPortalCooldown))
                    {
                        Entity traveledEntity = this.travelToDimension(dest, entityIn);
                        if (traveledEntity != null)
                        {
                            pinfo.set(traveledEntity.getEntityId(), DataType.TicksPortalCooldown, this.getNewTripInfo(traveledEntity.dimension));
                        }
                    }
                    else
                    {
                        PortalTripInfo info = pinfo.get(entityIn.getEntityId(), DataType.TicksPortalCooldown);
                        info.ticksCooldown = 0;
                    }
                }
            }
        }
    }
    
    private PortalTripInfo getNewTripInfo(int tripTo)
    {
        PortalTripInfo info = new PortalTripInfo();
        info.ticksCooldown = 0;
        info.dimensionIdTripTo = tripTo;
        return info;
    }
    
    public static void setPortalWithNoSideEffect(Entity entityIn, BlockPos pos) {
        entityIn.setPortal(pos);
        ReflectionHelper.setPrivateValue(Entity.class, entityIn, false, "field_71087_bX", "inPortal");
    }

    @Nullable
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(100) == 0)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            ParticleTofuPortal fx = new ParticleTofuPortal(worldIn, d0, d1, d2, d3, d4, d5);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
//            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
        }
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
    }
    
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((EnumFacing.Axis)state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, EnumFacing.Axis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumFacing.Axis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AXIS});
    }
    
    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int par1, EntityPlayerMP player)
    {
        teleportHandler.transferPlayerToDimension(player, par1);
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public Entity travelToDimension(int par1, Entity entity)
    {
        return teleportHandler.transferEntityToDimension(entity, par1);
    }
    
    public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_)
    {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
        BlockTofuPortal.Size blockportal$size = new BlockTofuPortal.Size(worldIn, p_181089_2_, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

        if (!blockportal$size.isValid())
        {
            enumfacing$axis = EnumFacing.Axis.X;
            blockportal$size = new BlockTofuPortal.Size(worldIn, p_181089_2_, EnumFacing.Axis.Z);
        }

        if (!blockportal$size.isValid())
        {
            return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        }
        else
        {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
            BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values())
            {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

                for (int i = 0; i < blockportal$size.getWidth(); ++i)
                {
                    for (int j = 0; j < blockportal$size.getHeight(); ++j)
                    {
                        BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

                        if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
                        {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values())
            {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
                {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
        }
    }
    
    
    
    
    
    public static class Size
    {
        private static Block frameBlock = TcBlocks.tofuGrilled;
        private static Block portalBlock = TcBlocks.tofuPortal;

        private final World world;
        private final EnumFacing.Axis axis;
        private final EnumFacing rightDir;
        private final EnumFacing leftDir;
        private int portalBlockCount = 0;
        private BlockPos bottomLeft;
        private int height;
        private int width;
        
        
        public Size(World worldIn, BlockPos blockposIn, EnumFacing.Axis p_i45694_3_)
        {
            this.world = worldIn;
            this.axis = p_i45694_3_;
            if (p_i45694_3_ == EnumFacing.Axis.X)
            {
                this.leftDir = EnumFacing.EAST;
                this.rightDir = EnumFacing.WEST;
            }
            else
            {
                this.leftDir = EnumFacing.NORTH;
                this.rightDir = EnumFacing.SOUTH;
            }
            
            for (BlockPos blockpos = blockposIn; blockposIn.getY() > blockpos.getY() - 21 && blockposIn.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(blockposIn.down())); blockposIn = blockposIn.down())
            {
                ;
            }

            int j1 = this.getDistanceUntilEdge(blockposIn, this.leftDir) - 1;
            
            if (j1 >= 0)
            {
                this.bottomLeft = blockposIn.offset(this.leftDir, j1);
                this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

                
                if (this.width < 2 || this.width > 21)
                {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if (this.bottomLeft != null)
            {
                this.height = this.calculatePortalHeight();
            }
        }

        protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_)
        {
        	int i1;
            for (i1 = 0; i1 < 22; ++i1)
            {
                BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i1);
                
                if (!this.isEmptyBlock(this.world.getBlockState(blockpos)) || this.world.getBlockState(blockpos.down()).getBlock() != Size.frameBlock)
                {
                    break;
                }
            	
            }
            Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i1)).getBlock();
            return block == Size.frameBlock ? i1 : 0;

        }
        
        public int getHeight()
        {
            return this.height;
        }

        public int getWidth()
        {
            return this.width;
        }

        protected int calculatePortalHeight()
        {
            label24:

			for (this.height = 0; this.height < 21; ++this.height) {
				
				for (int i = 0; i < this.width; ++i) {
					
					BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
					IBlockState state = this.world.getBlockState(blockpos);
					Block block = state.getBlock();

					if (!this.isEmptyBlock(state)) {
						break label24;
					}

					if (block == Size.portalBlock) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

						if (block != Size.frameBlock) {
							break label24;
						}
					} else if (i == this.width - 1) {
						block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

						if (block != Size.frameBlock) {
							break label24;
						}
					}
				}
			}

        	for (int j = 0; j < this.width; ++j)
        	{
        		if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Size.frameBlock)
        		{
        			this.height = 0;
        			break;
        		}
        	}

            if (this.height <= 21 && this.height >= 3)
            {
                return this.height;
            }
            else
            {
                this.bottomLeft = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        /** protected boolean func_150857_a(Block p_150857_1_) */
        protected boolean isEmptyBlock(IBlockState stateIn)
        {
            return stateIn.getMaterial() == Material.AIR || stateIn.getBlock() == Blocks.FIRE || stateIn.getBlock() == Size.portalBlock;
        }

        public boolean isValid()
        {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void placePortalBlocks()
        {
            for (int i = 0; i < this.width; ++i)
            {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                for (int j = 0; j < this.height; ++j)
                {
                    this.world.setBlockState(blockpos.up(j), Size.portalBlock.getDefaultState().withProperty(BlockTofuPortal.AXIS, this.axis), 2);
                }
            }
        }
    }

    
}
