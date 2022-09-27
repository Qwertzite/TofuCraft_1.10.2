package remiliaMarine.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.tileentity.ITfConsumer;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import remiliaMarine.tofu.block.BlockTfOven;
import remiliaMarine.tofu.inventory.ContainerTfOven;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class TileEntityTfOven extends TileEntityTfMachineSidedInventoryBase implements ITfConsumer {
	
    public static final int SLOT_ITEM_INPUT = 0;
    public static final int SLOT_ITEM_RESULT = 1;
    public static final int SLOT_ACCELERATION = 2;

    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {1};

    public static final double COST_TF_PER_TICK = 0.025D;
    public static final double TF_CAPACITY = 20.0D;
    public static final int WHOLE_COOK_TIME_BASE = 200;

    public double ovenCookTime;
    public double wholeCookTime;
    public double tfPooled;
    public double tfPooledLast;
    private ItemStack lastInputItem = null;
    private boolean isTfNeeded;
    public boolean isWorking;
    private boolean prevWorking;

    public TileEntityTfOven()
    {
        this.itemStacks = new ItemStack[3];
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfOven";
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.getByte("Rev") == 1)
        {
            this.ovenCookTime = nbtTagCompound.getShort("CookTime");
            this.wholeCookTime = nbtTagCompound.getShort("CookTimeW");
        }
        else
        {
            this.ovenCookTime = nbtTagCompound.getFloat("CookTime");
            this.wholeCookTime = nbtTagCompound.getFloat("CookTimeW");
        }
        this.tfPooled = nbtTagCompound.getFloat("TfP");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setFloat("CookTime", (float)this.ovenCookTime);
        nbtTagCompound.setFloat("CookTimeW", (float)this.wholeCookTime);
        nbtTagCompound.setFloat("TfP", (float) this.tfPooled);
        return nbtTagCompound;
    }

    @Override
    protected int getNBTRevision()
    {
        return 2;
    }

    @SideOnly(Side.CLIENT)
    public double getCookProgressScaled()
    {
        return (double)this.ovenCookTime / (double)wholeCookTime;
    }

    @SideOnly(Side.CLIENT)
    public double getTfCharged()
    {
        return this.tfPooled / TF_CAPACITY;
    }

    public boolean isHeating()
    {
        return this.isWorking;
    }

    @Override
    public void update()
    {
        boolean updated = false;

        if (!this.worldObj.isRemote)
        {
            this.isWorking = false;

            if (this.canSmelt())
            {
                this.wholeCookTime = this.getWholeCookTime();

                // Cooking
                this.ovenCookTime += 1D;
                this.tfPooled -= this.getTfAmountNeeded();
                this.isWorking = true;

                if (this.ovenCookTime >= wholeCookTime)
                {
                    // Finish
                    this.ovenCookTime -= wholeCookTime;
                    this.smeltItem();
                    updated = true;
                }
            }
            else
            {
                // Stop cooking
                if (lastInputItem != this.itemStacks[SLOT_ITEM_INPUT] || this.itemStacks[SLOT_ITEM_INPUT] == null)
                {
                    this.ovenCookTime = 0;
                }
            }

            if (this.isWorking != this.prevWorking)
            {
                updated = true;
                BlockTfOven.updateMachineState(isHeating(), this.worldObj, this.getPos());
            }
            
            if(this.tfPooledLast != this.tfPooled) {
            	this.tfPooledLast =this.tfPooled;
            	this.sendChangesToClients();
            }

        }

        if (updated)
        {
            this.markDirty();
        }

        lastInputItem = itemStacks[SLOT_ITEM_INPUT];
        this.prevWorking = this.isWorking;
    }

    public boolean isAccelerated()
    {
        ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
        return ItemTcMaterials.EnumTcMaterialInfo.activatedHellTofu.isItemEqual(itemStack);
    }

    private double getWholeCookTime()
    {
        if (this.isAccelerated())
        {
            ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
            return WHOLE_COOK_TIME_BASE / (ItemStackAdapter.getSize(itemStack) * 1.5D);
        }
        return WHOLE_COOK_TIME_BASE;
    }

    public double getTfAmountNeeded()
    {
        if (this.isAccelerated())
        {
            ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
            return 5.0D / this.getWholeCookTime() + COST_TF_PER_TICK / 10.0D * Math.pow(1.1, ItemStackAdapter.getSize(itemStack));
        }
        return COST_TF_PER_TICK;
    }

    private boolean canSmelt()
    {
        isTfNeeded = false;
        if (this.itemStacks[SLOT_ITEM_INPUT] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.itemStacks[SLOT_ITEM_INPUT]);
            if (itemstack != null)
            {
                if (this.itemStacks[SLOT_ITEM_RESULT] != null)
                {
                    if (!this.itemStacks[SLOT_ITEM_RESULT].isItemEqual(itemstack)) return false;

                    int result = ItemStackAdapter.getSize(itemStacks[SLOT_ITEM_RESULT]) + ItemStackAdapter.getSize(itemstack);
                    if (result > getInventoryStackLimit() || result > this.itemStacks[SLOT_ITEM_RESULT].getMaxStackSize())
                    {
                        return false;
                    }

                }
                isTfNeeded = true;
                return tfPooled >= this.getTfAmountNeeded();
            }
        }
        return false;
    }

    public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.itemStacks[SLOT_ITEM_INPUT]);

        if (this.itemStacks[SLOT_ITEM_RESULT] == null)
        {
            this.itemStacks[SLOT_ITEM_RESULT] = itemstack.copy();
        }
        else if (this.itemStacks[SLOT_ITEM_RESULT].getItem() == itemstack.getItem())
        {
        	ItemStackAdapter.addSize(this.itemStacks[SLOT_ITEM_RESULT], ItemStackAdapter.getSize(itemstack));
        }

        if (ItemStackAdapter.preDecr(this.itemStacks[SLOT_ITEM_INPUT]) <= 0)
        {
            this.itemStacks[SLOT_ITEM_INPUT] = null;
        }
    }

    @Override
    public double getMaxTfCapacity()
    {
        return TF_CAPACITY - this.tfPooled;
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return isTfNeeded ? this.getTfAmountNeeded() : 0;
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfPooled += amount;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing facing)
    {
        return facing == EnumFacing.DOWN ? slotsBottom : (facing == EnumFacing.UP ? slotsTop : slotsSides);
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 != 1;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, EnumFacing side)
    {
        return slot == 1;
    }

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerTfOven(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return "tofucraft:tfOven";
	}
}
