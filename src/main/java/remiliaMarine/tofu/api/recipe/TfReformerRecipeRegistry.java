package remiliaMarine.tofu.api.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.util.ModLog;

/**
 * TF Reformer recipe entry class
 *
 * For API users:
 * To register, see TfReformerRecipe class.
 * There are samples in recipe.Recipes class.
 */
public class TfReformerRecipeRegistry {

    public static final List<TfReformerRecipe> recipeRegistry = new ArrayList<TfReformerRecipe>();

    public static void register(TfReformerRecipe recipe)
    {
        if (recipeRegistry.contains(recipe))
        {
            ModLog.log(Level.WARN, "Duplicated recipe for TF Reformer! Already registered combination: container=%s, ingredients=%s", recipe.containerItem, Arrays.toString(recipe.getIngredients()));
            return;
        }

        recipeRegistry.add(recipe);
    }

    public static TfReformerRecipe getRecipe(ItemStack container, ItemStack[] ingredients)
    {
        for (TfReformerRecipe recipe : recipeRegistry)
        {
            if (recipe.matches(container, ingredients)) return recipe;
        }
        return null;
    }

    public static boolean isContainerItem(ItemStack container)
    {
        for (TfReformerRecipe recipe : recipeRegistry)
        {
            if (recipe.isContainerItem(container)) return true;
        }
        return false;
    }

}
