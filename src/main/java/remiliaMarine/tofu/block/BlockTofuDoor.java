package remiliaMarine.tofu.block;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.util.BlockUtils;
import remiliaMarine.tofu.util.TofuBlockUtils;

public class BlockTofuDoor extends BlockDoor implements IBlockTofuMaterial {

    public static final EnumMap<TofuMaterial, BlockTofuDoor> doorBlocks = Maps.newEnumMap(TofuMaterial.class);

    private TofuMaterial tofuMaterial;

    public BlockTofuDoor(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.setSoundType(tofuMaterial.getBlockInfo().getSoundType());
        this.tofuMaterial = tofuMaterial;
        this.disableStats();
        doorBlocks.put(tofuMaterial, this);
    }

    private static BlockUtils.IEntityWeightingBlockHandler tofuWeightingHandler = new BlockUtils.IEntityWeightingBlockHandler()
    {
        @Override
        public void apply(World world, Entity entity, Block block, BlockPos pos)
        {
            world.setBlockToAir(pos);

            if (entity instanceof EntityPlayer)
            {
                TcAchievementMgr.achieve((EntityPlayer) entity, TcAchievementMgr.Key.tofuMental);
            }
        }
    };

    @Override
    public void onFallenUpon(World par1World, BlockPos pos, Entity par5Entity, float par6)
    {
        if (this.tofuMaterial == TofuMaterial.KINU)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6, tofuWeightingHandler);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : TcItems.tofuDoor;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.tofuMaterial.id();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.tofuDoor, 1, this.damageDropped(state));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return this.tofuMaterial.getBlock().getBlockLayer() == BlockRenderLayer.TRANSLUCENT ?
        		BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }

}
