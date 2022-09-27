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

/**
 * Material items with no function
 */
public class ItemTcMaterials extends TcItem implements ITcItemColoured {

    public ItemTcMaterials()
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
    	EnumTcMaterialInfo info = EnumTcMaterialInfo.byMetaData(dmg);
        return "item.tofucraft:" + info.getName();
    	//return this.getItemSetName() + "." + info.getName();
    }
    
    protected String getItemSetName() {
    	return this.getUnlocalizedName();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	int length = EnumTcMaterialInfo.values().length;
        for (int i = 0; i < length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    public EnumTcMaterialInfo[] getItemInfoArray() {
    	return EnumTcMaterialInfo.getTypes();
    }
    
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation[] getResourceLocation() {
    	
    	EnumTcMaterialInfo[] values = EnumTcMaterialInfo.values();
    	ModelResourceLocation[] ret = new ModelResourceLocation[EnumTcMaterialInfo.values().length];
    	int length = values.length;
    	
    	for (int i = 0; i < length; i++) {
    		ret[i] = new ModelResourceLocation(TofuCraftCore.RES + values[i].getResName(), "inventory");
    	}
    	
    	return ret;
    }
	
	@Override
	public int getItemColour(int meta) {
		return EnumTcMaterialInfo.byMetaData(meta).getItemColour();
	}
	
	public static enum EnumTcMaterialInfo {
	    tofuGem(0, "tofuGem"),
	    tofuDiamondNugget(1, "tofuDiamondNugget"),
	    tofuHamburgRaw(2, "tofuHamburgRaw"),
	    tfCapacitor(3, "tfCapacitor"),
	    tfCircuit(4, "tfCircuit"),
	    tfCoil(5, "tfCoil"),
	    tfOscillator(6, "tfOscillator"),
	    advTofuGem(7, "advTofuGem"),
	    activatedTofuGem(8, "activatedTofuGem"),
	    activatedHellTofu(9, "activatedHellTofu"),
	    mineralSoymilk(10, "mineralSoymilk", 0xeaad72, "coloured_bottle"),
	    tofuSomen(11, "tofuSomen"),
	    glassBowl(12, "glassBowl"),
//TODO	    rollingPin(13, "rollingPin").setNonDurabilityTool()
	    ;
		
        private static final EnumTcMaterialInfo[] META_LOOKUP = new EnumTcMaterialInfo[values().length];

		private int meta;
		private String name;
		private int potionColour = 0xffffff;
		private String resName;
		
		EnumTcMaterialInfo(int meta, String name) {
			this.meta = meta;
			this.name = name;
			this.resName = name;
		}
		
		EnumTcMaterialInfo(int meta, String name, int colour, String resname) {
			this.meta = meta;
			this.name = name;
			this.potionColour = colour;
			this.resName = resname;
		}

		public int getMetadata() {
			return this.meta;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getItemColour() {
			return this.potionColour;
		}
		
		public String getResName() {
			return this.resName;
		}
		
    	public static EnumTcMaterialInfo byMetaData(int meta) {
    		
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
    		
    		return EnumTcMaterialInfo.META_LOOKUP[meta];
    	}
        
        public static EnumTcMaterialInfo[] getTypes() {
        	return META_LOOKUP;
        }
        
        public ItemStack getStack(int num) {
        	return new ItemStack(TcItems.materials, num, this.meta);

        }
        
        public ItemStack getStack() {
        	return this.getStack(1);
        }
        
        public boolean isItemEqual(ItemStack itemStack)
        {
            return itemStack != null && itemStack.getItem() == TcItems.materials && itemStack.getItemDamage() == this.meta;
        }
        
		static
        {
            for (EnumTcMaterialInfo enumtype : values())
            {
                META_LOOKUP[enumtype.getMetadata()] = enumtype;
            }
        }
	}
}
