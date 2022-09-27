package remiliaMarine.tofu.item;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.entity.EntityZundaArrow;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketZundaArrowType;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemZundaBow extends ItemBow {

    public ItemZundaBow()
    {
        super();
        this.setMaxDamage(1345);
        this.addPropertyOverride(new ResourceLocation("zpull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return itemstack != null && itemstack.getItem() == TcItems.zundaBow ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });

        this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if(entityIn != null && entityIn instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)entityIn;
			        EnumArrowType arrowType = EntityInfo.instance().get(player.getEntityId(), DataType.ZundaArrowType);
			        if(arrowType == EnumArrowType.ZUNDA) return 1.0f;
				}
				return 0.0f;
			}
        });
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase entityLiving, int par4)
    {
        if (entityLiving instanceof EntityPlayer)
        {
        	EntityPlayer player = (EntityPlayer)entityLiving;
	        int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;

	        boolean var5 = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;

	        EnumArrowType arrowType = EntityInfo.instance().get(player.getEntityId(), DataType.ZundaArrowType);

	        if (arrowType != null)
	        {
	            float var7 = var6 / 20.0F;
	            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

	            if (var7 < 0.1D)
	            {
	                return;
	            }

	            if (var7 > 1.0F)
	            {
	                var7 = 1.0F;
	            }

	            Entity var8;

	            if (arrowType != EnumArrowType.ZUNDA)
	            {
	                var8 = this.getNormalArrow(par1ItemStack, par2World, player, var7, arrowType);

	                if (var5)
	                {
	                    ((EntityArrow) var8).pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                }
	            }
	            else
	            {
	                var8 = this.getZundaArrow(par1ItemStack, par2World, player, var7);

	                if (var5)
	                {
	                    ((EntityZundaArrow) var8).pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                }
	            }

	            if (!var5)
	            {
	                this.consumeInventoryItem(player.inventory, arrowType.item);
	            }
	            par1ItemStack.damageItem(1, player);
	            par2World.playSound(player,player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

	            if (!par2World.isRemote)
	            {
	                par2World.spawnEntityInWorld(var8);
	            }
	        }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
    {
        EnumArrowType arrowType = this.getArrowType(par1ItemStack, par3EntityPlayer);
        if (arrowType != null)
        {
            par3EntityPlayer.setActiveHand(hand);
            PacketDispatcher.packet(
                    new PacketZundaArrowType(par3EntityPlayer.getEntityId(), arrowType))
                    .sendPacketToAllPlayers();
            EntityInfo.instance().set(par3EntityPlayer.getEntityId(), DataType.ZundaArrowType, arrowType);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, par1ItemStack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, par1ItemStack);
    }

    public EntityArrow getNormalArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, float var7, EnumArrowType type)
    {
    	EntityArrow arrow = ((ItemArrow)type.item).createArrow(par2World, par1ItemStack, par3EntityPlayer);
    	arrow.setAim(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0f, var7*2.0f*1.5f, 1.0f);
        if (var7 == 1.0F)
        {
            arrow.setIsCritical(true);
        }

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);

        if (var9 > 0)
        {
            arrow.setDamage(arrow.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, par1ItemStack);

        if (var10 > 0)
        {
            arrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, par1ItemStack) > 0)
        {
            arrow.setFire(100);
        }

        return arrow;
    }

    public EntityZundaArrow getZundaArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, float var7)
    {
        EntityZundaArrow var8 = new EntityZundaArrow(par2World, par3EntityPlayer);
        var8.setAim(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0f, var7*2.0f*1.5f, 1.0f);

        if (var7 == 1.0F)
        {
            var8.setIsCritical(true);
        }

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);

        if (var9 > 0)
        {
            var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, par1ItemStack);

        if (var10 > 0)
        {
            var8.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, par1ItemStack) > 0)
        {
            var8.setFire(100);
        }

        return var8;
    }

    private EnumArrowType getArrowType(ItemStack par1ItemStack, EntityPlayer player)
    {
        boolean var5 = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;

        if (var5 || findItem(player.inventory, EnumArrowType.ZUNDA.item) >= 0)
        {
            return EnumArrowType.ZUNDA;
        }
        else
        {
        	ItemStack stack = null;
            if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
            {
            	stack = player.getHeldItem(EnumHand.OFF_HAND);
            }
            else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
            {
            	stack = player.getHeldItem(EnumHand.MAIN_HAND);
            }
            else
            {
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);

                    if (this.isArrow(itemstack))
                    {
                    	stack = itemstack;
                    }
                }
            }
            if(stack == null) return null;
            if(stack.getItem() == EnumArrowType.NORMAL.item) return EnumArrowType.NORMAL;
            if(stack.getItem() == EnumArrowType.TIPPED.item) return EnumArrowType.TIPPED;
            if(stack.getItem() == EnumArrowType.SPECTRAL.item) return EnumArrowType.SPECTRAL;
            return null;
    	}
    }


    /**
     * removed one item of specified Item from inventory (if it is in a stack, the stack size will reduce with 1)
     */
    public boolean consumeInventoryItem(InventoryPlayer inv, Item itemIn)
    {
        int i = this.findItem(inv, itemIn);

        if (i < 0)
        {
            return false;
        }
        else
        {
        	ItemStackAdapter.addSize(inv.mainInventory[i], -1);
            if (ItemStackAdapter.getSize(inv.mainInventory[i]) <= 0)
            {
            	inv.mainInventory[i] = null;
            }

            return true;
        }
    }

    private int findItem(InventoryPlayer inv, Item itemIn)
    {
        for (int i = 0; i < inv.mainInventory.length; ++i)
        {
            if (inv.mainInventory[i] != null && inv.mainInventory[i].getItem() == itemIn)
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    public enum EnumArrowType
    {
        NORMAL(Items.ARROW),
        TIPPED(Items.TIPPED_ARROW),
        SPECTRAL(Items.SPECTRAL_ARROW),
        ZUNDA(TcItems.zundaArrow);

        public final Item item;

        EnumArrowType(Item item)
        {
            this.item = item;
        }
    }
}
