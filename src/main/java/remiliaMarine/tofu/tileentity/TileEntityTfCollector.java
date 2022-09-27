package remiliaMarine.tofu.tileentity;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.api.tileentity.ITfSupplier;
import remiliaMarine.tofu.api.tileentity.TileEntityTfMachineBase;
import remiliaMarine.tofu.api.util.TfJitterControl;

public class TileEntityTfCollector extends TileEntityTfMachineBase implements ITfSupplier {
	
    private static final EnumSet<EnumFacing> ENABLED_SIDES = EnumSet.of(
            EnumFacing.UP,
            EnumFacing.NORTH,
            EnumFacing.WEST,
            EnumFacing.SOUTH,
            EnumFacing.EAST);

    private double output = 0.0D;
    private final TfJitterControl jitterControl = new TfJitterControl(1.0D, 1.2, 0.5D, 24000 / 4, 63L);

    @Override
    protected String getInventoryNameTranslate()
    {
        return null;
    }

    @Override
    public void update()
    {
        if (worldObj.isRemote) return;
        
        BlockPos pos = this.getPos();
        if (worldObj.getHeightmapHeight(pos.getX(), pos.getZ()) - 10 < pos.getY())
        {
            double weatherRate = worldObj.isRaining() ? 1.5D : 1.0D;

            int workingSides = 0;
            for (EnumFacing facing : ENABLED_SIDES)
            {
                if (!worldObj.getBlockState(pos.offset(facing)).isNormalCube())
                {
                    workingSides++;
                }
            }

            double jitter = jitterControl.getCurrentValue(worldObj, pos.getX(), pos.getY(), pos.getZ());
            WorldInfo worldinfo = this.getWorld().getWorldInfo();
            int dimension = ReflectionHelper.getPrivateValue(WorldInfo.class, worldinfo, "dimension", "field_76105_j");
            output = (dimension == TofuCraftCore.TOFU_DIMENSION.getId() ? 2000D : 100D) / 24000D
                    * weatherRate * jitter * ((double) workingSides / ENABLED_SIDES.size());
        }
    }

    @Override
    public double getMaxTfOffered()
    {
        return output;
    }

    @Override
    public void drawTf(double amount)
    {
        output -= amount;
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
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public String getGuiID() {
		return "tofucraft:tfCollector";
	}
}
