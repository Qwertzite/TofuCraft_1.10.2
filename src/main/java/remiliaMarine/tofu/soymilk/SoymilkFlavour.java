package remiliaMarine.tofu.soymilk;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.iteminfo.SoymilkPlayerInfo;
import remiliaMarine.tofu.item.iteminfo.SoymilkPotionEffect;

public enum SoymilkFlavour {
	PLAIN  (0, "plain", 0xf5f7df, 2, 0.5F),
	KINAKO (1, "kinako", 0xd6bc2d, 4, 0.6F),
	COCOA  (2, "cocoa", 0x8d3d0d, 4, 0.6F),
	ZUNDA  (3, "zunda", 0x95e24a, 4, 0.6F),
	APPLE  (4, "apple", 0xf2e087, 4, 0.6F),
	PUMPKIN(5, "pumpkin", 0xffb504, 4, 0.6F),
	RAMUNE (6, "ramune", 0xa1c7ff, 4, 0.6F),
	STRAWBERRY(7, "strawberry", 0xf4a4b7, 4, 0.6F),
	SAKURA (8, "sakura", 0xffd1d7, 4, 0.6F),
	ANNIN  (9, "annin", 0xf5f7f3, 4, 0.6F),
	PUDDING(10, "pudding", 0xf2e087, 4, 0.6F),
	TEA    (11, "tea", 0xfed298, 4, 0.6F);
	
	private static final SoymilkFlavour[] META_LOOKUP = new SoymilkFlavour[values().length];
	
	public static void regieterEffectsAndGrades() {
		PLAIN.setPotionEffect(MobEffects.REGENERATION)
        		.addPotionEffectGrade( 0, 0,  15,  15,  5)
        		.addPotionEffectGrade( 5, 0,  40,  30, 10)
        		.addPotionEffectGrade(10, 0,  90,  60, 20)
        		.addPotionEffectGrade(15, 0, 190, 100, 25);
		
		KINAKO.setPotionEffect(MobEffects.SPEED)
	            .addPotionEffectGrade(0, 0,  60, 60, 30)
	            .addPotionEffectGrade(4, 1,  80, 60, 30)
	            .addPotionEffectGrade(8, 2, 120, 60, 15, 90);
		
		COCOA.setPotionEffect(MobEffects.JUMP_BOOST)
	            .addPotionEffectGrade(0, 0,  60, 60, 30)
	            .addPotionEffectGrade(4, 1,  80, 60, 30)
	            .addPotionEffectGrade(8, 2, 120, 60, 15, 90);

		ZUNDA.setPotionEffect(MobEffects.NIGHT_VISION)
	            .addPotionEffectGrade(0, 0, 20, 20, 5, 40);
		
		APPLE.setPotionEffect(MobEffects.RESISTANCE)
	            .addPotionEffectGrade(0, 0, 20, 20, 10)
	            .addPotionEffectGrade(4, 1, 30, 20,  5)
	            .addPotionEffectGrade(9, 2, 30, 15,  5, 20);
		
		PUMPKIN.setPotionEffect(MobEffects.STRENGTH)
	            .addPotionEffectGrade(0, 0, 45, 45, 15)
	            .addPotionEffectGrade(4, 1, 60, 30, 15)
	            .addPotionEffectGrade(8, 2, 80, 30,  5, 40);
		
		RAMUNE.setRandomPotionEffect();
		
		STRAWBERRY.setPotionEffect(MobEffects.HASTE)
	            .addPotionEffectGrade(0, 0, 45, 45, 15)
	            .addPotionEffectGrade(4, 1, 60, 30, 15)
	            .addPotionEffectGrade(8, 2, 90, 30,  5, 40);
		
		SAKURA.setPotionEffect(MobEffects.RESISTANCE)
	            .addPotionEffectGrade( 0, 0, 45, 30, 10)
	            .addPotionEffectGrade( 4, 1, 60, 30, 10)
	            .addPotionEffectGrade( 8, 2, 80, 30,  5)
	            .addPotionEffectGrade(14, 3, 80, 20,  4, 20);
		
		ANNIN.setPotionEffect(MobEffects.ABSORPTION)
	            .addPotionEffectGrade( 0, 0,  60, 60, 30)
	            .addPotionEffectGrade( 4, 1, 100, 60, 30)
	            .addPotionEffectGrade( 8, 2, 150, 60, 20)
	            .addPotionEffectGrade(13, 3, 180, 60, 10, 60);
		
		PUDDING.setPotionEffect(MobEffects.REGENERATION)
	            .addPotionEffectGrade( 0, 0, 20, 60, 20)
	            .addPotionEffectGrade( 4, 1, 45, 60, 15)
	            .addPotionEffectGrade(12, 2, 90, 60, 15, 90);
		
		TEA.setPotionEffect(MobEffects.WATER_BREATHING)
	            .addPotionEffectGrade( 0, 0, 30, 30, 6, 90);
	}
	
