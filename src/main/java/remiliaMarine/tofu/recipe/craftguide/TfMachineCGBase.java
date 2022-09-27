package remiliaMarine.tofu.recipe.craftguide;

import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.TofuCraftCore;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;

public abstract class TfMachineCGBase extends CraftGuideAPIObject implements RecipeProvider
{
    protected Slot[] slots;

    @Override
    public void generateRecipes(RecipeGenerator generator)
    {
        RecipeTemplate template;
        ItemStack machine = getMachineBlock();
        int posY = 58 * this.getTextureRow();
        template = generator.createRecipeTemplate(this.slots, machine,
                TofuCraftCore.RES + "textures/gui/craftGuideRecipe.png", 0, posY, 79, posY);
        this.generateRecipes(generator, template, machine);
    }

    protected abstract int getTextureRow();
    protected abstract ItemStack getMachineBlock();
    protected abstract void generateRecipes(RecipeGenerator generator, RecipeTemplate template, ItemStack machine);
}
