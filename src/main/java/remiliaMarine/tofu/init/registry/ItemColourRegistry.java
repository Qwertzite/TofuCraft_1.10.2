package remiliaMarine.tofu.init.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ITcItemColoured;
import remiliaMarine.tofu.item.ItemBottleSoymilk;

public class ItemColourRegistry {
	public static void register() {
		
		//LoaderFood
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
				new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack, int tintIndex) {
						if(tintIndex == 0) return ((ItemBottleSoymilk)stack.getItem()).getColour(stack.getMetadata());
						else return -1;
					}
				}, TcItems.bottleSoymilk);
		
		
		//LoaderMaterial
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
        		new IItemColor() {
        			@Override
        			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        				if(tintIndex == 0) return 0x809cff;
        				else return -1;
        			}
        		},
        		TcItems.nigari);
        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBottleColor(), TcItems.bottleSoySauce);        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBottleColor(), TcItems.defattingPotion);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBottleColor(), TcItems.soyOil);        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBottleColor(), TcItems.dashi);        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBottleColor() , TcItems.materials);
        
        
	}
	
	public static class ItemBottleColor implements IItemColor 
	{
		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			if(tintIndex == 0) return ((ITcItemColoured)stack.getItem()).getItemColour(stack.getMetadata());
			else return -1;
		}
	}
}
