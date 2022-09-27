package remiliaMarine.tofu.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer.EnumModel;

public class BlockTfReformer extends BlockTfMachineWithMultipleTypesOnNBT {

	public static final PropertyEnum<TileEntityTfReformer.EnumModel> MODEL = PropertyEnum.<TileEntityTfReformer.EnumModel>create("model", TileEntityTfReformer.EnumModel.class);
	
    public static final String[] blockNames = new String[]{
            TileEntityTfReformer.EnumModel.SIMPLE.getName(), TileEntityTfReformer.EnumModel.MIX.getName()};

    private static boolean keepMachineInventory = false;

    public BlockTfReformer(boolean par2, SoundType sound)
    {
        super(par2);
        this.setSoundType(sound);
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfReformerActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfReformerIdle;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack stack, EnumFacing facing, float par7, float par8, float par9)
    {
        TileEntityTfReformer tile = (TileEntityTfReformer)worldIn.getTileEntity(pos);
        
        if (tile != null && (stack == null || !(stack.getItem() instanceof IFluidContainerItem)))
        {
            int guiId = tile.model == TileEntityTfReformer.EnumModel.MIX ? TcGuiHandler.GUIID_TF_REFORMER2 : TcGuiHandler.GUIID_TF_REFORMER;
            playerIn.openGui(TofuCraftCore.INSTANCE, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean par0, World par1World, BlockPos pos)
    {
    	IBlockState state =  par1World.getBlockState(pos);
        EnumFacing facing = state.getValue(FACING);
        boolean noDrop = state.getValue(NO_DROP);
        TileEntityTfReformer.EnumModel model = state.getValue(MODEL);
        
        TileEntity tileentity = par1World.getTileEntity(pos);
        keepMachineInventory = true;

        if (par0)
        {
            par1World.setBlockState(pos, TcBlocks.tfReformerActive.getDefaultState()
            		.withProperty(FACING, facing).withProperty(NO_DROP, noDrop).withProperty(MODEL, model));
        }
        else
        {
            par1World.setBlockState(pos, TcBlocks.tfReformerIdle.getDefaultState()
            		.withProperty(FACING, facing).withProperty(NO_DROP, noDrop).withProperty(MODEL, model));
        }

        keepMachineInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public void breakBlock(World par1World, BlockPos pos, IBlockState state)
    {
        super.breakBlock(par1World, pos, state, keepMachineInventory);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfReformer();
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabIn, List<ItemStack> list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public int damageDropped(IBlockState state) {
    	return state.getValue(MODEL).id;
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(MODEL, meta == 0 ? EnumModel.SIMPLE : EnumModel.MIX);
    }
	
    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
     */
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(MODEL).id);
    }
    
    // ======== blockstate ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront((meta & 3) +2);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }
        
        EnumModel model = (meta & 4) > 0 ? EnumModel.MIX : EnumModel.SIMPLE;

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(NO_DROP, (meta & 8) > 0).withProperty(MODEL, model);
    }

    @Override
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
    	int i = 0;
    	i += ((EnumFacing)state.getValue(FACING)).getIndex() -2;
    	i += state.getValue(NO_DROP) ? 8 : 0;
    	i += state.getValue(MODEL) == EnumModel.MIX ? 4 : 0;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, NO_DROP, MODEL});
    }

	@Override
	public String getVariantName(int meta) {
		return meta == 0 ? ".simple" : ".mix";
	}
}
