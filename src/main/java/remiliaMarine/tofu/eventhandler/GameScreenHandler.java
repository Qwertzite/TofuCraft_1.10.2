package remiliaMarine.tofu.eventhandler;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.BatchDigging;
import remiliaMarine.tofu.item.DiamondTofuToolHandler;

public class GameScreenHandler {
    
	//private static final int LINE_COLOR = 0x00ffff;

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event)
    {
        RayTraceResult mop = event.getTarget();

        if (event.getSubID() == 0 && mop.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            ItemStack itemHeld = event.getPlayer().getHeldItemMainhand();

            if (itemHeld != null
                    && (itemHeld.getItem() == TcItems.toolDiamond[0] || itemHeld.getItem() == TcItems.toolDiamond[1] || itemHeld.getItem() == TcItems.toolDiamond[2]))
            {
                EntityPlayer player = event.getPlayer();
                World world = player.worldObj;
                IBlockState blockstate = world.getBlockState(mop.getBlockPos());
                Block block = blockstate.getBlock();
                if (!block.isAir(blockstate, world, mop.getBlockPos()) && itemHeld.getItem().getStrVsBlock(itemHeld, blockstate) > 1.0F)
                {
                    float partialTicks = event.getPartialTicks();

                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
                    GL11.glLineWidth(2.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(false);

                    float f1 = 0.002F;
                    double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                    double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                    double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                    DiamondTofuToolHandler.Area digArea = DiamondTofuToolHandler.getDigArea(player, itemHeld);
                    AxisAlignedBB box = BatchDigging.getDiggingArea(mop.getBlockPos(), digArea.w, digArea.d, digArea.h, mop.sideHit);
                    box = box.expand((double) f1, (double) f1, (double) f1).offset(-d0, -d1, -d2);
                    RenderGlobal.drawSelectionBoundingBox(box, 0.0f, 1.0f, 1.0f, 0.5f);

                    GL11.glDepthMask(true);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
        }
    }
}