	private static List<SoymilkPotionEffect> potionEffectsForRandom = Lists.newArrayList();
	
	private int id;
	private String unlocalizedFlavourName;
	private int potionColour;
	private int healAmount;
	private float saturationModifier;
	
	private SoymilkPotionEffect potionEffect;
    private boolean isRandomPotionEffect = false;
    private Random rand = null;
	
	
	SoymilkFlavour(int id, String name, int colour, int healAmount, float saturationModifier) {
		this.id = id;
		this.unlocalizedFlavourName = name;
		this.potionColour = colour;
		this.healAmount = healAmount;
		this.saturationModifier = saturationModifier;
	}
	//methods used for registry
    public SoymilkFlavour setPotionEffect(Potion potion)
    {
        this.potionEffect = new SoymilkPotionEffect(potion);
        potionEffectsForRandom.add(this.potionEffect);
        return this;
    }

    public SoymilkFlavour addPotionEffectGrade(int tierFrom, int level, int initial, double baseMax, int bonusInc)
    {
        this.potionEffect.addGrade(tierFrom, level, initial, baseMax, bonusInc, -1);
        return this;
    }

    public SoymilkFlavour addPotionEffectGrade(int tierFrom, int level, int initial, double baseMax, int bonusInc, int bonusMax)
    {
        this.potionEffect.addGrade(tierFrom, level, initial, baseMax, bonusInc, bonusMax);
        return this;
    }
    
    public SoymilkFlavour setRandomPotionEffect()
    {
        this.isRandomPotionEffect = true;
        this.rand = new Random();
        this.rand.nextInt();
        return this;
    }
    
    
    //getter methods
    public static SoymilkFlavour getFromMeta(int meta) {
        
    	if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }
    	
    	return SoymilkFlavour.META_LOOKUP[meta];
    }
    
    public ItemStack getStack()
    {
        return this.getStack(1);
    }

    public ItemStack getStack(int qty)
    {
        return new ItemStack(SoymilkFlavour.getItemInstance(), qty, id);
    }
    
    public static Item getItemInstance()
    {
        return TcItems.bottleSoymilk;
    }
    
    
    public int getId() {
    	return this.id;
    }
    
    public String getUnlocalizedName() {
    	return this.unlocalizedFlavourName;
    }
    
    public int getPotionColour() {
    	return this.potionColour;
    }
    
    public int getHealAmount() {
    	return this.healAmount;
    }
    
    public float getSaturationModifier() {
    	return this.saturationModifier;
    }
    
    public PotionEffect getPotionEffect(SoymilkPlayerInfo info)
    {
        if (!isRandomPotionEffect)
        {
            if (this.potionEffect != null)
            {
                return this.potionEffect.getPotionEffect(info);
            }
            else
            {
                return null;
            }
        }
        else
        {
            SoymilkPotionEffect randomEffect = potionEffectsForRandom.get(rand.nextInt(potionEffectsForRandom.size()));
            return randomEffect.getPotionEffect(info);
        }
    }
    
    public boolean isRandomPotionEffect() {
    	return this.isRandomPotionEffect;
    }
    
    
    static
    {
        for (SoymilkFlavour soymilkFlavour$enumtype : values())
        {
            META_LOOKUP[soymilkFlavour$enumtype.getId()] = soymilkFlavour$enumtype;
        }
    }
    
    
}
