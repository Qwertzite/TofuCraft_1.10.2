package remiliaMarine.tofu.init.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.itemblock.ItemTcBlock;

public class BlockRegister<T extends Block>
{
    private T block;
    private ItemBlock itemBlock;
    @SuppressWarnings("unused")
    @Deprecated
	private Object[] itemCtorArgs;
    private String uniqueName;
    private String resourceName;

    public BlockRegister(String name, T block)
    {
        this.block = block;
        this.itemBlock = new ItemBlock(block);
        this.uniqueName = name;
        this.resourceName = name;
    }

    public BlockRegister<T> withResource(String name)
    {
        this.resourceName = name;
        return this;
    }

    public BlockRegister<T> wrappedByItemTcBlock()
    {
        this.itemBlock = new ItemTcBlock(this.block);
        return this;
    }

    public BlockRegister<T> wrappedBy(ItemBlock itemBlock)
    {
        this.itemBlock = itemBlock;
        return this;
    }
    @Deprecated
    public BlockRegister<T> havingArgs(Object... itemCtorArgs)
    {
        this.itemCtorArgs = itemCtorArgs;
        return this;
    }

    public BlockRegister<T> setHarvestLevel(String tool, int level)
    {
        block.setHarvestLevel(tool, level);
        return this;
    }

    public BlockRegister<T> withTileEntity(Class<? extends TileEntity> tileEntity)
    {
        return this.withTileEntity(tileEntity, uniqueName);
    }

    public BlockRegister<T> withTileEntity(Class<? extends TileEntity> tileEntity, String name)
    {
        GameRegistry.registerTileEntity(tileEntity, TofuCraftCore.RES + name);
        return this;
    }

	public T registerBlock()
    {
        block.setUnlocalizedName(TofuCraftCore.RES + resourceName);
        //block.setBlockTextureName(TofuCraftCore.MODID + resourceName);
        ResourceLocation resource = new ResourceLocation(TofuCraftCore.MODID, this.resourceName);
        GameRegistry.register(this.block, resource);
        GameRegistry.register(this.itemBlock, resource);

        return block;
    }
}