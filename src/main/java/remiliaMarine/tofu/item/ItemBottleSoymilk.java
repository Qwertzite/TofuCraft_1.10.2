package remiliaMarine.tofu.item;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.item.iteminfo.SoymilkPlayerInfo;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemBottleSoymilk extends TcItem {
	
    
    // !! NOTE !! Potion Effect Tier is up to 20.
	
    
    public ItemBottleSoymilk()
    {
        this.setHasSubtypes(true);
        this.setCreativeTab(TcCreativeTabs.FOOD);
        this.setContainerItem(Items.GLASS_BOTTLE);
    }
    
    public List<PotionEffect> getPotionEffect(ItemStack stack, EntityPlayer par2EntityPlayer)
    {
        List<PotionEffect> list = Lists.newArrayList();
        SoymilkFlavour flavor = SoymilkFlavour.getFromMeta(stack.getMetadata());

        PotionEffect newEffect = flavor.getPotionEffect(SoymilkPlayerInfo.of(par2EntityPlayer).readNBTFromPlayer());
        if (newEffect != null)
        {
            list.add(newEffect);
        }
        return list;
    }
    
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
        	ItemStackAdapter.addSize(stack, -1);
        }
        
        if (!worldIn.isRemote && entityplayer != null)
        {
            SoymilkPlayerInfo info = SoymilkPlayerInfo.of(entityplayer).readNBTFromPlayer();
            info.onTaken();

            for (PotionEffect potioneffect : this.getPotionEffect(stack, entityplayer))
            {
            	entityplayer.addPotionEffect(new PotionEffect(potioneffect));
            }
            SoymilkFlavour flavour = SoymilkFlavour.getFromMeta(stack.getMetadata());
            entityplayer.getFoodStats().addStats(flavour.getHealAmount(), flavour.getSaturationModifier());
            worldIn.playSound(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);            
            
            TcAchievementMgr.achieve(entityplayer, TcAchievementMgr.Key.soymilk1st);

            if (info.isFirstTime)
            {
                // First experience of soy milk!
            	entityplayer.addChatMessage(new TextComponentTranslation("tofucraft.soymilkTaken.greetings"));
            }
            else if (info.daysPassed > 0)
            {
                if (info.chain == 1)
                {
                    // Chain broken
                    entityplayer.addChatMessage(new TextComponentTranslation("tofucraft.soymilkTaken.recovered",
                            info.daysPassed - 1, info.tier));
                } else
                {
                    // Keeping a chain
                    entityplayer.addChatMessage(new TextComponentTranslation("tofucraft.soymilkTaken.chain",
                            info.chain, info.tier));

                    if (info.chain >= 7)
                    {
                        TcAchievementMgr.achieve(entityplayer, TcAchievementMgr.Key.soymilkWeek);
                    }
                    if (info.tier == 20)
                    {
                        TcAchievementMgr.achieve(entityplayer, TcAchievementMgr.Key.soymilkMax);
                    }
                }
            }
        }
        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            if (ItemStackAdapter.getSize(stack) <= 0)
            {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (entityplayer != null)
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        
        return stack;
	}
	
    /**
     * How long it takes to use or consume an item
     */
	@Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
    /**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
	
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	SoymilkFlavour flavour = SoymilkFlavour.getFromMeta(stack.getMetadata());
    	
        if (flavour.isRandomPotionEffect())
        {
        	tooltip.add(I18n.format("tofucraft.soymilkInfo.randomEffect"));
            return;
        }
    	
        List<PotionEffect> list1 = this.getPotionEffect(stack, playerIn);
        HashMultimap<String, AttributeModifier> hashmultimap = HashMultimap.create();
        
        if (list1 != null && !list1.isEmpty())
        {
            Iterator<PotionEffect> iterator1 = list1.iterator();

            while (iterator1.hasNext())
            {
                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                String s1 = I18n.format(potioneffect.getEffectName()).trim();
                Potion potion = potioneffect.getPotion();
                Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

                if (map != null && map.size() > 0)
                {
                    Iterator<Entry<IAttribute, AttributeModifier>> iterator = map.entrySet().iterator();

                    while (iterator.hasNext())
                    {
                        Map.Entry<IAttribute, AttributeModifier> entry = (Entry<IAttribute, AttributeModifier>)iterator.next();
                        AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        hashmultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
                    }
                }

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + I18n.format("potion.potency." + potioneffect.getAmplifier()).trim();
                }

                if (potioneffect.getDuration() > 20)
                {
                    s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, 1.0f) + ")";
                }

                if (potion.isBadEffect())
                {
                    tooltip.add(TextFormatting.RED + s1);
                }
                else
                {
                	tooltip.add(TextFormatting.GRAY + s1);
                }
            }
        }
        else
        {
            String s = I18n.format("potion.empty").trim();
            tooltip.add(TextFormatting.GRAY + s);
        }

        if (!hashmultimap.isEmpty())
        {
        	tooltip.add("");
        	tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("potion.whenDrank"));
            Iterator<Entry<String, AttributeModifier>> iterator1 = hashmultimap.entries().iterator();

            while (iterator1.hasNext())
            {
                Map.Entry<String, AttributeModifier> entry1 = (Entry<String, AttributeModifier>)iterator1.next();
                AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
                double d0 = attributemodifier2.getAmount();
                double d1;

                if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                {
                    d1 = attributemodifier2.getAmount();
                }
                else
                {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D)
                {
                	tooltip.add(TextFormatting.BLUE + I18n.format("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), I18n.format("attribute.name." + (String)entry1.getKey())}));
                }
                else if (d0 < 0.0D)
                {
                    d1 *= -1.0D;
                    tooltip.add(TextFormatting.RED + I18n.format("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), I18n.format("attribute.name." + (String)entry1.getKey())}));
                }
            }
        }
        
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(playerIn).readNBTFromPlayer();
        info.update();
        if (info.tier == 0)
        {
        	tooltip.add(I18n.format("tofucraft.soymilkInfo.stat1"));
        	//tooltip.add(String.valueOf(info.tier));
        }
        else
        {
        	tooltip.add(I18n.format("tofucraft.soymilkInfo.stat2", info.tier));
        	//tooltip.add(String.valueOf(info.tier));
        }
        
//TODO        if (info.daysPassed != 0) {
//        	tooltip.add(I18n.translateToLocal("tofucraft.soymilkinfo.haventdrunk"));
//        }
        
    }
    
    public int getColour(int meta) {
    	return SoymilkFlavour.getFromMeta(meta).getPotionColour();
    }
    
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i = 0; i < SoymilkFlavour.values().length; ++i)
        {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
    
    public ItemStack getItemStack(SoymilkFlavour flavour) {
    	return new ItemStack(this, 1, flavour.getId());
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + SoymilkFlavour.getFromMeta(this.getMetadata(stack)).getUnlocalizedName();
    	
    }
}
