package remiliaMarine.tofu.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.api.tileentity.ITfConsumer;
import remiliaMarine.tofu.api.tileentity.ITfInputIndicator;
import remiliaMarine.tofu.api.tileentity.ITfSupplier;
import remiliaMarine.tofu.api.tileentity.ITfTank;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineBase;
import remiliaMarine.tofu.block.BlockTfAntenna;
import remiliaMarine.tofu.inventory.ContainerSaltFurnace;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.util.TileCoord;
import remiliaMarine.tofu.util.TileScanner;
import remiliaMarine.tofu.util.TileScanner.Impl;

public class TileEntityTfAntenna extends TileEntityTfMachineBase {
    private boolean isInit = false;

    private final List<BlockPos> supplierList = new ArrayList<BlockPos>();
    private final List<BlockPos> consumerList = new ArrayList<BlockPos>();

    public double statTfBalance;
    public double statTfDemand;
    public double statTfSupply;

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfAntenna";
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void update()
    {
        if (worldObj.isRemote) return;

        if (!isInit)
        {
            TileScanner scanner = new TileScanner(worldObj, this.getPos());
            this.scanWholeArea(scanner);
            isInit = true;
        }

        StringBuffer log = new StringBuffer();
        Iterator<BlockPos> itr;

        /*
         * Sum TF needed
         */
        double tfNeeded = 0.0D;
        double tfConsumed = 0.0D;
        itr = consumerList.iterator();
        while (itr.hasNext())
        {
            BlockPos coord = itr.next();
            TileEntity tileEntity = worldObj.getTileEntity(coord);
            if (tileEntity instanceof ITfConsumer)
            {
                tfNeeded += ((ITfConsumer)tileEntity).getMaxTfCapacity();
                tfConsumed += ((ITfConsumer)tileEntity).getCurrentTfConsumed();

                if (tileEntity instanceof ITfInputIndicator)
                {
                    ((ITfInputIndicator) tileEntity).setTfPowered();
                }
            }
            else if (worldObj.isBlockLoaded(coord)) // Checks if the block is loaded
            {
                itr.remove();
            }
        }
        
        statTfDemand = tfConsumed;
        log.append(String.format("tfNeeded=%.1f, ", tfNeeded));
        
        /*
         * Draw TF from suppliers
         */
        double tfInput = 0D;
        itr = supplierList.iterator();
        while (itr.hasNext())
        {
            BlockPos coord = itr.next();
            TileEntity tileEntity = worldObj.getTileEntity(coord);
            if (tileEntity instanceof ITfTank)
            {
                ITfTank supplier = (ITfTank)tileEntity;
                if (tfNeeded > 0.0D)
                {
                    if (tfNeeded <= supplier.getMaxTfOffered())
                    {
                        supplier.drawTf(tfNeeded);
                        tfInput += tfNeeded;
                        tfNeeded = 0.0D;
                        break;
                    } else
                    {
                        double tfDrawn = supplier.getMaxTfOffered();
                        supplier.drawTf(tfDrawn);
                        tfInput += tfDrawn;
                        tfNeeded -= tfDrawn;
                    }
                }
            }
            else if (tileEntity instanceof ITfSupplier)
            {
                ITfSupplier supplier = (ITfSupplier)tileEntity;
                double tfDrawn = supplier.getMaxTfOffered();
                supplier.drawTf(tfDrawn);
                tfInput += tfDrawn;
                tfNeeded -= tfDrawn;
            }
            else if (worldObj.isBlockLoaded(coord))
            {
                itr.remove();
            }
        }

        statTfSupply = tfInput;
        log.append(String.format("tfInput=%.1f, ", tfInput));
        if (tfInput == 0D) return;

        /*
         * Supply TF to consumers
         */
        double tfLeft = tfInput;
        itr = consumerList.iterator();
        while (itr.hasNext())
        {
            BlockPos coord = itr.next();
            TileEntity tileEntity = worldObj.getTileEntity(coord);
            if (tileEntity instanceof ITfConsumer)
            {
                ITfConsumer consumer = (ITfConsumer) tileEntity;
                double tfOutput = consumer.getMaxTfCapacity();
                if (tfLeft < tfOutput)
                {
                    consumer.chargeTf(tfLeft);
                    tfLeft = 0D;
                    break;
                }
                else
                {
                    tfLeft -= tfOutput;
                    consumer.chargeTf(consumer.getMaxTfCapacity());
                }
            }
        }

        log.append(String.format("tfLeft=%.1f, ", tfLeft));
        if (tfLeft == 0D) return;

        /*
         * Charge extra TF to TF tank
         */
        itr = supplierList.iterator();
        while (itr.hasNext())
        {
            BlockPos coord = itr.next();
            TileEntity tileEntity = worldObj.getTileEntity(coord);
            if (tileEntity instanceof ITfTank)
            {
                ITfTank tank = (ITfTank)tileEntity;
                if (tfLeft > 0.0D && tfLeft <= tank.getMaxTfCapacity())
                {
                    tank.drawTf(-tfLeft);
                    tfLeft = 0.0D;
                    break;
                }
                else
                {
                    double tfCapacity = tank.getMaxTfCapacity();
                    tank.drawTf(-tfCapacity);
                    tfLeft -= tfCapacity;
                }
            }
        }

        statTfBalance = tfLeft;
    }

	public void scanWholeArea(TileScanner scanner)
    {
        BlockTfAntenna.WaveFreq waveFreq = this.getAntennaType();

        scanner.scan(waveFreq.radius, TileScanner.Method.full, new Impl<Object>()
        {
            public void apply(World world, BlockPos pos)
            {
                TileEntity tileEntity = worldObj.getTileEntity(pos);
                if (tileEntity != null)
                {
                    registerMachine(tileEntity);
                }
            }
        });
    }

    public void registerMachine(TileEntity tileEntity)
    {
        BlockTfAntenna.WaveFreq waveFreq = this.getAntennaType();
        BlockPos pos = tileEntity.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos mypos = this.getPos();

        if (Math.abs(x - mypos.getX()) + Math.abs(y - mypos.getY()) + Math.abs(z - mypos.getZ()) > waveFreq.radius) return;

        if (tileEntity instanceof ITfSupplier)
        {
            supplierList.add(new BlockPos(x, y, z));
            ModLog.debug("%s connected to antenna as supplier", tileEntity.getBlockType().getUnlocalizedName());
        }

        if (tileEntity instanceof ITfConsumer)
        {
            consumerList.add(new BlockPos(x, y, z));
            ModLog.debug("%s connected to antenna as consumer", tileEntity.getBlockType().getUnlocalizedName());
        }
    }

    public BlockTfAntenna.WaveFreq getAntennaType()
    {
        Block block = worldObj.getBlockState(this.getPos()).getBlock();
        if (block instanceof BlockTfAntenna)
        {
            return ((BlockTfAntenna) block).getAntennaType();
        }
        else
        {
            this.invalidate();
            return BlockTfAntenna.WAVE_LIST[0];
        }
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
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
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerSaltFurnace(playerInventory, this);
    }

	@Override
	public String getGuiID() {
		return "tofucraft:tfAntenna";
	}

}
