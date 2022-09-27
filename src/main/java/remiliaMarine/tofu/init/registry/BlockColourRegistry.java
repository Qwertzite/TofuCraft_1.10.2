package remiliaMarine.tofu.init.registry;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import remiliaMarine.tofu.block.BlockFermentable;
import remiliaMarine.tofu.init.TcBlocks;

public class BlockColourRegistry {

	public static void register() {

		//LoaderConstruction
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockClourFfffff(), TcBlocks.tcLeaves);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockClourFfffff(), TcBlocks.chikuwaPlatformTofu);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockClourFfffff(), TcBlocks.chikuwaPlatformPlain);
		//LoaderDecoration
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColourFermentable(), TcBlocks.barrelMiso);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColourFermentable(), TcBlocks.barrelMisoTofu);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColourFermentable(), TcBlocks.barrelGlowtofu);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColourFermentable(), TcBlocks.barrelAdvTofuGem);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColourFermentable(), TcBlocks.nattoBed);


	}

	public static class BlockColourFermentable implements IBlockColor {
		@Override
        public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
        {
			return ((BlockFermentable)state.getBlock()).colorMultiplier(state, worldIn, pos);
        }
	}

	public static class BlockClourFfffff implements IBlockColor {
		@Override
        public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
        {
			return 0xFFFFFF;
        }
	}

}
