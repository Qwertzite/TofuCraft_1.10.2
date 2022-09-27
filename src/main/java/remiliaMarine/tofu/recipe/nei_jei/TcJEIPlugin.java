package remiliaMarine.tofu.recipe.nei_jei;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.api.TfMaterialRegistry;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipeRegistry;
import remiliaMarine.tofu.api.recipe.TfReformerRecipeRegistry;
import remiliaMarine.tofu.gui.inventory.GuiTfCondenser;
import remiliaMarine.tofu.gui.inventory.GuiTfReformer2;
import remiliaMarine.tofu.gui.inventory.GuiTfStorage;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.inventory.ContainerTfCondenser;
import remiliaMarine.tofu.inventory.ContainerTfReformer2;
import remiliaMarine.tofu.inventory.ContainerTfStorage;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;

@JEIPlugin
public class TcJEIPlugin extends BlankModPlugin {

	@Override
	public void registerIngredients(IModIngredientRegistration ingredientRegistry) {
		ingredientRegistry.register(TfStack.class, Collections.singletonList(new TfStack(1.0d)), new TfStackHelper(), new TfStackRenderer());
	}

	@Override
	public void register(IModRegistry registry) {
		
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipeCategories(
				new RecipeCategoryTfMaterial(guiHelper),
				new RecipeCategoryTfReformer(guiHelper),
				new RecipeCategoryTfCondenser(guiHelper));
		
		registry.addRecipeHandlers(
				new TfMaterialRecipeHandler(),
				new TfReformerRecipeHandler(),
				new TfCondenserRecipeHandler());
		
		registry.addRecipeClickArea(GuiTfStorage.class, 70, 38, 23, 14, RecipeCategoryTfMaterial.UID);
		registry.addRecipeClickArea(GuiTfReformer2.class, 65, 38, 23, 14, RecipeCategoryTfReformer.UID);
		registry.addRecipeClickArea(GuiTfCondenser.class, 116, 37, 23, 14, RecipeCategoryTfCondenser.UID);// 75 120
		
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTfStorage.class, RecipeCategoryTfMaterial.UID, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTfReformer2.class, RecipeCategoryTfReformer.UID, 0, 5, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTfCondenser.class, RecipeCategoryTfCondenser.UID, 0, 2, 2, 36);
		
		registry.addRecipeCategoryCraftingItem(new ItemStack(TcBlocks.tfStorageIdle), RecipeCategoryTfMaterial.UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(TcBlocks.tfReformerIdle,  1, TileEntityTfReformer.EnumModel.MIX.id), RecipeCategoryTfReformer.UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(TcBlocks.tfCondenserIdle), RecipeCategoryTfCondenser.UID);
		
		registry.addRecipes((List<TfMaterialRecipeWrapper>)TfMaterialRecipeMaker.getRecipe(TfMaterialRegistry.getTfMaterialList()));
		registry.addRecipes((List<TfReformerRecipeWrapper>)TfReformerRecipeMaker.getRecipe(TfReformerRecipeRegistry.recipeRegistry));
		registry.addRecipes((List<TfCondenserRecipeWrapper>)TfCondenserRecipeMaker.getRecipe(TfCondenserRecipeRegistry.recipeRegistry));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

	}
}
