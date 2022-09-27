package remiliaMarine.tofu.potion;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.init.TcMobEffects;
import remiliaMarine.tofu.item.ItemTcFood;

public class TcPotion {

    public static final ResourceLocation TCPOTION_ICON = new ResourceLocation("tofucraft:textures/gui/tcInventory.png");
	
    public static void register(Configuration conf)
    {
        TcMobEffects.FILLING = new PotionFilling(false, 0xffa734).setPotionName("potion.filling");
        GameRegistry.register(TcMobEffects.FILLING, new ResourceLocation(TofuCraftCore.MODID, "filling"));
        
        TcMobEffects.GLOWING = new PotionGlowing(false, 0xcccc00).setPotionName("potion.glowing");
        GameRegistry.register(TcMobEffects.GLOWING, new ResourceLocation(TofuCraftCore.MODID, "glowing"));
    	
    	// PotionType
//TODO        TcMobEffects.TcPotionTypes.TYPE_FILLING = new PotionType("filling", new PotionEffect(TcMobEffects.FILLING, 4800));
//        TcMobEffects.TcPotionTypes.TYPE_FILLING = new PotionType("filling", new PotionEffect[0]);
//        GameRegistry.register(TcMobEffects.TcPotionTypes.TYPE_FILLING, new ResourceLocation(TofuCraftCore.MODID, "filling"));
    	
//TODO        TcMobEffects.TcPotionTypes.TYPE_GLOWING = new PotionType("glowing", new PotionEffect(TcMobEffects.GLOWING, 3600));
//        TcMobEffects.TcPotionTypes.TYPE_GLOWING = new PotionType("glowing", new PotionEffect[0]);
//        GameRegistry.register(TcMobEffects.TcPotionTypes.TYPE_GLOWING, new ResourceLocation(TofuCraftCore.MODID, "glowing"));
        
        if (TcMobEffects.GLOWING != null)
        {
            ((ItemTcFood) TcItems.tofuGlow).setPotionEffect(new PotionEffect(TcMobEffects.GLOWING, 4800, 0), 1.0F);
        }

        if (TcMobEffects.FILLING != null)
        {
            ((ItemTcFood) TcItems.tofuMiso).setPotionEffect(new PotionEffect(TcMobEffects.FILLING, 6000, 0), 1.0F);
        }
        
    }
}
