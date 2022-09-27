package remiliaMarine.tofu.init;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.util.ModLog;

public class TcTextures {
    public static final ResourceLocation tfSign = new ResourceLocation("tofucraft", "textures/gui/tfsign.png");
    public static final ResourceLocation tfMachineGui = new ResourceLocation("tofucraft", "textures/gui/tfMachine.png");

    public static TextureAtlasSprite tofuSmoke;
    public static TextureAtlasSprite nigari;

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Pre event)
    {
    	
//        int type = event.getMap().getTextureType();
//
//        if (type == 1)
        {
            registerIcons(event.getMap());
        }
    }

    public void registerIcons(TextureMap par1IconRegister)
    {
    	tofuSmoke = par1IconRegister.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "particle/smokeTofu"));
    	//nigari = par1IconRegister.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari"));
//    	nigari = par1IconRegister.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/blockSalt"));
    	ModLog.debug("particleTofu texture registered");
    }


}
