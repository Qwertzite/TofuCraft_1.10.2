package remiliaMarine.tofu.item;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import remiliaMarine.tofu.block.BlockTofuBase;
import remiliaMarine.tofu.data.TofuInfo;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;

public enum TofuMaterial implements IStringSerializable{
    KINU("kinu"),
    MOMEN("momen"),
    ISHI("ishi"),
    METAL("metal"),
    GRILLED("grilled"),
    DRIED("dried"),
    FRIEDPOUCH("friedPouch"),
    FRIED("fried"),
    EGG("egg"),
    ANNIN("annin"),
    SESAME("sesame"),
    ZUNDA("zunda"),
    STRAWBERRY("strawberry"),
    HELL("hell"),
    GLOW("glow"),
    DIAMOND("diamond"),
    MISO("miso");

	public String name;

    TofuMaterial(String name)
    {
    	this.name= name;
    }
//TODO
    public BlockTofuBase getBlock()
    {
        return this.getBlockInfo().getBlock();
    }

    public TofuInfo getBlockInfo()
    {
        return TcBlocks.tofuInfoMap.get(this);
    }

    public Item getItem()
    {
        return TcItems.TOFU_ITEMS.get(this);
    }

    public static TofuMaterial get(int id)
    {
        return values()[id];
    }

    public int id()
    {
        return this.ordinal();
    }

    @Override
	public String getName() {
		return this.name;
	}

    public static TofuMaterial byMetadata(int meta) {
    	if(meta < 0 || meta >= TofuMaterial.values().length) meta = 0;
    	return TofuMaterial.values()[meta];
    }
}
