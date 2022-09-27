package remiliaMarine.tofu.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTofuSword;
import remiliaMarine.tofu.util.ModLog;

public class EntityTofuSlime extends EntitySlime {

    public static final Predicate<Entity> ENTITY_SELECTOR_MYSELF = new Predicate<Entity>()
    {
		@Override
		public boolean apply(Entity input) {
			return input instanceof EntityTofuSlime;
		}
    };

    public EntityTofuSlime(World par1World)
    {
        super(par1World);
    }

    @Override
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
            TcAchievementMgr.achieve(entityplayer, TcAchievementMgr.Key.tofuSlimeHunter);
        }
    }

    /**
     * drops the loot of this entity upon death
     */
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
    	super.dropLoot(wasRecentlyHit, lootingModifier, source);
    	this.dropFewItems(wasRecentlyHit, lootingModifier);
    }

    @Override
    protected Item getDropItem()
    {
        return this.getSlimeSize() == 1 ? TcItems.tofuKinu : null;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
    	return LootTableList.EMPTY;
        //return this.getSlimeSize() == 1 ? TofuLootTableList.TOFU_SLIME : LootTableList.EMPTY;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        super.dropFewItems(wasRecentlyHit, lootingModifier);

        if (this.getSlimeSize() == 1 && this.attackingPlayer != null)
        {
            ItemStack equipped = this.attackingPlayer.getHeldItemMainhand();
            if (this.attackingPlayer.dimension == TofuCraftCore.TOFU_DIMENSION.getId()
                    || equipped != null && equipped.getItem() instanceof ItemTofuSword)
            {
                if (this.rand.nextInt(10) == 0)
                {
                    this.dropItem(TcItems.tofuStick, 1);
                }
            }
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItemsBase(boolean wasRecentlyHit, int lootingModifier)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int i = this.rand.nextInt(3);

            if (lootingModifier > 0)
            {
                i += this.rand.nextInt(lootingModifier + 1);
            }

            for (int j = 0; j < i; ++j)
            {
                this.dropItem(item, 1);
            }
        }
    }

    @Override
    protected EntitySlime createInstance()
    {
        return new EntityTofuSlime(this.worldObj);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        if (this.getSlimeSize() == 1 || this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
        {
            int lightValue = this.worldObj.getLightFromNeighbors(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
            Biome biome = this.worldObj.getBiome(new BlockPos(this.posX, 0, this.posZ));
            if (BiomeDictionary.isBiomeOfType(biome, TofuCraftCore.BIOME_TYPE_TOFU) && this.rand.nextInt(20) == 0
                    && this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(48.0D, 24.0D, 48.0D)).size() == 0)
            {
                return this.baseGetCanSpawnHere();
            }

            if (this.dimension == 0
                    && EntityTofuSlime.isSpawnChunk(this.worldObj, this.posX, this.posZ)
                    && this.posY > 15.0D && this.posY < 40.0D
                    && lightValue <= this.rand.nextInt(8))
            {
                ModLog.debug("Tofu slime spawned at (%.1f, %.1f, %.1f)", this.posX, this.posY, this.posZ);
                return this.baseGetCanSpawnHere();
            }
        }

        return false;
    }

    /**
     * Must be the same as EntityLiving.getCanSpawnHere!
     */
    public boolean baseGetCanSpawnHere()
    {
        IBlockState iblockstate = this.worldObj.getBlockState((new BlockPos(this)).down());
        return iblockstate.canEntitySpawn(this);
    }

    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    protected EnumParticleTypes getParticleType()
    {
        return EnumParticleTypes.SNOWBALL;
    }

    public static boolean isSpawnChunk(World world, double x, double z)
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor_double(x), 0, MathHelper.floor_double(z));
        Chunk chunk = world.getChunkFromBlockCoords(blockpos);
        return chunk.getRandomWithSeed(4611020141L).nextInt(24) == 0;
    }

}
