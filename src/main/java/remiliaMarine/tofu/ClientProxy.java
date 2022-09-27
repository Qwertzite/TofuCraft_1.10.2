package remiliaMarine.tofu;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore.ISidedProxy;
import remiliaMarine.tofu.eventhandler.GameScreenHandler;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.init.TcTextures;
import remiliaMarine.tofu.init.block.LoaderDecorationBlock;
import remiliaMarine.tofu.init.registry.BlockColourRegistry;
import remiliaMarine.tofu.init.registry.ItemColourRegistry;

@SideOnly(Side.CLIENT)
public class ClientProxy implements ISidedProxy
{
    @Override
    public void registerComponents()
    {
        ItemColourRegistry.register();
        BlockColourRegistry.register();
    	
        MinecraftForge.EVENT_BUS.register(new TcTextures());
        MinecraftForge.EVENT_BUS.register(new GameScreenHandler());

        LoaderDecorationBlock.registerBlockRenderer();
        TcEntity.registerEntityRenderer();
    }
    
	public void regsterMeshDefAndStateMapper() {
		
        //ItemMeshDefinitionの登録。手持ち時の描画用
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(TcBlocks.soymilkStill), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return TcFluids.ResourceLocSOYMILK;
            }
        });
        //StateMapperの登録。ブロック描画用
        ModelLoader.setCustomStateMapper(TcBlocks.soymilkStill, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
                return TcFluids.ResourceLocSOYMILK;
            }
        });

        //ItemMeshDefinition手持ち時の描画用
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(TcBlocks.soymilkHellStill),
				new ItemMeshDefinition() {
					@Override
					public ModelResourceLocation getModelLocation(ItemStack stack) {
						return TcFluids.ResourceLocSOYMILK_HELL;
					}
				});
		// StateMapperの登録。ブロック描画用
		ModelLoader.setCustomStateMapper(TcBlocks.soymilkHellStill, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return TcFluids.ResourceLocSOYMILK_HELL;
			}
		});
		
        //ItemMeshDefinition手持ち時の描画用
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(TcBlocks.soySauceStill),
				new ItemMeshDefinition() {
					@Override
					public ModelResourceLocation getModelLocation(ItemStack stack) {
						return TcFluids.ResourceLocSOY_SAUCE;
					}
				});
		// StateMapperの登録。ブロック描画用
		ModelLoader.setCustomStateMapper(TcBlocks.soySauceStill, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return TcFluids.ResourceLocSOY_SAUCE;
			}
		});
	}
}
