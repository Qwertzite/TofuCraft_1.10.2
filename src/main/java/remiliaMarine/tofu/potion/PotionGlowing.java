package remiliaMarine.tofu.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.Settings;
import remiliaMarine.tofu.glowtofu.GlowingHandler;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketGlowingFinish;

public class PotionGlowing extends Potion {

    public PotionGlowing(boolean isBadEffectIn, int liquidColour)
    {
        super(isBadEffectIn, liquidColour);
        this.setIconIndex(1, 0);
    }
    
    @Override
    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
    {
        int intv = par1EntityLivingBase.worldObj.isRemote ?
                Settings.clientGlowTofuLightInterval
                : Settings.serverGlowTofuLightInterval;

        if (par1EntityLivingBase.ticksExisted % intv == 0)
        {
            GlowingHandler.lightTarget(par1EntityLivingBase.worldObj, par1EntityLivingBase);
        }
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap par2BaseAttributeMap, int amplifier)
    {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, par2BaseAttributeMap, amplifier);
        
        GlowingHandler.removeLight(entityLivingBaseIn.worldObj, entityLivingBaseIn);
        
        PacketDispatcher.packet(new PacketGlowingFinish(entityLivingBaseIn.getEntityId()))
                .sendToAllInDimension(entityLivingBaseIn.dimension);
    }
    
    @Override
    public boolean isReady(int par1, int par2)
    {
        return true;
    }
    
    /**
     * Returns the index for the icon to display when the potion is active.
     */
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(TcPotion.TCPOTION_ICON);
        return super.getStatusIconIndex();
    }
}
