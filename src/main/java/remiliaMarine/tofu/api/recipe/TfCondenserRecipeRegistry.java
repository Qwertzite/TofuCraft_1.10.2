package remiliaMarine.tofu.api.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.util.ModLog;

/**
 * TF Condenser recipe entry class
 *
 * For API users:
 * To register, call register() from init() in your mod class.
 */
public class TfCondenserRecipeRegistry {
    public static final List<TfCondenserRecipe> recipeRegistry = new ArrayList<TfCondenserRecipe>();
    public static final Map<Fluid, TfCondenserRecipe> ingredientToRecipeMap = new HashMap<Fluid, TfCondenserRecipe>();

    public static void init()
    {
        register(new TfCondenserRecipe(
                1200,
                new FluidStack(TcFluids.MINERAL_SOYMILK, 2000),
                2400,
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.EnumTcMaterialInfo.activatedTofuGem.getMetadata())));

        register(new TfCondenserRecipe(
                2800,
                new FluidStack(TcFluids.SOYMILK_HELL, 5000),
                2400,
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.EnumTcMaterialInfo.activatedHellTofu.getMetadata())));

        register(new TfCondenserRecipe(
                20,
                new FluidStack(TcFluids.STRAWBERRY_JAM, 5),
                200,
                new ItemStack(TcBlocks.tofuStrawberry)));
    }

    public static void register(TfCondenserRecipe recipe)
    {
        if (recipe.ingredient != null && ingredientToRecipeMap.containsKey(recipe.ingredient.getFluid()))
        {
            ModLog.log(Level.WARN, "Duplicated recipe for TF Condenser! Already registered ingredient: %s", recipe.ingredient.toString());
            return;
        }

        recipeRegistry.add(recipe);
        if (recipe.ingredient != null)
        {
            ingredientToRecipeMap.put(recipe.ingredient.getFluid(), recipe);
        }
        else
        {
            ingredientToRecipeMap.put(null, recipe);
        }
    }


}
