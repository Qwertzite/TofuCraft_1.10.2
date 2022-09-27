package remiliaMarine.tofu.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipe;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipeRegistry;
import remiliaMarine.tofu.api.tileentity.ITfConsumer;
import remiliaMarine.tofu.api.tileentity.ITfInputIndicator;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import remiliaMarine.tofu.block.BlockTfCondenser;
import remiliaMarine.tofu.data.ContainerParamBool;
import remiliaMarine.tofu.fluids.FluidUtils;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.inventory.ContainerTfCondenser;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class TileEntityTfCondenser extends TileEntityTfMachineSidedInventoryBase implements ITfConsumer, ITfInputIndicator {
	
    public static final int SLOT_NIGARI_INPUT = 0;
    public static final int SLOT_NIGARI_OUTPUT = 1;
    public static final int SLOT_SPECIAL_INPUT = 2;
    public static final int SLOT_SPECIAL_OUTPUT = 3;
    public static final int SLOT_TOFU_OUTPUT = 4;

    public static final int NIGARI_COST_MB = 5;
    public static final int TANK_CAPACITY_NIGARI = 200;
    public static final int TANK_CAPACITY_INGREDIENT = 10000;

    public static final int[] slotsTop = new int[]{0, 2};
    public static final int[] slotsSides = new int[]{0, 1, 2, 3, 4};
    public static final int[] slotsBottom = new int[]{1, 3, 4};

    public double tfPooled = 0;
    public double tfNeeded = 0;
    public int processTimeOutput = 0;

    public int wholeTimeOutput = 0;
    public FluidTank nigariTank = new FluidTank(0);
    public FluidTank ingredientTank = new FluidTank(0);
    public ItemStack ingredientFluidItem;
    private boolean isTfCharged = false;
    public boolean isWorking = false;
    private boolean prevWorking;

    public boolean paramTfPow;
    public ContainerParamBool paramTfPowered = new ContainerParamBool(6, false);
    private boolean isTfPoweredInternal = false;

    public TileEntityTfCondenser()
    {
        this.itemStacks = new ItemStack[5];

        this.nigariTank.setFluid(new FluidStack(TcFluids.NIGARI, 0));
        this.nigariTank.setCapacity(TANK_CAPACITY_NIGARI);
        this.ingredientTank.setCapacity(TANK_CAPACITY_INGREDIENT);
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfCondenser";
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        this.processTimeOutput = nbtTagCompound.getShort("ProcO");
        this.tfPooled = nbtTagCompound.getDouble("TfP");
        this.tfNeeded = nbtTagCompound.getDouble("TfN");
        this.nigariTank.readFromNBT(nbtTagCompound.getCompoundTag("Tank1"));
        this.ingredientTank.readFromNBT(nbtTagCompound.getCompoundTag("Tank2"));
        this.paramTfPow = nbtTagCompound.getBoolean("ParamTfPow");
        this.updateIngredientItem();
    }

    /**
     * Writes a tile entity to NBT.
     * @return 
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("ProcO", (short) this.processTimeOutput);
        nbtTagCompound.setShort("WholeO", (short) this.wholeTimeOutput);
        nbtTagCompound.setDouble("TfP", this.tfPooled);
        nbtTagCompound.setDouble("TfN", this.tfNeeded);
        nbtTagCompound.setBoolean("ParamTfPow", this.paramTfPow);

        NBTTagCompound tag1 = this.nigariTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("Tank1", tag1);

        NBTTagCompound tag2 = this.ingredientTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("Tank2", tag2);
        return nbtTagCompound;
    }

    public boolean isProcessing()
    {
        return this.isWorking;
    }

    @Override
    public void update()
    {
        boolean isInventoryChanged = false;
        this.isWorking = false;

        if (!this.worldObj.isRemote)
        {
            if (this.isRedstonePowered())
            {
                if ((wholeTimeOutput == 0 || this.tfNeeded == 0) && this.canCondenseTf())
                {
                    // Make a start on process
                    TfCondenserRecipe recipe = getCurrentRecipe();
                    this.wholeTimeOutput = recipe.ticksNeeded;
                    this.tfNeeded = recipe.tfAmountNeeded;
                }

                if (this.wholeTimeOutput > 0 && this.canCondenseTf())
                {
                    if (this.tfPooled >= tfNeeded)
                    {
                        // Condensing
                        this.processTimeOutput++;
                        this.isWorking = true;
                    }

                    if (this.processTimeOutput >= this.wholeTimeOutput)
                    {
                        // Process Complete
                        this.processTimeOutput = 0;
                        this.wholeTimeOutput = 0;
                        this.tfPooled = 0;
                        this.tfNeeded = 0;
                        this.onOutputCompleted();
                        isInventoryChanged = true;
                    }
                }
                else if (this.wholeTimeOutput > 0)
                {
                    // Process stopped
                    this.processTimeOutput = 0;
                    this.wholeTimeOutput = 0;
                    this.tfPooled = 0;
                }
            }

            if (isTfCharged)
            {
                this.isWorking = true;
                this.isTfCharged = false;
            }

            if (isTfPoweredInternal)
            {
                this.paramTfPowered.set(true);
                this.paramTfPow = true;
                this.sendChangesToClients();
                this.isTfPoweredInternal = false;
            }
            else
            {
                this.paramTfPowered.set(false);
                this.paramTfPow = false;
                this.sendChangesToClients();
            }
        }

        // Update machine appearance
        if (this.isWorking != this.prevWorking)
        {
            isInventoryChanged = true;
            BlockTfCondenser.updateMachineState(this.isProcessing(), this.worldObj, this.getPos());
        }

        isTfCharged = false;

        // Nigari slot
        if (nigariTank.getFluid() == null)
        {
            nigariTank.setFluid(new FluidStack(TcFluids.NIGARI, 0));
        }
        this.addFluidToTank(SLOT_NIGARI_INPUT, SLOT_NIGARI_OUTPUT, nigariTank);

        // Ingredient slot
        ItemStack ingredientItem = itemStacks[SLOT_SPECIAL_INPUT];
        if (ingredientItem != null)
        {
            this.addFluidToTank(SLOT_SPECIAL_INPUT, SLOT_SPECIAL_OUTPUT, ingredientTank);
            this.updateIngredientItem();
        }

        if (isInventoryChanged)
        {
            this.markDirty();
        }

        this.prevWorking = this.isWorking;
    }

    private boolean addFluidToTank(int inputSlotId, int outputSlotId, FluidTank tank)
    {
        ItemStack slotItemInput = itemStacks[inputSlotId];
        ItemStack slotItemOutput = itemStacks[outputSlotId];
        if (slotItemInput != null)
        {
            FluidStack fluidInput = FluidContainerRegistry.getFluidForFilledItem(slotItemInput);
            if (fluidInput != null && canAddItemToSlot(slotItemOutput, FluidContainerRegistry.drainFluidContainer(slotItemInput)))
            {
                // Check if the input fluid is the same and the tank will not overflow
                if (fluidInput.amount == tank.fill(fluidInput, false))
                {
                    tank.fill(fluidInput, true);
                    this.moveDrainedItem(inputSlotId, outputSlotId);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canAddItemToSlot(ItemStack slotItem, ItemStack inputItem)
    {
        if (slotItem == null || inputItem == null) return true;
        if (!slotItem.isItemEqual(inputItem)) return false;

        // Check if the stack will overflow
        int result = ItemStackAdapter.getSize(slotItem) + 1;
        return (result <= getInventoryStackLimit() && result <= slotItem.getMaxStackSize());
    }

    private void moveDrainedItem(int inputSlotId, int outputSlotId)
    {
        ItemStack slotItemInput = itemStacks[inputSlotId];
        ItemStack slotItemOutput = itemStacks[outputSlotId];
        ItemStack container = FluidContainerRegistry.drainFluidContainer(slotItemInput);

        if (container != null)
        {
            if (slotItemOutput == null)
            {
                itemStacks[outputSlotId] = container.copy();
            }
            else
            {
            	ItemStackAdapter.addSize(slotItemOutput, 1);
            }
        }
        
        if (ItemStackAdapter.preDecr(slotItemInput) == 0)
        {
            this.itemStacks[inputSlotId] = null;
        }
    }

    public void updateIngredientItem()
    {
        if (ingredientTank.getFluid() != null)
        {
            this.ingredientFluidItem = FluidUtils.getSampleFilledItemFromFluid(ingredientTank.getFluid().getFluid());
        }
        else
        {
            this.ingredientFluidItem = null;
        }
    }

    /**
     * Checks if the machine accepts the item to output soymilk
     */
    private boolean canCondenseTf()
    {
        if (!this.isRedstonePowered()) return false;

        TfCondenserRecipe recipe = this.getCurrentRecipe();
        if (recipe == null) return false;

        // Has enough ingredients?
        if (recipe.ingredient != null && ingredientTank.getFluidAmount() < recipe.ingredient.amount
                || nigariTank.getFluidAmount() < NIGARI_COST_MB) return false;

        // Check if an output item can be stacked to the output slot
        if (this.itemStacks[SLOT_TOFU_OUTPUT] == null) return true;

        if (!this.itemStacks[SLOT_TOFU_OUTPUT].isItemEqual(recipe.result)) return false;

        // Check if the stack will overflow
        int resultStacks = ItemStackAdapter.getSize(itemStacks[SLOT_TOFU_OUTPUT]) + ItemStackAdapter.getSize(recipe.result);
        return (resultStacks <= getInventoryStackLimit() && resultStacks <= recipe.result.getMaxStackSize());
    }

    /**
     * When the output process completed. Take one from the output container stack and fill soymilk converted from TF
     */
    public void onOutputCompleted()
    {
        TfCondenserRecipe recipe = this.getCurrentRecipe();
        ItemStack var1 = recipe.result;

        if (this.itemStacks[SLOT_TOFU_OUTPUT] == null)
        {
            this.itemStacks[SLOT_TOFU_OUTPUT] = var1.copy();
        }
        else if (this.itemStacks[SLOT_TOFU_OUTPUT].isItemEqual(var1))
        {
        	ItemStackAdapter.addSize(itemStacks[SLOT_TOFU_OUTPUT], ItemStackAdapter.getSize(var1));
        }

        if (recipe.ingredient != null)
        {
            this.ingredientTank.drain(recipe.ingredient.amount, true);
            this.updateIngredientItem();
        }
        this.nigariTank.drain(NIGARI_COST_MB, true);
    }

    public TfCondenserRecipe getCurrentRecipe()
    {
        if (ingredientTank.getFluid() != null)
        {
            return TfCondenserRecipeRegistry.ingredientToRecipeMap.get(ingredientTank.getFluid().getFluid());
        }
        else
        {
            return null;
        }
    }

    @Override
    public double getMaxTfCapacity()
    {
        if (this.isRedstonePowered() && this.tfPooled < this.tfNeeded)
        {
            return Math.min(2.0D, this.tfNeeded - this.tfPooled);
        }
        else
        {
            return 0;
        }
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return getMaxTfCapacity();
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfPooled += amount;
        if (amount > 0)
        {
            this.isTfCharged = true;
        }
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack stackIn)
    {
        if (par1 == SLOT_NIGARI_INPUT)
        {
            return stackIn.getItem() == TcItems.nigari;
        }
        else if (par1 == SLOT_SPECIAL_INPUT)
        {
            FluidStack fluidStackInput = FluidContainerRegistry.getFluidForFilledItem(stackIn);
            return fluidStackInput != null
                    && TfCondenserRecipeRegistry.ingredientToRecipeMap.containsKey(fluidStackInput.getFluid());
        }
        return false;

    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, EnumFacing side)
    {
        return slot == SLOT_NIGARI_OUTPUT || slot == SLOT_SPECIAL_OUTPUT || slot == SLOT_TOFU_OUTPUT;
    }

    @Override
    public void setTfPowered()
    {
        this.isTfPoweredInternal = true;
    }

	@Override
    public int[] getSlotsForFace(EnumFacing side)
    {
		switch(side){
		case DOWN:
			return slotsBottom;
		case UP:
			return slotsTop;
		default:
			return slotsSides;
		}
    }

	@Override
	public int getField(int id) {
		switch (id) {
		case 0: return this.processTimeOutput;
		case 1: return this.wholeTimeOutput;
		case 2: return this.paramTfPow ? 1 : 0;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0: this.processTimeOutput = value; break;
		case 1: this.wholeTimeOutput = value; break;
		case 2: this.paramTfPow = value > 0;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerTfCondenser(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return "tofucraft:tfCondenser";
	}
	
    protected void sendChangesToClients() {

		if (!this.hasWorldObj() || this.worldObj.isRemote) return;

		List<EntityPlayer> list = this.getWorld().playerEntities;
		for (EntityPlayer player : list) {
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).connection.sendPacket(this.getUpdatePacket());
			}
		}
     }
}
