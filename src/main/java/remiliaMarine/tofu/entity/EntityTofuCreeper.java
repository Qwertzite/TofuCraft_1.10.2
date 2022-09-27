package remiliaMarine.tofu.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.world.loot.TofuLootTableList;

public class EntityTofuCreeper extends EntityMob {

	/**dataWatcher 16*/
    private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(EntityCreeper.class, DataSerializers.VARINT);
    /**dataWatcher 17*/
    private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityCreeper.class, DataSerializers.BOOLEAN);

    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private float explosionRadius = 1.5F;
    //private long es;

    public EntityTofuCreeper(World par1World)
    {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITofuCreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity<EntityOcelot>(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

//    /**
//     * Returns true if the newer Entity AI code should be run
//     */
//    @Override
//    public boolean isAIEnabled()
//    {
//        return true;
//    }

    @Override
    public int getMaxFallHeight()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }
    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
	public void fall(float distance, float damageMultiplier)
    {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(STATE, Integer.valueOf(-1));
        this.dataManager.register(POWERED, Boolean.valueOf(false));
    }

    public static void registerFixesTofuCreeper(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, "TofuCreeper");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if (((Boolean)this.dataManager.get(POWERED)).booleanValue())
        {
            compound.setBoolean("powered", true);
        }

        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));

        if (compound.hasKey("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.hasKey("ExplosionRadius", 99))
        {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;


                //for (i = 0; i < 200; i++)
                //{
                    //Entity splash = new EntityTofuSplash(worldObj, this);
                    //worldObj.spawnEntityInWorld(splash);
                //}

                int r = getPowered() ? 6 : 2;

                this.buildTofu((int)this.posX, (int)this.posY, (int)this.posZ, r, this.worldObj);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + r, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX - r, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ + r, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ - r, 1.0D, 0.0D, 0.0D);
                this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
                this.setDead();
            }
        }
        super.onUpdate();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (this.dimension == TofuCraftCore.TOFU_DIMENSION.getId())
        {
            return super.getCanSpawnHere();
        }
        else
        {
            int[] bid = EntityTofuCreeper.getSpawnBiomeIds(this.worldObj);
            int bidHere = Biome.getIdForBiome(worldObj.getBiome(new BlockPos((int)this.posX, 0, (int)this.posZ)));
            if (bidHere == bid[0] || bidHere == bid[1])
            {
                return super.getCanSpawnHere();
            }
            else
            {
                return false;
            }
        }
    }

    public static int[] getSpawnBiomeIds(World world)
    {
        int[] idx = TofuCreeperSeed.instance().getSpawnId(world, TcEntity.allBiomesList.length);
        for (int i = 0; i < idx.length; i++)
        {
            idx[i] = Biome.getIdForBiome(TcEntity.allBiomesList[idx[i]]);
        }
        return idx;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (this.worldObj.getGameRules().getBoolean("doMobLoot"))
        {
            if (cause.getEntity() instanceof EntitySkeleton)
            {
                this.dropItem(TcItems.tofuCake, 1);
            }
            else if (cause.getEntity() instanceof EntityCreeper && cause.getEntity() != this && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled())
            {
                ((EntityCreeper)cause.getEntity()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return ((Boolean)this.dataManager.get(POWERED)).booleanValue();
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return TofuLootTableList.TOFU_CREEPER;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return ((Integer)this.dataManager.get(STATE)).intValue();
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state)
    {
        this.dataManager.set(STATE, Integer.valueOf(state));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        //super.onStruckByLightning(lightningBolt);
        this.dealFireDamage(1);
    	this.dataManager.set(POWERED, Boolean.valueOf(true));
    }

    protected void buildTofu(int ox, int oy, int oz, int height, World par1World)
    {
        int blockY, radius, blockX;
        radius = 1 + height / 2;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (blockY = oy; blockY <= oy + height; ++blockY)
        {
            for (blockX = ox - radius; blockX <= ox + radius; ++blockX)
            {

                for (int blockZ = oz - radius; blockZ <= oz + radius; ++blockZ)
                {
                    mutable.setPos(blockX, blockY, blockZ);
                    if (par1World.getBlockState(mutable).getBlock() != Blocks.MOB_SPAWNER)
                    {
                        par1World.setBlockState(mutable, TcBlocks.tofuMomen.getDefaultState(), 3);
                    }
                }
            }
        }
    }


}
