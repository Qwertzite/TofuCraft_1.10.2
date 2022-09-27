package remiliaMarine.tofu.block;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcItems;

public class BlockGlowtofuBarrel extends BlockBarrelBase {

    public BlockGlowtofuBarrel(Material par3Material)
    {
        super(par3Material);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.barrelGlowtofu);
    }

    /**
     * For client
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean checkEnvironment(IBlockAccess blockAccess, BlockPos pos)
    {
        return Biome.getIdForBiome(blockAccess.getBiome(pos)) == Biome.getIdForBiome(Biomes.HELL);
    }

    /**
     * For server
     */
    @Override
    public boolean checkEnvironment(World world, BlockPos pos)
    {
        return Biome.getIdForBiome(world.getBiome(pos)) == Biome.getIdForBiome(Biomes.HELL);
    }

    @Override
    public void addFermentedItem(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.tofuGlow, 3));
    }

    @Override
    public void addIngredients(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.tofuMomen, 3));
        list.add(new ItemStack(Items.GLOWSTONE_DUST, 3));
    }
}
