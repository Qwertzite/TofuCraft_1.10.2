package remiliaMarine.tofu.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.item.TofuMaterial;

public class BlockTofuAnnin extends BlockTofu {
    
	public BlockTofuAnnin(TofuMaterial material)
    {
        super(material, SoundType.SNOW);
        this.setLightOpacity(2);
    }
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
    @Override
    public boolean isOpaqueCube(IBlockState stat)
    {
        return false;
    }
}
