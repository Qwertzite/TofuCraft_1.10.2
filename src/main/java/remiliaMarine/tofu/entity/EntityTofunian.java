package remiliaMarine.tofu.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.init.TcVillages;
import remiliaMarine.tofu.item.ItemTcMaterials.EnumTcMaterialInfo;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class EntityTofunian extends EntityVillager {
    private int friendship = 0;

    public EntityTofunian(World par1World)
    {
        this(par1World, 0);
    }

    @SuppressWarnings("deprecation")
	public EntityTofunian(World par1World, int par2)
    {
        super(par1World, VillagerRegistry.getId(TcVillages.ProfessionTofunian));
        this.setSize(0.45F, 1.4F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    }

    @Override
    public float getEyeHeight()
    {
        return 1.0f;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setProfession(TcVillages.ProfessionTofunian);
        return livingdata;
    }

    @Override
    public EntityVillager createChild(EntityAgeable par1EntityAgeable)
    {
        EntityTofunian tofunian = new EntityTofunian(this.worldObj);
        tofunian.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.getPosition()), null);
        return tofunian;
    }

    @Override
    public void useRecipe(MerchantRecipe par1MerchantRecipe)
    {
        super.useRecipe(par1MerchantRecipe);
        ItemStack buy = par1MerchantRecipe.getItemToBuy();

        if (buy.getItem() == TcItems.tofuMomen || buy.getItem() == TcItems.tofuKinu)
        {
            this.friendship += ItemStackAdapter.getSize(buy);
            ModLog.debug("friendship=%d", this.friendship);
            if(this.getFriendship() >= 256) {
            	int career = ReflectionHelper.getPrivateValue(EntityVillager.class, this, "careerId", "field_175563_bv");
            	ModLog.debug("career=%d", career);
            	if (career < 3) {
            		ReflectionHelper.setPrivateValue(EntityVillager.class, this, 3, "careerId", "field_175563_bv");
            		MerchantRecipeList recipeList = ReflectionHelper.getPrivateValue(EntityVillager.class, this, "buyingList", "field_70963_i");
            		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.materials, 3+this.rand.nextInt(3), EnumTcMaterialInfo.tofuGem.getMetadata()), new ItemStack(TcItems.tofuHoe)));
            		ModLog.debug("career set to %d", 3);
            	}
            } else if (this.getFriendship() >= 64) {
            	int career = ReflectionHelper.getPrivateValue(EntityVillager.class, this, "careerId", "field_175563_bv");
            	ModLog.debug("career=%d", career);
            	if (career < 2) {
            		ReflectionHelper.setPrivateValue(EntityVillager.class, this, 2, "careerId", "field_175563_bv");
            		MerchantRecipeList recipeList = ReflectionHelper.getPrivateValue(EntityVillager.class, this, "buyingList", "field_70963_i");
            		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.materials, 20+rand.nextInt(6), EnumTcMaterialInfo.tofuGem.getMetadata()), new ItemStack(TcItems.tofuDiamond)));
                	ModLog.debug("career set to %d", 2);
            	}
            }
        }
    }

    public int getFriendship()
    {
        return this.friendship;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Riches", this.friendship);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.friendship = par1NBTTagCompound.getInteger("Riches");
    }

    @Override
    public void playSound(SoundEvent sound, float par2, float par3)
    {
    	if(sound == SoundEvents.ENTITY_VILLAGER_AMBIENT||
    			sound == SoundEvents.ENTITY_VILLAGER_DEATH ||
    			sound == SoundEvents.ENTITY_VILLAGER_HURT ||
    			sound == SoundEvents.ENTITY_VILLAGER_NO ||
    			sound == SoundEvents.ENTITY_VILLAGER_TRADING ||
    			sound == SoundEvents.ENTITY_VILLAGER_YES) return;
        this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, sound, this.getSoundCategory(), par2, par3);
    }
}
