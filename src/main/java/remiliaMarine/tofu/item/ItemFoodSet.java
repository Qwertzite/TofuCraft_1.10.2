package remiliaMarine.tofu.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.iteminfo.PotionEffectEntry;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemFoodSet extends TcItem {
	public static String foodSetName = null;

    public ItemFoodSet()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(TcCreativeTabs.FOOD);
        this.setUnlocalizedName("foodSet");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	EnumSetFood food = EnumSetFood.byMetaData(stack.getMetadata());
    	ItemStackAdapter.addSize(stack, -1);

        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;

            entityplayer.getFoodStats().addStats(food.healAmount, food.saturationModifier);
			worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);

			if (!worldIn.isRemote) {
				this.onFoodEaten(stack, worldIn, entityplayer);
			}

			if (food.hasContainerItem()) {
				if (ItemStackAdapter.getSize(stack) <= 0) {
					return food.getContainerItem();
				}

				entityplayer.inventory.addItemStackToInventory(food.getContainerItem());

        	}
        }
        return stack;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        List<PotionEffect> effectList = EnumSetFood.byItemStack(stack).getPotionEffect(stack, player);
        for (PotionEffect effect : effectList)
        {
            player.addPotionEffect(effect);
        }
    }

    /**
     * How long it takes to use or consume an item
     */
	@Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return EnumSetFood.byItemStack(stack).getDuration();
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (playerIn.canEat(EnumSetFood.byItemStack(itemStackIn).alwaysEdible))
        {
            playerIn.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int dmg = par1ItemStack.getItemDamage();
        return "item." + this.getFullName(dmg);
    }

    private String getFullName(int dmg)
    {
    	EnumSetFood food = EnumSetFood.byMetaData(dmg);
        return "tofucraft:" + (this.getItemSetName() != null ? this.getItemSetName() + "." : "") + food.getName();
    }

    protected String getItemSetName() {
    	return ItemFoodSet.foodSetName;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	int length = EnumSetFood.values().length;
        for (int i = 0; i < length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public ModelResourceLocation[] getResourceLocation() {

    	EnumSetFood[] values = EnumSetFood.values();
    	ModelResourceLocation[] ret = new ModelResourceLocation[EnumSetFood.values().length];
    	int length = values.length;

    	for (int i = 0; i < length; i++) {
    		ret[i] = new ModelResourceLocation(TofuCraftCore.RES + values[i].getName(), "inventory");
    	}

    	return ret;
    }

    public static enum EnumSetFood implements IStringSerializable {

        TOFU_CHIKUWA      ( 0,  6, 0.4f,  true, "tofuChikuwa"),
        OAGE               ( 1,  5, 0.3f,  true, "oage"),
        MABODOFU           ( 2, 16, 0.5f, false, "mabodofu", new ItemStack(Items.BOWL)),
        ONIGIRI            ( 3,  4, 0.4f, false, "onigiri"),
        ONIGIRI_SALT       ( 4,  5, 0.6f, false, "onigiriSalt"),
        YAKIONIGIRI_MISO   ( 5,  6, 0.8f, false, "yakionigiriMiso"),
        YAKIONIGIRI_SHOYU  ( 6,  6, 0.8f, false, "yakionigiriShoyu"),
        SPROUT_SAUTE       ( 7,  8, 0.4f, false, "moyashiitame", new ItemStack(Items.BOWL)),
        MOYASHI_OHITASHI   ( 8,  5, 0.3f, false, "moyashiohitashi", new ItemStack(Items.BOWL)),
        SPROUTS            ( 9,  2, 0.5f, false, "sprouts"),
        HIYAYAKKO          (10,  6, 0.5f, false, "hiyayakko"),
        RICE_TOFU          (11, 10, 0.2f, false, "riceTofu"), // For External Mod
        TOFU_HAMBURG       (12, 12, 0.8f, false, "tofuHamburg"),
        TOFU_COOKIE       (13,  2, 0.15f, false, "tofuCookie"),
        INARI              (14,  5, 0.6f, false, "inari"),
        TOFUFISH_RAW       (15,  4, 0.4f, false, "tofufishRaw"),
        TOFUFISH_COOKED    (16,  6, 0.6f, false, "tofufishCooked"),
        HIYAYAKKO_GL       (17,  6, 0.5f, false, "hiyayakko_glass", ItemTcMaterials.EnumTcMaterialInfo.glassBowl.getStack()),
        NATTO_HIYAYAKKO_GL (18,  8, 0.8f, false, "nattoHiyayakko_glass", ItemTcMaterials.EnumTcMaterialInfo.glassBowl.getStack()),
        TOFU_SOMEN         (19,  3, 0.3f,  true, "tofuSomenBowl_glass", new ItemStack(TcItems.somenTsuyuBowl)),
        ZUNDA_MOCHI        (20,  3, 0.8f, false, "zundaMochi"), // For External Mod
        KINAKO_MOCHI       (21,  5, 0.8f, false, "kinakoMochi"), // For External Mod
        CHIKUWA            (22,  4, 0.6f, false, "chikuwa"),
        TOFU_STEAK         (23,  6, 0.6f, false, "tofuSteak"),
        TOFU_HAMBURG_TEMPRA(24, 16, 1.0f, false, "tofuHamburgT"),
        TOFU_HAMBURG_TEMPRA_ANKAKE(25, 20, 1.0f, false, "tofuHamburgTA"),
        TOFU_MINCED        (26,  2, 0.2f, false, "tofuMinced"),
        RICE_SOBORO_TOFU   (27,  8, 0.6f, false, "riceSoboroTofu"), // For External Mod
        SOBORO_TOFU_SAUTE  (28,  7, 0.6f, false, "soboroTofuSaute"),
        // moved from ItemFoodSetStick
        GOHEIMOCHI          (29,  6, 0.8f, false, "goheimochi", new ItemStack(Items.STICK));

        private static final EnumSetFood[] META_LOOKUP = new EnumSetFood[values().length];

    	private final int id;
        public int healAmount;
        public float saturationModifier;
        public boolean alwaysEdible;
    	private final String name;

        public ItemStack container = null;
        public int itemUseDuration = 32;
        public List<PotionEffectEntry> potionEffects = null;
        public boolean randomPotionEffect = false;
        public float randomPotionEffectProb = 0.0f;
        public double randomPotionProbTotal = 0.0D;

    	EnumSetFood(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name) {
    		this.id = id;
            this.healAmount = healAmount;
            this.saturationModifier = saturationModifier;
            this.alwaysEdible = alwaysEdible;
    		this.name= name;
    	}

    	EnumSetFood(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name, ItemStack container) {
    		this(id, healAmount, saturationModifier, alwaysEdible, name);
    		this.container = container;
    	}

        public int getId() {
        	return this.id;
        }

    	private EnumSetFood setMaxItemUseDuration(int i) {
    		this.itemUseDuration = i;
    		return this;
    	}

        public EnumSetFood addPotionEffect(Potion potion, int duration, int amplifier, float probability)
        {
            if (potionEffects == null)
            {
                this.potionEffects = Lists.newArrayList();
            }
            this.potionEffects.add(new PotionEffectEntry(potion, duration, amplifier, probability));
            this.randomPotionProbTotal += probability;
            return this;
        }

		@Override
		public String getName() {
			return this.name;
		}

    	public int getMetadata() {
    		return this.id;
    	}

        public ItemStack getContainerItem()
        {
            if (container != null)
            {
                return container.copy();
            }
            else
            {
                return null;
            }
        }

        public boolean hasContainerItem()
        {
            return container != null;
        }

        public int getDuration() {
        	return this.itemUseDuration;
        }

        public List<PotionEffect> getPotionEffect(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer)
        {
            World world = par2EntityPlayer.getEntityWorld();

            EnumSetFood food = EnumSetFood.byItemStack(par1ItemStack);
            List<PotionEffect> list = Lists.newArrayList();

            if (food.potionEffects != null)
            {
                if (!food.randomPotionEffect)
                {
                    // Stable
                    for (PotionEffectEntry entry : food.potionEffects)
                    {
                        if (world.rand.nextFloat() < entry.probability)
                        {
                            list.add(new PotionEffect(entry.potion, entry.duration * 20, entry.amplifier));
                        }
                    }
                }
                else if (world.rand.nextFloat() < food.randomPotionEffectProb)
                {
                    // Random
                    double total = food.randomPotionProbTotal;

                    for (PotionEffectEntry entry : food.potionEffects)
                    {
                        double rand = world.rand.nextDouble() * total;
                        if (rand < entry.probability)
                        {
                            list.add(new PotionEffect(entry.potion, entry.duration * 20, entry.amplifier));
                            break;
                        }
                        total -= entry.probability;
                    }
                }
            }
            return list;
        }



    	public static EnumSetFood byMetaData(int meta) {

            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

    		return EnumSetFood.META_LOOKUP[meta];
    	}

    	public static EnumSetFood byItemStack(ItemStack stack) {
    		return EnumSetFood.byMetaData(stack.getMetadata());
    	}

        public ItemStack getStack() {
        	return this.getStack(1);
        }

        public ItemStack getStack(int num) {
        	return new ItemStack(TcItems.foodSet, num, this.id);
        }

        static
        {

            ZUNDA_MOCHI.addPotionEffect(MobEffects.REGENERATION, 4, 2, 1.0F).setMaxItemUseDuration(64); // For External Mod
            KINAKO_MOCHI.setMaxItemUseDuration(64); // For External Mod
            TOFU_STEAK.setMaxItemUseDuration(48);


            for (EnumSetFood enumtype : values())
            {
                META_LOOKUP[enumtype.getMetadata()] = enumtype;
            }
        }

    }

}
