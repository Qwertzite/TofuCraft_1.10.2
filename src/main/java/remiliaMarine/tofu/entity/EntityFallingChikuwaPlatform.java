package remiliaMarine.tofu.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.block.BlockChikuwaPlatform;

public class EntityFallingChikuwaPlatform extends Entity implements IEntityAdditionalSpawnData {

	public static final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0,0,0,1,1,1);

    private IBlockState blockContained;
    public int blockMetadata;
    public int fallingTime;
    private int chikuwaChunkUID;
    private BlockPos homeCoord = null;

    public float yOffset;

    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;

    public EntityFallingChikuwaPlatform(World worldIn)
    {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.0F, 0.875F);
    }

    public EntityFallingChikuwaPlatform(World worldIn, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_, IBlockState block, int meta, int chikuwaChunkUID)
    {
        this(worldIn);
        this.blockContained = block;
        this.blockMetadata = meta;
        this.yOffset = this.height / 2.0F;
        this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
        this.motionX = 0.0D;
        this.motionY = -0.1D;
        this.motionZ = 0.0D;
        this.prevPosX = p_i45319_2_;
        this.prevPosY = p_i45319_4_;
        this.prevPosZ = p_i45319_6_;
        this.chikuwaChunkUID = chikuwaChunkUID;
    }

    @Override
    public float getEyeHeight()
    {
        return this.yOffset;
    }

    public void setHome(BlockPos coord)
    {
        this.homeCoord = coord;
    }

    public BlockPos getHome() {
        return this.homeCoord;
    }

    @Override
	protected void entityInit() {}

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    @Override
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.getEntityBoundingBox();
    }

    /**
     * Returns the collision bounding box for this entity
     */
    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return true;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void applyEntityCollision(Entity entityIn)
    {
    	if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entityIn);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (this.blockContained.getMaterial() == Material.AIR)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY = -0.1D;
            ++this.fallingTime;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (!this.worldObj.isRemote)
            {
                int tileX = MathHelper.floor_double(this.posX);
                int tileY = MathHelper.floor_double(this.posY);
                int tileZ = MathHelper.floor_double(this.posZ);

                if (this.onGround)
                {
                    if (this.onLanding(tileX, tileY, tileZ))
                    {
                        this.triggerLanding();
                    }
                }
                else if (this.fallingTime > 100 && !this.worldObj.isRemote && (tileY < 1 || tileY > 256) || this.fallingTime > 600)
                {
                    if (this.homeCoord != null)
                    {
                        if (this.onLanding(tileX, tileY, tileZ))
                        {
                            this.triggerLanding();
                        }
                    }
                    else
                    {
                        this.entityDropItem(new ItemStack(this.blockContained.getBlock(), 1, this.blockContained.getBlock().damageDropped(this.blockContained)), 0.0F);
                        this.setDead();
                    }
                }
            }
        }
    }

    public void triggerLanding()
    {
        List<EntityFallingChikuwaPlatform> chikuwaChunk = Lists.newArrayList();

        for (Object entry : this.worldObj.loadedEntityList)
        {
            if (entry instanceof EntityFallingChikuwaPlatform)
            {
                EntityFallingChikuwaPlatform chikuwa = (EntityFallingChikuwaPlatform)entry;
                if (!chikuwa.equals(this) && chikuwa.chikuwaChunkUID == this.chikuwaChunkUID)
                {
                    chikuwaChunk.add(chikuwa);
                }
            }
        }

        for (EntityFallingChikuwaPlatform entity : chikuwaChunk)
        {
            int tileX = MathHelper.floor_double(entity.posX);
            int tileY = MathHelper.floor_double(entity.posY);
            int tileZ = MathHelper.floor_double(entity.posZ);
            entity.onLanding(tileX, tileY, tileZ);
        }
    }

    private boolean onLanding(int tileX, int tileY, int tileZ)
    {
    	BlockPos tilePos;
        boolean landed;
        if (this.homeCoord != null)
        {
            tileX = this.homeCoord.getX();
            tileY = this.homeCoord.getY();
            tileZ = this.homeCoord.getZ();
            tilePos = this.homeCoord;

            this.setDead();
            landed = this.worldObj.setBlockState(tilePos, this.blockContained, 3);
        }
        else
        {
        	tilePos = new BlockPos(tileX, tileY, tileZ);
            if (this.worldObj.getBlockState(tilePos).getBlock() != Blocks.PISTON_EXTENSION)
            {
                this.setDead();
                landed = this.worldObj.canBlockBePlaced(this.blockContained.getBlock(), tilePos, true, EnumFacing.UP, (Entity) null, (ItemStack) null)
                        && !BlockChikuwaPlatform.canGoThrough(this.worldObj, tilePos.down())
                        && this.worldObj.setBlockState(tilePos, this.blockContained, 3);
            }
            else
            {
                // Consider landing is successful
                landed = true;
            }
        }

        if (!landed)
        {
            this.entityDropItem(new ItemStack(this.blockContained.getBlock(), 1, this.blockContained.getBlock().damageDropped(this.blockContained)), 0.0F);
        }
        return landed;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("TileID", Block.getIdFromBlock(this.blockContained.getBlock()));
        nbt.setByte("Data", (byte) this.blockMetadata);
        nbt.setShort("Time", (short) this.fallingTime);
        nbt.setInteger("ChunkUID", chikuwaChunkUID);
        if (this.homeCoord != null)
        {
            NBTTagCompound nbtHome = new NBTTagCompound();
            nbtHome.setInteger("X", this.homeCoord.getX());
            nbtHome.setInteger("Y", this.homeCoord.getY());
            nbtHome.setInteger("Z", this.homeCoord.getZ());
            nbt.setTag("Home", nbtHome);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        this.blockMetadata = nbt.getByte("Data") & 255;
        this.blockContained = Block.getBlockById(nbt.getInteger("TileID")).getStateFromMeta(this.blockMetadata);
        this.fallingTime = nbt.getShort("Time");
        this.chikuwaChunkUID = nbt.getInteger("ChunkUID");
        if (nbt.hasKey("Home"))
        {
            NBTTagCompound nbtHome = nbt.getCompoundTag("Home");
            this.homeCoord = new BlockPos(nbtHome.getInteger("X"), nbtHome.getInteger("Y"), nbtHome.getInteger("Z"));
        }

        if (this.blockContained.getMaterial() == Material.AIR)
        {
            this.blockContained = Blocks.SAND.getDefaultState();
        }
    }

    @Override
    public void addEntityCrashInfo(CrashReportCategory p_85029_1_)
    {
        super.addEntityCrashInfo(p_85029_1_);
        p_85029_1_.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.blockContained.getBlock())));
        p_85029_1_.addCrashSection("Immitating block data", Integer.valueOf(this.blockMetadata));
    }

    @SideOnly(Side.CLIENT)
    /**public World func_145807_e()*/
    public World getWorldObj()
    {
        return this.worldObj;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }
    /** public Block func_145805_f() */
    public IBlockState getContainedBlock()
    {
        return this.blockContained;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int posRotationIncrements, boolean teleport)
    {
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
    {
        this.velocityX = this.motionX = p_70016_1_;
        this.velocityY = this.motionY = p_70016_3_;
        this.velocityZ = this.motionZ = p_70016_5_;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        buffer.writeInt(Block.getIdFromBlock(blockContained.getBlock()));
        buffer.writeByte(blockMetadata);

        buffer.writeFloat(((float)motionX));
        buffer.writeFloat(((float)motionY));
        buffer.writeFloat(((float)motionZ));
    }

    @SuppressWarnings("deprecation")
	@Override
    public void readSpawnData(ByteBuf additionalData)
    {
    	Block tmpBlock = Block.getBlockById(additionalData.readInt());
        blockMetadata = additionalData.readByte();
        blockContained = tmpBlock.getStateFromMeta(blockMetadata);

        motionX = additionalData.readFloat();
        motionY = additionalData.readFloat();
        motionZ = additionalData.readFloat();
        setVelocity(motionX, motionY, motionZ);
    }

}
