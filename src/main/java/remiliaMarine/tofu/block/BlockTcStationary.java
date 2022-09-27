package remiliaMarine.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockTcStationary extends BlockFluidClassic {
    @SuppressWarnings("unused")
	private final String[] iconNames;
    //private IIcon[] theIcon;
    @SuppressWarnings("unused")
	private int fluidColor = 0xffffff;
    
    public BlockTcStationary(Fluid fluid, Material material, String[] iconNames)
    {
        super(fluid, material);
        this.iconNames = iconNames;
        disableStats();
    }
    
    public BlockTcStationary setColor(int color)
    {
        this.fluidColor = color;
        return this;
    }
    
//    @Override
//    public int getBlockColor()
//    {
//        return 16777215;
//    }
    
//    @Override
//    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        return fluidColor;
//    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos)
    {
        Block b = world.getBlockState(pos).getBlock();
        if (!(b instanceof BlockFluidBase))
        {
            if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
            
        }
        return super.canDisplace(world, pos);
    }
    
    @Override
    public boolean displaceIfPossible(World world, BlockPos pos)
    {
        Block b = world.getBlockState(pos).getBlock();
        if (!(b instanceof BlockFluidBase))
        {
            if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
        }
        return super.displaceIfPossible(world, pos);
    }
    
    
    
    
}
