package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemDrinkBucket extends ItemTcBucket {
    private boolean alwaysEdible = false;
    
    private final int healAmount;
    private final Float saturationModifier;

    private Potion potionId;
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;
    
    public ItemDrinkBucket(Block par2, int healAmount, float saturationModifier)
    {
        super(par2);
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        
    	if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
        {
    		ItemStackAdapter.addSize(stack, -1);
        }
        
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			entityplayer.getFoodStats().addStats(healAmount, saturationModifier);
			this.func_77849_c(stack, worldIn, entityplayer);

			if (ItemStackAdapter.getSize(stack) <= 0) {
				return new ItemStack(Items.BUCKET);
			}

			entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
			
		}
        
        return stack;
    }
    
    protected void func_77849_c(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote && Potion.getIdFromPotion(this.potionId) > 0 && par2World.rand.nextFloat() < this.potionEffectProbability)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.DRINK;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    { 
    	ActionResult<ItemStack> resultItemStack = super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    	
        RayTraceResult var12 = this.rayTrace(worldIn, playerIn, true);
        if (var12 == null && playerIn.canEat(this.alwaysEdible))
        {
        	playerIn.setActiveHand(hand);
        }

        return resultItemStack;
    }
    
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return EnumActionResult.PASS;
    }
    
    public ItemDrinkBucket setPotionEffect(Potion par1, int par2, int par3, float par4)
    {
        this.potionId = par1;
        this.potionDuration = par2;
        this.potionAmplifier = par3;
        this.potionEffectProbability = par4;
        return this;
    }

    public ItemDrinkBucket setAlwaysEdible()
    {
        this.alwaysEdible = true;
        return this;
    }
    
    
    
    
    
}
