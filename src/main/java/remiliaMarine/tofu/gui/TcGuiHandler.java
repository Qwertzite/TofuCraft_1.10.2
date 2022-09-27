package remiliaMarine.tofu.gui;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import remiliaMarine.tofu.gui.inventory.GuiSaltFurnace;
import remiliaMarine.tofu.gui.inventory.GuiTfAntenna;
import remiliaMarine.tofu.gui.inventory.GuiTfCondenser;
import remiliaMarine.tofu.gui.inventory.GuiTfOven;
import remiliaMarine.tofu.gui.inventory.GuiTfReformer;
import remiliaMarine.tofu.gui.inventory.GuiTfReformer2;
import remiliaMarine.tofu.gui.inventory.GuiTfSaturator;
import remiliaMarine.tofu.gui.inventory.GuiTfStorage;
import remiliaMarine.tofu.inventory.ContainerSaltFurnace;
import remiliaMarine.tofu.inventory.ContainerTfAntenna;
import remiliaMarine.tofu.inventory.ContainerTfCondenser;
import remiliaMarine.tofu.inventory.ContainerTfOven;
import remiliaMarine.tofu.inventory.ContainerTfReformer;
import remiliaMarine.tofu.inventory.ContainerTfReformer2;
import remiliaMarine.tofu.inventory.ContainerTfSaturator;
import remiliaMarine.tofu.inventory.ContainerTfStorage;
import remiliaMarine.tofu.tileentity.TileEntitySaltFurnace;
import remiliaMarine.tofu.tileentity.TileEntityTfAntenna;
import remiliaMarine.tofu.tileentity.TileEntityTfCondenser;
import remiliaMarine.tofu.tileentity.TileEntityTfOven;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfSaturator;
import remiliaMarine.tofu.tileentity.TileEntityTfStorage;

public class TcGuiHandler implements IGuiHandler {

    public static final int GUIID_SALT_FURNACE = 0;
    public static final int GUIID_TF_STORAGE = 1;
    public static final int GUIID_TF_ANTENNA = 2;
    public static final int GUIID_TF_CONDENSER = 3;
    public static final int GUIID_TF_OVEN = 4;
    public static final int GUIID_TF_REFORMER = 5;
    public static final int GUIID_TF_REFORMER2 = 6;
    public static final int GUIID_TF_SATURATOR = 7;

    private HashMap<Integer, GuiEntry> guiRegistry = new HashMap<Integer, GuiEntry>();

    public TcGuiHandler()
    {
        this.registerGuiEntry(createEntry(GUIID_SALT_FURNACE).withName("SaltFurnace"));
        this.registerGuiEntry(createEntry(GUIID_TF_STORAGE).withName("TfStorage"));
        this.registerGuiEntry(createEntry(GUIID_TF_ANTENNA).withName("TfAntenna"));
        this.registerGuiEntry(createEntry(GUIID_TF_CONDENSER).withName("TfCondenser"));
        this.registerGuiEntry(createEntry(GUIID_TF_OVEN).withName("TfOven"));
        this.registerGuiEntry(createEntry(GUIID_TF_REFORMER).withName("TfReformer"));
        this.registerGuiEntry(createEntry(GUIID_TF_REFORMER2).withName("TfReformer2", "TfReformer", "TfReformer2"));
        this.registerGuiEntry(createEntry(GUIID_TF_SATURATOR).withName("TfSaturator"));
    }

    private GuiEntry createEntry(int id)
    {
        if (FMLLaunchHandler.side() == Side.CLIENT)
        {
            return new GuiEntryClient(id);
        }
        else
        {
            return new GuiEntryServer(id);
        }
    }

    public void registerGuiEntry(GuiEntry entry)
    {
        guiRegistry.put(entry.id, entry);
    }


    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
    	TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    	if(!(tile instanceof IInventory))return null;

    	switch(guiId){
    	case GUIID_SALT_FURNACE:
    		return new ContainerSaltFurnace(player.inventory, (IInventory)tile);
		case GUIID_TF_STORAGE:
			return new ContainerTfStorage(player.inventory, (TileEntityTfStorage) tile);
		case GUIID_TF_ANTENNA:
			return new ContainerTfAntenna(player.inventory, (TileEntityTfAntenna) tile);
		case GUIID_TF_CONDENSER:
			return new ContainerTfCondenser(player.inventory, (TileEntityTfCondenser) tile);
		case GUIID_TF_OVEN:
			return new ContainerTfOven(player.inventory, (TileEntityTfOven) tile);
		case GUIID_TF_REFORMER:
			return new ContainerTfReformer(player.inventory, (TileEntityTfReformer) tile);
		case GUIID_TF_REFORMER2:
			return new ContainerTfReformer2(player.inventory, (TileEntityTfReformer) tile);			
		case GUIID_TF_SATURATOR:
			return new ContainerTfSaturator(player.inventory, (TileEntityTfSaturator) tile);
    	}
//        GuiEntry entry = guiRegistry.get(guiId);
//
//        if (entry != null)
//        {
//            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
//            if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
//            {
//                try
//                {
//                    Constructor constructor = entry.getContainerClass().getConstructor(InventoryPlayer.class, entry.getTileEntityClass());
//                    return constructor.newInstance(player.inventory, tile);
//                } catch (Exception e)
//                {
//                    ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
//                }
//            }
//        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
    	TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    	if(!(tile instanceof IInventory))return null;

    	switch(guiId){
    	case GUIID_SALT_FURNACE:
        	if(!(tile instanceof TileEntitySaltFurnace))return null;
    		return new GuiSaltFurnace(player.inventory, (TileEntitySaltFurnace)tile);
		case GUIID_TF_STORAGE:
    		return new GuiTfStorage(player.inventory, (TileEntityTfStorage)tile);
		case GUIID_TF_ANTENNA:
			return new GuiTfAntenna(player.inventory, (TileEntityTfAntenna) tile);
		case GUIID_TF_CONDENSER:
			return new GuiTfCondenser(player.inventory, (TileEntityTfCondenser) tile);
		case GUIID_TF_OVEN:
			return new GuiTfOven(player.inventory, (TileEntityTfOven) tile);
		case GUIID_TF_REFORMER:
			return new GuiTfReformer(player.inventory, (TileEntityTfReformer) tile);
		case GUIID_TF_REFORMER2:
			return new GuiTfReformer2(player.inventory, (TileEntityTfReformer) tile);		
		case GUIID_TF_SATURATOR:
			return new GuiTfSaturator(player.inventory, (TileEntityTfSaturator) tile);
    	}
//        GuiEntry entry = guiRegistry.get(guiId);
//
//        if (entry != null)
//        {
//            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
//            if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
//            {
//                try
//                {
//                    Constructor constructor = entry.getGuiClass().getConstructor(InventoryPlayer.class, entry.getTileEntityClass());
//                    return constructor.newInstance(player.inventory, tile);
//                } catch (Exception e)
//                {
//                    ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
//                }
//            }
//        }

        return null;
    }
}
