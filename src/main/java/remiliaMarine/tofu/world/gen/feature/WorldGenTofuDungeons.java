package remiliaMarine.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.world.loot.TofuDungeonChest;

public class WorldGenTofuDungeons extends WorldGenerator {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
        byte b0 = 3;
        int l = rand.nextInt(2) + 2;
        int i1 = rand.nextInt(2) + 2;
        int j1 = 0;
        int k1;
        int l1;
        int i2;
        
        int par3 = position.getX();
        int par4 = position.getY();
        int par5 = position.getZ();
        
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (k1 = par3 - l - 1; k1 <= par3 + l + 1; ++k1)
        {
            for (l1 = par4 - 1; l1 <= par4 + b0 + 1; ++l1)
            {
                for (i2 = par5 - i1 - 1; i2 <= par5 + i1 + 1; ++i2)
                {
                	mutable.setPos(k1, l1, i2);
                    Material material = worldIn.getBlockState(mutable).getMaterial();

                    if (l1 == par4 - 1 && !material.isSolid())
                    {
                        return false;
                    }

                    if (l1 == par4 + b0 + 1 && !material.isSolid())
                    {
                        return false;
                    }

                    if ((k1 == par3 - l - 1 || k1 == par3 + l + 1 || i2 == par5 - i1 - 1 || i2 == par5 + i1 + 1) && l1 == par4 && worldIn.isAirBlock(mutable) && worldIn.isAirBlock(mutable.up()))
                    {
                        ++j1;
                    }
                }
            }
        }

        if (j1 >= 1 && j1 <= 5)
        {
            for (k1 = par3 - l - 1; k1 <= par3 + l + 1; ++k1)
            {
                for (l1 = par4 + b0; l1 >= par4 - 1; --l1)
                {
                    for (i2 = par5 - i1 - 1; i2 <= par5 + i1 + 1; ++i2)
                    {
                    	mutable.setPos(k1, l1, i2);
                        if (k1 != par3 - l - 1 && l1 != par4 - 1 && i2 != par5 - i1 - 1 && k1 != par3 + l + 1 && l1 != par4 + b0 + 1 && i2 != par5 + i1 + 1)
                        {
                            worldIn.setBlockToAir(mutable);
                        }
                        else if (l1 >= 0 && !worldIn.getBlockState(mutable.down()).getMaterial().isSolid())
                        {
                            worldIn.setBlockToAir(mutable);
                        }
                        else if (worldIn.getBlockState(mutable).getMaterial().isSolid())
                        {
//                            if (l1 == par4 - 1 && par2Random.nextInt(4) != 0)
//                            {
//                                par1World.setBlock(k1, l1, i2, Block.cobblestoneMossy, 0, 2);
//                            }
//                            else
//                            {
//                                par1World.setBlock(k1, l1, i2, TcBlock.tofuIshi, 0, 2);
//                            }
                            worldIn.setBlockState(mutable, TcBlocks.tofuIshi.getDefaultState(), 2);
                        }
                    }
                }
            }

            k1 = 0;

            while (k1 < 2)
            {
                l1 = 0;

                while (true)
                {
                    if (l1 < 3)
                    {
                        label210:
                        {
                            i2 = par3 + rand.nextInt(l * 2 + 1) - l;
                            int j2 = par5 + rand.nextInt(i1 * 2 + 1) - i1;
                            mutable.setPos(i2, par4, j2);
                            if (worldIn.isAirBlock(mutable))
                            {
                                int k2 = 0;

                                if (worldIn.getBlockState(mutable.north()).getMaterial().isSolid())
                                {
                                    ++k2;
                                }

                                if (worldIn.getBlockState(mutable.east()).getMaterial().isSolid())
                                {
                                    ++k2;
                                }

                                if (worldIn.getBlockState(mutable.south()).getMaterial().isSolid())
                                {
                                    ++k2;
                                }

                                if (worldIn.getBlockState(mutable.west()).getMaterial().isSolid())
                                {
                                    ++k2;
                                }

                                if (k2 == 1)
                                {
                                    worldIn.setBlockState(mutable, Blocks.CHEST.getDefaultState(), 2);
                                    TileEntityChest tileentitychest = (TileEntityChest)worldIn.getTileEntity(mutable);

                                    if (tileentitychest != null)
                                    {
                                    	//((TileEntityChest)tileentitychest).setLootTable(TofuLootTableList.TOFU_DUNGEON, rand.nextLong());
                                        TofuDungeonChest.generateDangeonChestContent(rand, tileentitychest);
                                    }

                                    break label210;
                                }
                            }

                            ++l1;
                            continue;
                        }
                    }

                    ++k1;
                    break;
                }
            }

            worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)worldIn.getTileEntity(position);

            if (tileentitymobspawner != null)
            {
                tileentitymobspawner.getSpawnerBaseLogic().setEntityName(TcEntity.IdTofuCreeper);
            }
            else
            {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    
	}
}
