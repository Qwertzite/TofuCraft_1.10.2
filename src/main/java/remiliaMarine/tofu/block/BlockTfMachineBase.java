package remiliaMarine.tofu.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineBase;
import remiliaMarine.tofu.entity.ParticleWhiteSmoke;
import remiliaMarine.tofu.tileentity.TileEntitySaltFurnace;
import remiliaMarine.tofu.tileentity.TileEntityTfAntenna;
import remiliaMarine.tofu.util.TileScanner;

public abstract class BlockTfMachineBase extends BlockContainer4Directions {
	
    protected final Random machineRand = new Random();
    protected final boolean isActive;
    private boolean hasGuiScreen;
    private int guiId;

    protected BlockTfMachineBase(boolean isActive)
    {
        super(Material.IRON);
        this.isActive = isActive;
    }

    public BlockTfMachineBase setGuiScreen(int guiId)
    {
        this.guiId = guiId;
        this.hasGuiScreen = true;
        return this;
    }

    @Override
    public Item getItemDropped(IBlockState stateIn, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this.getBlockIdle());
    }

    abstract protected Block getBlockActive();

    abstract protected Block getBlockIdle();

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (hasGuiScreen)
        {
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile != null)
            {
                playerIn.openGui(TofuCraftCore.INSTANCE, this.guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    @Override
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
        if (this.isActive)
        {
            //int metadata = par1World.getBlockMetadata(par2, par3, par4);
            //int direction = metadata & 3;
            float var7 = pos.getX() + 0.5F;
            float var8 = pos.getY() + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float var9 = pos.getZ() + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
            ParticleWhiteSmoke fx;

            fx = new ParticleWhiteSmoke(par1World, (var7 - var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new ParticleWhiteSmoke(par1World, (var7 + var10), var8, (var9 + var11), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new ParticleWhiteSmoke(par1World, (var7 + var11), var8, (var9 - var10), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            fx = new ParticleWhiteSmoke(par1World, (var7 + var11), var8, (var9 + var10), 0.0D, 0.0D, 0.0D);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, pos, state, par5EntityLivingBase, par6ItemStack);

        TileEntity tileEntity = par1World.getTileEntity(pos);

        if (tileEntity instanceof TileEntityTfMachineBase)
        {
            TileEntityTfMachineBase machineTE = (TileEntityTfMachineBase) tileEntity;
            if (par6ItemStack.hasDisplayName())
            {
                machineTE.setCustomName(par6ItemStack.getDisplayName());
            }
            machineTE.onTileEntityCreated(par1World, pos, par5EntityLivingBase, par6ItemStack);
        }

        this.connectToTfAntenna(tileEntity);
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state, boolean keepinventory)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (!keepinventory && te instanceof TileEntityTfMachineBase)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityTfMachineBase)te);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    public void connectToTfAntenna(final TileEntity tileEntity)
    {
        TileScanner scanner = new TileScanner(tileEntity.getWorld(), tileEntity.getPos());
        scanner.scan(BlockTfAntenna.MAX_RADIUS, TileScanner.Method.full, new TileScanner.Impl<Object>()
        {
            @Override
            public void apply(World world, BlockPos pos)
            {
                TileEntity t = world.getTileEntity(pos);
                if (t instanceof TileEntityTfAntenna)
                {
                    ((TileEntityTfAntenna)t).registerMachine(tileEntity);
                }
            }
        });
    }

    protected boolean isRedstonePowered(World world, BlockPos pos)
    {
        return world.isBlockPowered(pos);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState par5, World par1World, BlockPos pos)
    {
        return Container.calcRedstoneFromInventory((IInventory) par1World.getTileEntity(pos));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(getBlockIdle()));
    }

}
