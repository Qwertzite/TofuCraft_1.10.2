package remiliaMarine.tofu.api.tileentity;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketGuiControl;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;
import remiliaMarine.tofu.versionAdapter.TileEntityAdapter;

public abstract class TileEntityTfMachineBase extends TileEntityLockable implements IInventory, ITickable {

	protected ItemStack[] itemStacks = new ItemStack[0];

    protected String customName;

    abstract protected String getInventoryNameTranslate();

    public void onTileEntityCreated(World world, BlockPos pos, EntityLivingBase entityCreatedBy, ItemStack itemstack) {};

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : this.getInventoryNameTranslate();
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String par1Str)
    {
        this.customName = par1Str;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (itemStacks.length > 0)
        {
            NBTTagList nbttaglist = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            this.itemStacks = new ItemStack[this.getSizeInventory()];

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                byte b0 = nbttagcompound1.getByte("Slot");

                if (b0 >= 0 && b0 < this.itemStacks.length)
                {
                    this.itemStacks[b0] = ItemStackAdapter.loadFromNBT(nbttagcompound1);
                }
            }
        }

        if (nbtTagCompound.hasKey("CustomName", 8))
        {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte("Rev", (byte) this.getNBTRevision());

        if (itemStacks.length > 0)
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.itemStacks.length; ++i)
            {
                if (this.itemStacks[i] != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.itemStacks[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }

            nbtTagCompound.setTag("Items", nbttaglist);
        }

        if (this.hasCustomName())
        {
            nbtTagCompound.setString("CustomName", this.customName);
        }
        return nbtTagCompound;
    }

    protected int getNBTRevision()
    {
        return 1;
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), nbttagcompound);
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
       return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @SideOnly(Side.CLIENT)
    public void postGuiControl(int windowId, int eventId)
    {
        this.postGuiControl(windowId, eventId, null);
    }

    @SideOnly(Side.CLIENT)
    public void postGuiControl(int windowId, int eventId, PacketGuiControl.DataHandler data)
    {
        PacketGuiControl packet = new PacketGuiControl(windowId, eventId, data);
        PacketDispatcher.packet(packet).sendToServer();
    }

    public boolean isRedstonePowered()
    {
        return this.getWorld().isBlockPowered(this.getPos());
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.itemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.itemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.itemStacks[par1] != null)
        {
            ItemStack var3;

            if (ItemStackAdapter.getSize(this.itemStacks[par1]) <= par2)
            {
                var3 = this.itemStacks[par1];
                this.itemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.itemStacks[par1].splitStack(par2);

                if (ItemStackAdapter.getSize(this.itemStacks[par1]) == 0)
                {
                    this.itemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack removeStackFromSlot(int par1)
    {
        if (this.itemStacks[par1] != null)
        {
            ItemStack var2 = this.itemStacks[par1];
            this.itemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.itemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && ItemStackAdapter.getSize(par2ItemStack) > this.getInventoryStackLimit())
        {
        	ItemStackAdapter.setSize(par2ItemStack, this.getInventoryStackLimit());
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     * 1.10.2: boolean {@link IInventory#isUseableByPlayer(EntityPlayer)}<br>
     * 1.12.2: boolean {@link IInventory#isUsableByPlayer(EntityPlayer)}
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.getWorld().getTileEntity(this.getPos()) != this ? false : par1EntityPlayer.getDistanceSq(this.getPos().add(0.5d, 0.5d, 0.5D)) <= 64.0D;
    }

    protected IBlockState getLiveMetadata()
    {
        return this.getWorld().getBlockState(this.getPos());
    }
    
    protected void sendChangesToClients() {

		if (!TileEntityAdapter.hasWorld(this) || this.getWorld().isRemote) return;

		List<EntityPlayer> list = this.getWorld().playerEntities;
		for (EntityPlayer player : list) {
			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).connection.sendPacket(this.getUpdatePacket());
			}
		}
     }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}
    
    @Override
    public void clear()
    {
        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            this.itemStacks[i] = null;
        }
        this.markDirty();
    }

}
