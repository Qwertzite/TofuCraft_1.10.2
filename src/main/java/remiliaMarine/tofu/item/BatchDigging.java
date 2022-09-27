package remiliaMarine.tofu.item;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class BatchDigging {
	
    private static List<List<ItemStack>> blockGroupingRegistry = Lists.newArrayList();

    private ItemStack itemStackDestroyed;

    static
    {
        addBlockGroup(new ItemStack(Blocks.DIRT, 1, 0), new ItemStack(Blocks.DIRT, 1, 2), new ItemStack(Blocks.GRASS, 1, 0));
    }

    public static void addBlockGroup(ItemStack... stacks)
    {
        List<ItemStack> group = Lists.newArrayList();
        Collections.addAll(group, stacks);
        blockGroupingRegistry.add(group);
    }
    
    private static Area calcArea(BlockPos pos, int w, int d, int h, EnumFacing sideHit)
    {
        // Convert WDH to practical XYZ
        EnumFacing dir = sideHit.getOpposite();
        int x1, y1, z1, x2, y2, z2;
        switch (dir)
        {
            case UP:
                x1 = -w;
                x2 = w;
                y1 = 0;
                y2 = d;
                z1 = -h;
                z2 = h;
                break;
            case DOWN:
                x1 = -w;
                x2 = w;
                y1 = -d;
                y2 = 0;
                z1 = -h;
                z2 = h;
                break;

            case WEST:
                x1 = -d;
                x2 = 0;
                y1 = -h;
                y2 = h;
                z1 = -w;
                z2 = w;
                break;
            case EAST:
                x1 = 0;
                x2 = d;
                y1 = -h;
                y2 = h;
                z1 = -w;
                z2 = w;
                break;

            case NORTH:
                x1 = -w;
                x2 = w;
                y1 = -h;
                y2 = h;
                z1 = -d;
                z2 = 0;
                break;

            case SOUTH:
                x1 = -w;
                x2 = w;
                y1 = -h;
                y2 = h;
                z1 = 0;
                z2 = d;
                break;

            default:
                return null;
        }

        // Add base coordinate
        int bx = pos.getX();
        int by = pos.getY();
        int bz = pos.getZ();
        
        return new Area(bx + x1, by + y1, bz + z1, bx + x2, by + y2, bz + z2);
    }
    
    public static AxisAlignedBB getDiggingArea(BlockPos pos, int w, int d, int h, EnumFacing sideHit)
    {
        Area area = calcArea(pos, w, d, h, sideHit);
        return new AxisAlignedBB(area.x1, area.y1, area.z1, area.x2 + 1.0D, area.y2 + 1.0D, area.z2 + 1.0D);
    }
    
    private EntityPlayer owner;
    
    public BatchDigging(EntityPlayer owner, ItemStack itemstackdestroyed)
    {
        this.owner = owner;
        this.itemStackDestroyed = itemstackdestroyed;
    }

    public static boolean isUnitedBlock(ItemStack block1, ItemStack block2)
    {
        if (block1.isItemEqual(block2)) return true;

        for (List<ItemStack> group : blockGroupingRegistry)
        {
            boolean matched1 = false;
            boolean matched2 = false;
            for (ItemStack stack : group)
            {
                if (OreDictionary.itemMatches(stack, block1, false)) matched1 = true;
                if (OreDictionary.itemMatches(stack, block2, false)) matched2 = true;
            }
            if (matched1 && matched2) return true;
        }
        return false;
    }
    
    public int execute(World world, BlockPos pos, int w, int d, int h, EnumFacing sideHit)
    {
        Area area = calcArea(pos, w, d, h, sideHit);

        int numBlocksDestroyed = 0;
        for (int x = area.x1; x <= area.x2; x++)
        {
            for (int y = area.y1; y <= area.y2; y++)
            {
                for (int z = area.z1; z <= area.z2; z++)
                {
                    if (this.destroyBlock(world, new BlockPos(x, y, z)))
                    {
                        numBlocksDestroyed++;
                    }
                }
            }
        }
        return numBlocksDestroyed;
    }
    
    private boolean destroyBlock(World world, BlockPos pos)
    {
    	//BlockPos pos = new BlockPos(x, y, z);
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        int meta = block.getMetaFromState(blockState);

        if (isUnitedBlock(itemStackDestroyed, new ItemStack(block, 1, meta)))
        {
            if (owner instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = (EntityPlayerMP) owner;
                int exp = ForgeHooks.onBlockBreakEvent(
                        world, player.interactionManager.getGameType(), player, pos);
                if (exp == -1)
                {
                    return false;
                }
                else
                {
                    boolean isCreative = player.capabilities.isCreativeMode;
                    boolean flag;
                    if (isCreative)
                    {
                        flag = this.removeBlock(world, pos);
                        player.connection.sendPacket(new SPacketBlockChange(world, pos));
                    }
                    else
                    {
                        flag = this.removeBlock(world, pos);

                        if (flag)
                        {
                            block.harvestBlock(world, player, pos, blockState, null, null);
                        }
                    }

                    // Drop experience
                    if (!isCreative && flag)
                    {
                        block.dropXpOnBlockBreak(world, pos, exp);
                    }
                    return flag;
                }
            }
            else
            {
                int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, owner.getHeldItemMainhand());
                world.setBlockToAir(pos);
                block.dropBlockAsItem(world, pos, blockState, fortune);
                return true;
            }
        }
        return false;
    }

    private boolean removeBlock(World world, BlockPos pos)
    {
        IBlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
        block.onBlockHarvested(world, pos, blockstate, owner);
        boolean flag = block.removedByPlayer(blockstate, world, pos, owner, true);

        if (flag)
        {
            block.onBlockDestroyedByPlayer(world, pos, blockstate);
        }

        return flag;
    }
    
    
    
    private static class Area
    {
        final int x1, y1, z1, x2, y2, z2;

        public Area(int x1, int y1, int z1, int x2, int y2, int z2)
        {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }
    }
}
