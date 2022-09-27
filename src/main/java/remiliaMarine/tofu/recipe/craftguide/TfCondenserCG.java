package remiliaMarine.tofu.recipe.craftguide;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipe;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipeRegistry;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.tileentity.TileEntityTfCondenser;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.LiquidSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class TfCondenserCG extends TfMachineCGBase
{
    public static final int SLOT_NIGARI = 0;
    public static final int SLOT_INGREDIENT = 1;
    public static final int SLOT_TF = 2;
    public static final int SLOT_TIME = 3;
    public static final int SLOT_OUTPUT = 4;
    public static final int SLOT_MACHINE = 5;

    private static final FluidStack NIGARI = new FluidStack(TcFluids.NIGARI, TileEntityTfCondenser.NIGARI_COST_MB);

    public TfCondenserCG()
    {
        this.slots = new Slot[6];
        this.slots[SLOT_NIGARI] = new LiquidSlot(2, 11);
        this.slots[SLOT_INGREDIENT] = new LiquidSlot(17, 11);
        this.slots[SLOT_TF] = new TfGuageSlot(10, 31);
        this.slots[SLOT_TIME] = new TimeSlot(33, 20, 18, 16);

        this.slots[SLOT_OUTPUT] = new ItemSlot(56, 20, 16, 16, true).setSlotType(SlotType.OUTPUT_SLOT);
        this.slots[SLOT_MACHINE] = new ItemSlot(35, 37, 20, 20).setSlotType(SlotType.MACHINE_SLOT);
    }

    @Override
    public void generateRecipes(RecipeGenerator generator, RecipeTemplate template, ItemStack machine)
    {
        for (TfCondenserRecipe recipe : TfCondenserRecipeRegistry.recipeRegistry)
        {
            Object[] items = new Object[6];
            items[SLOT_NIGARI] = NIGARI;
            items[SLOT_INGREDIENT] = recipe.ingredient.copy();
            items[SLOT_TF] = recipe.tfAmountNeeded;
            items[SLOT_TIME] = recipe.ticksNeeded;
            items[SLOT_OUTPUT] = recipe.result;
            items[SLOT_MACHINE] = machine;
            generator.addRecipe(template, items);
        }
    }

    protected int getTextureRow()
    {
        return 1;
    }

    @Override
    public ItemStack getMachineBlock()
    {
        return new ItemStack(TcBlocks.tfCondenserIdle, 1, 0);
    }
}
