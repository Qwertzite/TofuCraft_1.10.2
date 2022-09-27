package remiliaMarine.tofu.item;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcItems;

public class ItemGelatin extends TcItem {
	
    public ItemGelatin()
    {
        super();
        this.setHasSubtypes(true);
    }
	
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int dmg = stack.getItemDamage();
        return this.getFullName(dmg);
    };
    
    private String getFullName(int dmg)
    {
    	EnumGelatin info = EnumGelatin.byMetaData(dmg);
        return "item.tofucraft:" + info.getName();
    	//return this.getItemSetName() + "." + info.getName();
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	int length = EnumGelatin.values().length;
        for (int i = 0; i < length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
	
    public EnumGelatin[] getItemInfoArray() {
    	return EnumGelatin.getTypes();
    }
    
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation[] getResourceLocation() {
    	
    	EnumGelatin[] values = EnumGelatin.values();
    	ModelResourceLocation[] ret = new ModelResourceLocation[EnumGelatin.values().length];
    	int length = values.length;
    	
    	for (int i = 0; i < length; i++) {
    		ret[i] = new ModelResourceLocation(TofuCraftCore.RES + values[i].getName(), "inventory");
    	}
    	
    	return ret;
    }
    
	public static enum EnumGelatin {
		GELATIN(0, "gelatin"),
		GELATIN_RAW(1, "gelatinRaw");
		
        private static final EnumGelatin[] META_LOOKUP = new EnumGelatin[values().length];
        
        private int meta;
        private String name;
        
        EnumGelatin(int meta, String name) {
        	this.meta = meta;
        	this.name= name;
        }
        
		static
        {
            for (EnumGelatin enumtype : values())
            {
                META_LOOKUP[enumtype.getMetadata()] = enumtype;
            }
        }
		
		public int getMetadata() {
			return this.meta;
		}
		
		public String getName() {
			return this.name;
		}
		
    	public static EnumGelatin byMetaData(int meta) {
    		
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
    		
    		return META_LOOKUP[meta];
    	}
    	
        public ItemStack getStack(int num) {
        	return new ItemStack(TcItems.gelatin, num, this.meta);

        }
        
        public ItemStack getStack() {
        	return this.getStack(1);
        }		
    	
        public static EnumGelatin[] getTypes() {
        	return META_LOOKUP;
        }
		
	}
}
