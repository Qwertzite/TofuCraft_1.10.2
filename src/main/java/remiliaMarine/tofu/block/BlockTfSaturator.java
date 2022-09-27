package remiliaMarine.tofu.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.entity.ParticleTofuSmoke;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcTextures;
import remiliaMarine.tofu.tileentity.TileEntityTfSaturator;

public class BlockTfSaturator extends BlockTfMachineBase {
	
    private static boolean keepMachineInventory = false;

    public BlockTfSaturator(boolean isActive, SoundType sound)
    {
        super(isActive);
        this.setSoundType(sound);
        if (isActive) setTickRandomly(true);
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfSaturatorActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfSaturatorIdle;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean par0, World worldIn, BlockPos pos)
    {
        EnumFacing facing = worldIn.getBlockState(pos).getValue(FACING);
        TileEntity var6 = worldIn.getTileEntity(pos);
        keepMachineInventory = true;

        if (par0)
        {
            worldIn.setBlockState(pos, TcBlocks.tfSaturatorActive.getDefaultState().withProperty(FACING, facing));
        }
        else
        {
            worldIn.setBlockState(pos, TcBlocks.tfSaturatorIdle.getDefaultState().withProperty(FACING, facing));
        }

        keepMachineInventory = false;

        if (var6 != null)
        {
            var6.validate();
            worldIn.setTileEntity(pos, var6);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
//        BlockUtils.onNeighborBlockChange_RedstoneSwitch(this, worldIn, pos, blockIn);
    }

    @Override
    public void breakBlock(World par1World, BlockPos pos, IBlockState state)
    {
        super.breakBlock(par1World, pos, state, keepMachineInventory);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos ,IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = worldIn.getTileEntity(pos);

        if (tile != null)
        {
            playerIn.openGui(TofuCraftCore.INSTANCE, TcGuiHandler.GUIID_TF_SATURATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
        if (this.isActive)
        {
            this.spoutSmoke(par1World, pos.getX(), pos.getY(), pos.getZ() , par5Random);
        }
    }

    @SideOnly(Side.CLIENT)
    public void spoutSmoke(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        for (int i = 0; i < par5Random.nextInt(3) + 1; i++)
        {
            double t = par5Random.nextDouble() * 2.0D * Math.PI;
            double d0 = (double) par2 + 0.5D;
            double d1 = (double) par3 + 0.3D + par5Random.nextDouble();
            double d2 = (double) par4 + 0.5D;
            double d3 = Math.sin(t) / 64.0D;
            double d4 = 0.05D + par5Random.nextDouble() * 0.05D;
            double d5 = Math.cos(t) / 64.0D;
            ParticleTofuSmoke entityFX = new ParticleTofuSmoke(par1World, d0, d1, d2, d3, d4, d5);

            entityFX.setParticleTexture(TcTextures.tofuSmoke);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(entityFX);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfSaturator();
    }
}
