package remiliaMarine.tofu.item;

import java.util.ArrayList;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.achievement.TcAchievementMgr.Key;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.util.ModLog;

public class ItemKoujiBase extends TcItem {

    public ItemKoujiBase()
    {
        super();
        this.setMaxStackSize(1);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int inventorySlot, boolean isCurrentItem)
    {
        if ((par2World.getWorldTime() & 15) != 0) return;

        if (par3Entity instanceof EntityLivingBase && !isCurrentItem)
        {
            int damage = par1ItemStack.getItemDamage();
            if (damage < 60)
            {
                if (itemRand.nextInt(10) == 0)
                {
                    par1ItemStack.setItemDamage(damage + 1);
                    ModLog.debug("koujibase %d proceeded to step %d at %s", inventorySlot, damage + 1, par2World.isRemote ? "Client" : "Server");
                }
                //System.out.println(par1ItemStack.getItemDamage());
            }
            else
            {
                par1ItemStack.setItem(TcItems.kouji);
                par1ItemStack.setItemDamage(0);

                if (par3Entity instanceof EntityPlayer)
                {
                    TcAchievementMgr.achieve((EntityPlayer)par3Entity, Key.kouji);
                }
            }
        }
    }
    
    public static ModelResourceLocation[] getResourceLocation() {
    	ModelResourceLocation location = new ModelResourceLocation(TofuCraftCore.RES + "koujiBase", "inventory");
    	ArrayList<ModelResourceLocation> list = new ArrayList<ModelResourceLocation>();
    	for (int i = 0; i < 60; i++) {
    		list.add(location);
    	}
    	return list.toArray(new ModelResourceLocation[0]);
    }
}
