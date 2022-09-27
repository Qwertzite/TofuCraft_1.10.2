package remiliaMarine.tofu.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.tileentity.ITfConsumer;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineBase;
import remiliaMarine.tofu.block.BlockTfSaturator;
import remiliaMarine.tofu.block.BlockTofu;
import remiliaMarine.tofu.data.ContainerParamBool;
import remiliaMarine.tofu.inventory.ContainerSaltFurnace;
import remiliaMarine.tofu.util.TileScanner;

public class TileEntityTfSaturator extends TileEntityTfMachineBase implements ITfConsumer {
	
    public static final double TF_CAPACITY = 1D;
    public static final double COST_TF_PER_TICK = 0.008D;
    public static final int RADIUS = 16;

    private final Random machineRand = new Random();
    public double tfPooled = 0D;
    public int interval = getNextInterval();
    public int tickCounter = 0;
    public int step = 0;
    public boolean isProcessingLastTick = false;
    public int saturatingTime = 0;
    public ContainerParamBool paramSuffocating = new ContainerParamBool(1, false);

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfSaturator";
    }

    /**
     * Writes a tile entity to NBT.
     * @return 
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setFloat("amount", (float) tfPooled);
        nbtTagCompound.setShort("tick", (short) tickCounter);
        nbtTagCompound.setShort("intv", (short) interval);
        nbtTagCompound.setShort("step", (byte) step);
        return nbtTagCompound;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        tfPooled = nbtTagCompound.getFloat("amount");
        tickCounter = nbtTagCompound.getShort("tick");
        interval = nbtTagCompound.getShort("intv");
        step = nbtTagCompound.getByte("step");
    }

    @SideOnly(Side.CLIENT)
    public double getProgressScaled()
    {
        return (double)this.tickCounter / (double)interval;
    }

    public boolean isProcessing()
    {
        return tfPooled >= COST_TF_PER_TICK && this.isRedstonePowered() && !this.paramSuffocating.get();
    }

    @SideOnly(Side.CLIENT)
    public double getTfCharged()
    {
        return this.tfPooled / TF_CAPACITY;
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void update()
    {
        if (!worldObj.isRemote)
        {
            this.paramSuffocating.set(!worldObj.isAirBlock(this.getPos().up()));

            boolean isProcessing = isProcessing();
            if (isProcessing)
            {
                if (tickCounter >= interval)
                {
                    saturateAround();
                    tickCounter = 0;
                    interval = getNextInterval();
                }

                tickCounter++;
                tfPooled -= COST_TF_PER_TICK;
                //ModLog.debug("t=%d, i=%d, s=%d", tickCounter, interval, step);
            }

            if (isProcessingLastTick != isProcessing())
            {
                BlockTfSaturator.updateMachineState(isProcessing(), this.worldObj, this.getPos());
            }
            isProcessingLastTick = isProcessing();
        }
        else
        {
            if (tickCounter >= interval)
            {
                saturatingTime = 30;
            }
            else if (saturatingTime > 0)
            {
                saturatingTime--;
            }
        }
    }

    public void saturateAround()
    {
        TileScanner scanner = new TileScanner(worldObj, pos);

        int len = Math.min(step * 2, RADIUS);
        scanner.scan(len, TileScanner.Method.full, new TileScanner.Impl<Object>()
        {
            public void apply(World world, BlockPos pos)
            {
                Block b = world.getBlockState(pos).getBlock();
                if (b instanceof BlockTofu)
                {
                    BlockTofu tofu = (BlockTofu)b;
                    if (tofu.canDrain() && tofu.isUnderWeight(worldObj, pos))
                    {
                        tofu.drainOneStep(worldObj, pos, machineRand);
                    }
                }
            }
        });

        if (++step * 2 > RADIUS) step = 1;
    }

    private int getNextInterval()
    {
        return 200 + machineRand.nextInt(400);
    }

    @Override
    public double getMaxTfCapacity()
    {
        return Math.min(0.1, TF_CAPACITY - tfPooled);
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return this.isRedstonePowered() ? COST_TF_PER_TICK : 0;
    }

    @Override
    public void chargeTf(double amount)
    {
        tfPooled += amount;
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }

	@Override
	public int getField(int id) {
		switch(id) {
		case 0: return this.interval;
		case 1: return this.tickCounter;
		case 2: return this.step;
		case 3: return this.isProcessingLastTick ? 1 : 0;
		case 4: return this.saturatingTime; 
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0: this.interval = value;
		case 1: this.tickCounter = value;
		case 2: this.step = value;
		case 3: this.isProcessingLastTick = value > 0;
		case 4: this.saturatingTime = value;
		}		
	}

	@Override
	public int getFieldCount() {
		return 5;
	}
	
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerSaltFurnace(playerInventory, this);
    }

	@Override
	public String getGuiID() {
		return "tofucraft:tfSaturator";
	}
}
