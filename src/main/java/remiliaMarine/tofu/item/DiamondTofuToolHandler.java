package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import remiliaMarine.tofu.enchantment.TcEnchantmentHelper;

public class DiamondTofuToolHandler {

    private ItemTool tool;

    public DiamondTofuToolHandler(ItemTool tool)
    {
        this.tool = tool;
    }

    public void onBlockStartBreak(ItemStack stack, World world, Block blockDestroyed, BlockPos pos, EntityPlayer owner)
    {
        IBlockState state = world.getBlockState(pos);
        if (tool.getStrVsBlock(stack, state) > 1.0F)
        {
            RayTraceResult mop = FMLClientHandler.instance().getClient().objectMouseOver;
            if (mop == null) return;
            Area area = getDigArea(owner, stack);
            new BatchDigging(owner, new ItemStack(blockDestroyed, 1, blockDestroyed.damageDropped(state))).execute(owner.getEntityWorld(), pos, area.w, area.d, area.h, mop.sideHit);
        }
    }

    public static Area getDigArea(EntityLivingBase owner, ItemStack stack)
    {
        // Determine digging area depending on level
        int lvl = TcEnchantmentHelper.getBatchModifier(owner, stack);
        Area area = new Area();
        area.w = 1 + lvl;
        area.d = 2 + lvl;
        area.h = 1 + lvl;
        return area;
    }

    public static class Area
    {
        public int w, d, h;
    }
}
