package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.versionAdapter.DamageSourceAdapter;

public class BlockSoymilkStill extends BlockTcStationary {
	
    public BlockSoymilkStill(Material material, String[] iconNames)
    {
        super(TcFluids.SOYMILK, material, iconNames);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.getBlockState(pos).getValue(LEVEL) == 0)
        {
            int heat = this.getHeatStrength(world, pos);

            if (heat == 2)
            {
                if (world.isAirBlock(pos.up()) && rand.nextInt(2) == 0)
                {
                	world.setBlockState(pos.up(), TcBlocks.yuba.getDefaultState());

                    if (rand.nextInt(10) == 0)
                    {
                    	world.setBlockToAir(pos);
                    }
                }

            }
        }

        super.updateTick(world, pos, state, rand);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            int heat = this.getHeatStrength(par1World, pos);

            if (par5Entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLiving = (EntityLivingBase)par5Entity;
                if (entityLiving.ticksExisted % 20 == 0)
                {
                    if (heat == 2)
                    {
                        entityLiving.attackEntityFrom(DamageSourceAdapter.ON_FIRE, 0.2f);
                    }
                    if (heat == 1)
                    {
                        entityLiving.heal(0.05f);
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (worldIn.getBlockState(pos.up()).getMaterial() != Material.WATER && rand.nextInt(3) == 0)
        {
            if (this.getHeatStrength(worldIn, pos) > 0)
            {
                float steamX = pos.getX() + 0.5F;
                float steamY = pos.getY() + 0.9F;
                float steamZ = pos.getZ() + 0.5F;
                float steamRandX = rand.nextFloat() * 0.6F - 0.3F;
                float steamRandZ = rand.nextFloat() * 0.6F - 0.3F;
                double gRand1 = rand.nextGaussian() * 0.01D;
                double gRand2 = rand.nextGaussian() * 0.01D;
                double gRand3 = rand.nextGaussian() * 0.01D;
                worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (steamX + steamRandX), steamY, (steamZ + steamRandZ), gRand1, gRand2, gRand3);
            }
        }
    }

    private int getHeatStrength(World par1World, BlockPos pos)
    {
        for (int i = 1; i < 5; i++)
        {
            Block block = par1World.getBlockState(pos.down(i)).getBlock();
            if (block == Blocks.FIRE || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
            {
                return i <= 2 ? 2 : 1;
            }
        }
        return 0;
    }

}
