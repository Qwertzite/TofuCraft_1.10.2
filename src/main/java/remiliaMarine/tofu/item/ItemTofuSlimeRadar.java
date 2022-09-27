package remiliaMarine.tofu.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.entity.EntityTofuSlime;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketTofuRadar;

public class ItemTofuSlimeRadar extends TcItem{

    public ItemTofuSlimeRadar()
    {
        super();
    	this.addPropertyOverride(new ResourceLocation("scanresult"), new IItemPropertyGetter() {

            @SideOnly(Side.CLIENT)
    		int disp;

			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {

				if(entityIn == null) return 0.0f;
	            Entry entry = EntityInfo.instance().get(entityIn.getEntityId(), DataType.SlimeRadar);
	            if (entry != null)
	            {
	                if (entry.result)
	                {
	                    return (entry.ticks & 7) > 2 ? 1.0f : 0.0f;
	                }
	                else
	                {
	                    return -1.0f;
	                }
	            }
				return 0;
			}

    	});
        this.setMaxStackSize(1);
        this.setMaxDamage(87);
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par2World.isRemote)
        {
            Entry entry = EntityInfo.instance().get(par3Entity.getEntityId(), DataType.SlimeRadar);

            if (entry != null)
            {
                entry.ticks--;

                if (entry.ticks < 0)
                {
                    EntityInfo.instance().remove(par3Entity.getEntityId(), DataType.SlimeRadar);
                }
                else
                {
                    EntityInfo.instance().set(par3Entity.getEntityId(), DataType.SlimeRadar, entry);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.setActiveHand(hand);

        boolean flag = playerIn.capabilities.isCreativeMode;

        if (flag || itemStackIn.getItemDamage() <= itemStackIn.getMaxDamage())
        {
            if (!worldIn.isRemote)
            {
                Biome biome = playerIn.worldObj.getBiome(new BlockPos(MathHelper.floor_double(playerIn.posX), 0, MathHelper.floor_double(playerIn.posZ)));
                boolean isSpawnChunk = BiomeDictionary.isBiomeOfType(biome, TofuCraftCore.BIOME_TYPE_TOFU) || EntityTofuSlime.isSpawnChunk(playerIn.worldObj, playerIn.posX, playerIn.posZ);

                PacketDispatcher.packet(new PacketTofuRadar(isSpawnChunk))
                        .sendToPlayer((EntityPlayerMP)playerIn);
            }

            if (!playerIn.capabilities.isCreativeMode && itemStackIn.isItemStackDamageable())
            {
                itemStackIn.attemptDamageItem(1, playerIn.getRNG());
            }
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 0.5F, 1.0F);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    public void onUse(boolean isSpawnChunk, ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        Entry entry = new Entry();
        entry.ticks = 50;
        entry.result = isSpawnChunk;
        EntityInfo.instance().set(par3EntityPlayer.getEntityId(), DataType.SlimeRadar, entry);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.NONE;
    }

    private class Entry
    {
        public int ticks;
        public boolean result;
    }
}
