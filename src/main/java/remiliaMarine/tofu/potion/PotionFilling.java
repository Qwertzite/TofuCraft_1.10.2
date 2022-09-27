package remiliaMarine.tofu.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionFilling extends Potion {
	
    int[] intervalList = new int[]{200, 100, 60, 20, 5};
    
    protected PotionFilling(boolean isBadEffectIn, int colour)
    {
        super(isBadEffectIn, colour);
        this.setIconIndex(0, 0);
    }
    
    @Override
    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
    {
        if (par1EntityLivingBase instanceof EntityPlayer)
        {
            if (!par1EntityLivingBase.worldObj.isRemote)
            {
                ((EntityPlayer)par1EntityLivingBase).getFoodStats().addStats(par2 + 1, 0.2F);
            }
        }
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int intv = intervalList[MathHelper.clamp_int(amplifier, 0, intervalList.length - 1)];
        return duration % intv == 0;
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
