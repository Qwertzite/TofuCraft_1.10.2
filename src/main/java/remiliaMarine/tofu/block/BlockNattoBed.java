package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcItems;

public class BlockNattoBed extends BlockFermentable {

    public BlockNattoBed(Material material, SoundType sound)
    {
        super(material);
        this.setTickRandomly(true);
        this.setSoundType(sound);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public void addFermentedItem(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.natto, 6));
    }

    @Override
    public void addIngredients(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.soybeans, 6));
        list.add(new ItemStack(Items.WHEAT, 3));
    }

    @Override
    public boolean checkEnvironment(World world, BlockPos pos)
    {
        boolean isSnowEnabled = world.getBiome(pos).getEnableSnow();
        return isSnowEnabled && world.getLightFromNeighbors(pos) >= 8 || !isSnowEnabled;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (placer instanceof EntityPlayer)
        {
            TcAchievementMgr.achieve((EntityPlayer)placer, TcAchievementMgr.Key.nattoFarm);
        }
    }

}
