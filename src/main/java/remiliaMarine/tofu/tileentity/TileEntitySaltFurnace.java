package remiliaMarine.tofu.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.block.BlockSaltFurnace;
import remiliaMarine.tofu.fluids.FluidUtils;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.inventory.ContainerSaltFurnace;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class TileEntitySaltFurnace extends TileEntityLockable implements ITickable, ISidedInventory, IFluidHandler {
	/**�ｿｽA�ｿｽN�ｿｽZ�ｿｽX�ｿｽﾅゑｿｽ�ｿｽ�ｿｽX�ｿｽ�ｿｽ�ｿｽb�ｿｽg�ｿｽﾌ番搾ｿｽ�ｿｽﾌ配�ｿｽ�ｿｽ
	 * 0=Fuel, 1=Salt output, 2=Glass bottle, 3=Nigari output */
    private static final int[] SLOTS_TOP = new int[] {0, 2};
    private static final int[] SLOTS_SIDE = new int[] {0, 1, 2, 3};
    private static final int[] SLOTS_BOTTOM = new int[] {1, 3};
    private static final FluidStack nigari = new FluidStack(TcFluids.NIGARI, 10);

    public FluidTank nigariTank = new FluidTank(TcFluids.NIGARI, 0, 120);
    /** The ItemStacks that hold the items currently being used in the furnace
     * 0=Fuel, 1=Salt output, 2=Glass bottle, 3=Nigari output*/
    private ItemStack[] furnaceItemStacks = new ItemStack[4];
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime = 0;
    private int cookTime = 0;
    private int totalCookTime;
    /**-1=noCauldron, 0-3=waterlevel*/
    private int lastCauldronStatus = 0;
    private String customName;


    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update()
    {
    	int cauldronStat = this.getCauldronStatus();
    	int burnTime = this.furnaceBurnTime;
    	boolean wasBurning = burnTime > 0;
        boolean isDirty = false;

    	if(burnTime > 0) burnTime--;

        if (!this.worldObj.isRemote) {

        	if(burnTime == 0 && this.canBoil(cauldronStat)) {

    			ItemStack fuleStack = this.furnaceItemStacks[0];
        		this.currentItemBurnTime = burnTime = TileEntityFurnace.getItemBurnTime(fuleStack);

        		if(burnTime > 0) {
        			isDirty = true;

        			if(fuleStack != null) {

        				if (ItemStackAdapter.preDecr(fuleStack) == 0)
        				{
        					this.furnaceItemStacks[0] = fuleStack.getItem().getContainerItem(fuleStack);
        				}
        			}
        		}
        	}
        	this.furnaceBurnTime = burnTime;

			if (this.isBurning() && this.canBoil(cauldronStat) && this.getCauldronStatus() >= 1) {
				this.cookTime++;

				if (this.cookTime == 200) {
					this.cookTime = 0;
					this.boilDown();
					isDirty = true;
				}
			} else {
				this.cookTime = 0;
			}

			if (wasBurning != this.furnaceBurnTime > 0)
			{
				isDirty = true;
				BlockSaltFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.pos);
			}

			if (this.isBurning()) {

				if (cauldronStat == -1) {
					this.worldObj.setBlockState(this.pos.up(), Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 0));
				} else if (cauldronStat == -2) {
					if (this.worldObj.rand.nextInt(200) == 0) {
						this.worldObj.setBlockState(this.pos.up(2), Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 0));
					}
				}
			}

			// Output nigari
			if (this.canOutputNigari()) {
				this.outputNigari();
			}
        }

        this.updateCauldronStatus();

        if (isDirty)
        {
            this.markDirty();
        }
        this.furnaceBurnTime = burnTime;
    }

    /**
     * Returns the number of slots in the inventory.
     */
	@Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

	@Override
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks[index];
    }

	@Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

	@Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]);
		this.furnaceItemStacks[index] = stack;

        if (stack != null && ItemStackAdapter.getSize(stack) > this.getInventoryStackLimit())
        {
        	ItemStackAdapter.setSize(stack, this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.markDirty();
        }
	}
    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

	@Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i == 0 ? TileEntityFurnace.isItemFuel(itemstack) : i == 2 ? FluidUtils.isContainerForFluid(nigari.copy(), itemstack) : false;
    }
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }
    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

	@Override
	public int getFieldCount() {
		return 4;
	}

    public void clear()
    {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            this.furnaceItemStacks[i] = null;
        }
        this.markDirty();
    }

	@Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.tofucraft.SaltFurnace";
    }

    /**
     * Returns true if this thing is named
     */
	@Override
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomInventoryName(String name)
    {
        this.customName = name;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerSaltFurnace(playerInventory, this);
    }

    @Override
    public String getGuiID()
    {
        return "tofucraft:saltFurnace";
    }

	@Override
    public int[] getSlotsForFace(EnumFacing side)
    {
		switch(side){
		case DOWN:
			return SLOTS_BOTTOM;
		case UP:
			return SLOTS_TOP;
		default:
			return SLOTS_SIDE;
		}
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
	@Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return index == 1 || direction == EnumFacing.DOWN;
    }

    public static void registerFixesFurnace(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("SaltFurnace", new String[] {"Items"}));
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbttaglist.getCompoundTagAt(i);
            int var5 = nbtTagCompound.getByte("Slot");

            if (var5 >= 0 && var5 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
            }
        }

        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.cookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = par1NBTTagCompound.getShort("ItemBurnTime");;
        this.nigariTank.readFromNBT(par1NBTTagCompound.getCompoundTag("NigariTank"));

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        par1NBTTagCompound.setInteger("ItemBurnTime", this.currentItemBurnTime);

        NBTTagCompound nigariTag = this.nigariTank.writeToNBT(new NBTTagCompound());
        par1NBTTagCompound.setTag("NigariTank", nigariTag);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(var4);
                tagList.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", tagList);

        if (this.hasCustomName())
        {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }

        return par1NBTTagCompound;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
       NBTTagCompound nbtTagCompound = new NBTTagCompound();
       this.writeToNBT(nbtTagCompound);
       return new SPacketUpdateTileEntity(pos, this.getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
       return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
       this.readFromNBT(pkt.getNbtCompound());
    }
    
    /**Returns cook Progress in integer between 0 and par1*/
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.cookTime * par1 / 200;
    }
    /**Returns remaining burn time in integer between 0 and par1*/
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public FluidTank getNigariTank()
    {
        return this.nigariTank;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    /**returns true when it is possible to boil.*/
    private boolean canBoil(int cauldronStat)
    {
        if (cauldronStat == 0 || cauldronStat <= -3) return false;//no water or no air for fire

        ItemStack stack = new ItemStack(TcItems.salt, 2);
        if (this.furnaceItemStacks[1] == null) return true;//slot empty
        if (!this.furnaceItemStacks[1].isItemEqual(stack)) return false;//wrong item(s) present at salt slot.
        int result = ItemStackAdapter.getSize(furnaceItemStacks[1]) + ItemStackAdapter.getSize(stack);
        return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
    }

    public void boilDown()
    {
        if (this.canBoil(this.getCauldronStatus()))
        {
            ItemStack stack = new ItemStack(TcItems.salt, 2);

            if (this.furnaceItemStacks[1] == null)
            {
                this.furnaceItemStacks[1] = stack.copy();
            }
            else if (this.furnaceItemStacks[1].isItemEqual(stack))
            {
            	ItemStackAdapter.addSize(furnaceItemStacks[1], ItemStackAdapter.getSize(stack));
            }
            this.nigariTank.fill(new FluidStack(TcFluids.NIGARI, 20), true);
            this.sendTankChangeToClients();

            // Decrease the water in the cauldron
            if (!worldObj.isRemote)
            {
                int waterLevel = this.getCauldronWaterLevel();
                this.setCauldronWaterLevel(waterLevel - 1);
            }
        }
    }

    private boolean canOutputNigari()
    {
        ItemStack containerStack = this.furnaceItemStacks[2];

        if (containerStack != null && ItemStackAdapter.getSize(containerStack) > 0)
        {
            @SuppressWarnings("deprecation")
			ItemStack filledStack = FluidContainerRegistry.fillFluidContainer(nigariTank.getFluid(), containerStack);

            if (filledStack == null) return false;
            @SuppressWarnings("deprecation")
            int containerCapacity = FluidContainerRegistry.getContainerCapacity(filledStack);

            if (this.nigariTank.getFluidAmount() < containerCapacity) return false;
            if (furnaceItemStacks[3] == null) return true;
            int result = ItemStackAdapter.getSize(furnaceItemStacks[3]) + ItemStackAdapter.getSize(filledStack);
            return result <= getInventoryStackLimit() && result <= filledStack.getMaxStackSize();
        }
        return false;
    }

    public void outputNigari()
    {
        ItemStack containerStack = this.furnaceItemStacks[2];
        @SuppressWarnings("deprecation")
		ItemStack filledStack = FluidContainerRegistry.fillFluidContainer(nigariTank.getFluid(), containerStack);
        @SuppressWarnings("deprecation")
		int containerCapacity = FluidContainerRegistry.getContainerCapacity(filledStack);
        if (this.furnaceItemStacks[3] == null)
        {
            this.furnaceItemStacks[3] = filledStack.copy();
        }
        else if (this.furnaceItemStacks[3].isItemEqual(filledStack))
        {
        	ItemStackAdapter.addSize(furnaceItemStacks[3], ItemStackAdapter.getSize(filledStack));
        }

        this.nigariTank.drain(containerCapacity, true);
        this.sendTankChangeToClients();

        ItemStackAdapter.addSize(furnaceItemStacks[2], -1);
        if (ItemStackAdapter.getSize(furnaceItemStacks[2]) == 0)
        {
            furnaceItemStacks[2] = null;
        }
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }



    /*
     * === Cauldron Control ===
     */
    private void updateCauldronStatus()
    {
        int stat = this.getCauldronStatus();

        if (this.lastCauldronStatus != stat && this.worldObj.getBlockState(this.pos.up()).getBlock() == Blocks.CAULDRON)
        {
            int metadata = this.worldObj.getBlockState(this.pos.up()).getValue(BlockCauldron.LEVEL);
            IBlockState newMetadata = Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, metadata);
            this.worldObj.setBlockState(this.pos.up(), newMetadata, 2);
            this.lastCauldronStatus = stat;
        }
    }

    /**returns water level without checking wether there is a cauldron or not. */
    private int getCauldronWaterLevel()
    {
        return worldObj.getBlockState(this.pos.up()).getValue(BlockCauldron.LEVEL);
    }

    private void setCauldronWaterLevel(int level)
    {
        worldObj.setBlockState(this.pos.up(), this.worldObj.getBlockState(this.pos.up()).withProperty(BlockCauldron.LEVEL, level), 2);
    }

    /**gets the water Level of the cauldron above this block
     * @return water level. 0-3, int.
     * If there is no cauldron, returns negative value. -1, -2: must put fire one/two blck above, -3: inflammable*/
    @SuppressWarnings("deprecation")
	public int getCauldronStatus()
    {
    	IBlockState stateUp = this.worldObj.getBlockState(this.pos.up());
    	if (stateUp.getBlock() == Blocks.CAULDRON)
    			return stateUp.getValue(BlockCauldron.LEVEL);
    	else {
        	Block block = stateUp.getBlock();
    		if (block.isAir(stateUp, worldObj, this.getPos().up()) || block == Blocks.FIRE) return -1;
        	else if (!block.getMaterial(stateUp).getCanBurn()) return -3;
        	else {
        		IBlockState state2Up = this.worldObj.getBlockState(this.pos.up(2));
        		if (state2Up.getBlock().isAir(state2Up, worldObj, this.pos.up(2))) return -2;
        		else return -3;
        	}
    	}
    }
    /**
     * @param BlockPos BlockPos of SALF FURNACE
     * @return water level. 0-3, int.
     * If there is no cauldron, returns negative value. -1, -2: fire one/two blck above, -3: inflammable*/
    @SuppressWarnings("deprecation")
	public static int getCauldronStatus(BlockPos pos, World worldIn) {
    	IBlockState stateUp = worldIn.getBlockState(pos.up());
    	if (stateUp.getBlock() == Blocks.CAULDRON)
    			return stateUp.getValue(BlockCauldron.LEVEL);
    	else {
        	Block block = stateUp.getBlock();
    		if (block.isAir(stateUp, worldIn, pos.up())) return -1;
        	else if (!block.getMaterial(stateUp).getCanBurn()) return -3;
        	else {
        		IBlockState state2Up = worldIn.getBlockState(pos.up(2));
        		Block block2Up = state2Up.getBlock();
        		if (block2Up.isAir(state2Up, worldIn, pos.up(2))) return -2;
        		else return -3;
        	}
    	}
    }
    /*
     * === Fluid Handler ===
     */

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.nigariTank.getTankProperties();
	}
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
        if ((resource == null) || (!resource.isFluidEqual(nigariTank.getFluid())))
        {
            return null;
        }

        if (!canDrain(resource.getFluid())) return null;

        return nigariTank.drain(resource.amount, doDrain);
	}
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
        return nigariTank.drain(maxDrain, doDrain);
	}

    public boolean canDrain(Fluid fluid)
    {
        return nigariTank.getFluid().getUnlocalizedName() == fluid.getUnlocalizedName();
    }

    protected void sendTankChangeToClients() {

		if (!this.hasWorldObj() || this.worldObj.isRemote) return;

		List<EntityPlayer> list = this.getWorld().playerEntities;
		for (EntityPlayer player : list) {
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).connection.sendPacket(this.getUpdatePacket());
			}
		}

     }

}
