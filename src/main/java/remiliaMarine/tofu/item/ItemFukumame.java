package remiliaMarine.tofu.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.entity.EntityFukumame;

public class ItemFukumame extends TcItem {

    public ItemFukumame()
    {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(62);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new DispenserBehaviorFukumame());
        this.addPropertyOverride(new ResourceLocation("empty"), new IItemPropertyGetter() {

			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				return stack.getItemDamage() > stack.getMaxDamage() ? 1.0f : 0.0f;
			}

        });
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stackIn, World par2World, EntityPlayer playerIn, EnumHand hand)
    {
        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stackIn) > 0;

        if (flag || stackIn.getItemDamage() <= stackIn.getMaxDamage())
        {
            for (int i = 0; i < 8; i++) {
                EntityFukumame fukumame = new EntityFukumame(par2World, playerIn);

                applyEffect(fukumame, stackIn);

                if (!par2World.isRemote)
                {
                    par2World.spawnEntityInWorld(fukumame);
                }
            }
            stackIn.attemptDamageItem(1, playerIn.getRNG());
            par2World.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stackIn);
        }
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stackIn);
    }

    public static void applyEffect(EntityFukumame fukumame, ItemStack itemstack)
    {
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);

        if (k > 0)
        {
            fukumame.setDamage(fukumame.getDamage() + (double)k * 0.25D + 0.25D);
        }

//        int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
//
//        if (l > 0)
//        {
//            fukumame.setKnockbackStrength(l);
//        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0)
        {
            fukumame.setFire(100);
        }

    }

    /**
     * How long it takes to use or consume an item
     */
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

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

    private class DispenserBehaviorFukumame implements IBehaviorDispenseItem
    {
        @Override
        public ItemStack dispense(IBlockSource iblocksource, ItemStack itemstack)
        {
            if (itemstack.getItemDamage() > itemstack.getMaxDamage()) return itemstack;

            EnumFacing enumfacing = iblocksource.getBlockState().getValue(BlockDispenser.FACING); // getFacing
            World world = iblocksource.getWorld();
            BlockPos pos = iblocksource.getBlockPos();

            for (int i = 0; i < 8; i++) {
                EntityFukumame fukumame = new EntityFukumame(world, pos.getX(), pos.getY(), pos.getZ());
                fukumame.setThrowableHeading(enumfacing.getFrontOffsetX(), (enumfacing.getFrontOffsetY() + 0.1F), enumfacing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());

                applyEffect(fukumame, itemstack);

                if (!world.isRemote)
                {
                    world.spawnEntityInWorld(fukumame);
                }
            }

            if (itemstack.isItemStackDamageable())
            {
                itemstack.getItem();
				itemstack.attemptDamageItem(1, Item.itemRand);
            }
            iblocksource.getWorld().playEvent(1000, pos, 0);
            return itemstack;
        }

        protected float func_82498_a()
        {
            return 6.0F;
        }

        protected float func_82500_b()
        {
            return 1.1F;
        }
    }
}
