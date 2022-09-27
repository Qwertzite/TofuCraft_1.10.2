package remiliaMarine.tofu.api.recipe;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import remiliaMarine.tofu.recipe.Ingredient;
import remiliaMarine.tofu.recipe.IngredientDicSet;
import remiliaMarine.tofu.recipe.IngredientItem;
import remiliaMarine.tofu.util.ItemUtils;

/**
 * Represents a recipe for TF Reformer.
 *
 * For API users:
 * To register recipes, all you need use is constructors, addIngredients() and setInputContainerItem().
 * There are samples in recipe.Recipes class.
 */
public class TfReformerRecipe {

    public final double tfAmountNeeded;
    public Ingredient<?> containerItem;
    private final List<IngInfoItem> ingItems = Lists.newArrayList();
    private final List<IngInfoDic> ingDic = Lists.newArrayList();
    public final ItemStack result;

    /**
     * Constructor
     *
     * @param result Result item
     * @param tfAmount TF amount needed to reform TF to the result item
     */
    public TfReformerRecipe(ItemStack result, double tfAmount)
    {
        ItemStack container = result.getItem().getContainerItem(result);
        if (container == null)
        {
            throw new RuntimeException("Tf Reformer recipe needs an item with CONTAINER as result!");
        }
        int[] oreIds = OreDictionary.getOreIDs(container);
        if (oreIds.length > 0)
        {
            List<String> names = Lists.newArrayList();
            for (int oreId : oreIds)
            {
                names.add(OreDictionary.getOreName(oreId));
            }
            this.containerItem = new IngredientDicSet(names.toArray(new String[names.size()]));
        }
        else
        {
            this.containerItem = new IngredientItem(container);
        }
        this.tfAmountNeeded = tfAmount;
        this.result = result;
    }

    /**
     * Adds an ore dictionary item as ingredients
     *
     * @param dic TofuCraft dic object
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(TcOreDic dic, boolean isCatalyzer)
    {
        return this.addIngredients(new String[]{dic.name()}, isCatalyzer);
    }

    /**
     * Adds an ore dictionary item as ingredients
     *
     * @param dicName dic name
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(String dicName, boolean isCatalyzer)
    {
        return this.addIngredients(new String[]{dicName}, isCatalyzer);
    }

    /**
     * Adds an ore dictionary item as ingredients
     *
     * @param dicName Ore name
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(String[] dicName, boolean isCatalyzer)
    {
        ingDic.add(new IngInfoDic(dicName, isCatalyzer));
        sortIngredients();
        return this;
    }

    /**
     * Adds an item as ingredients
     *
     * @param item Item
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(Item item, boolean isCatalyzer)
    {
        return this.addIngredients(new ItemStack(item), isCatalyzer);
    }

    /**
     * Adds an item as ingredients
     *
     * @param block Block
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(Block block, boolean isCatalyzer)
    {
        return this.addIngredients(new ItemStack(block), isCatalyzer);
    }

    /**
     * Adds an item stack as ingredients
     *
     * @param item Item stack
     * @param isCatalyzer If true, the item is not consumed when completed
     * @return myself to chain
     */
    public TfReformerRecipe addIngredients(ItemStack item, boolean isCatalyzer)
    {
        ingItems.add(new IngInfoItem(item, isCatalyzer));
        sortIngredients();
        return this;
    }

    /**
     * Set an input container item INSTEAD of the empty item of the container item.
     *
     * This is almost never used.
     * The empty item of the result is set as default in constructor.
     *
     * @param itemStack Item stack
     */
    public TfReformerRecipe setInputContainerItem(ItemStack itemStack)
    {
        this.containerItem = new IngredientItem(itemStack);
        return this;
    }

    /**
     * Set an input container item INSTEAD of the empty item of the container item.
     *
     * This is almost never used.
     * The empty item of the result is set as default in constructor.
     *
     * @param dicName Ore name
     */
    public TfReformerRecipe setInputContainerItem(String[] dicName)
    {
        this.containerItem = new IngredientDicSet(dicName);
        return this;
    }

    // === INTERNAL METHODS AND CLASSES ===

    private void sortIngredients()
    {
        if (ingItems.size() >= 2)
        {
            Collections.sort(ingItems);
        }
        if (ingDic.size() >= 2)
        {
            Collections.sort(ingDic);
        }
    }

    public IngInfo<?>[] getIngredients() // For slot check
    {
        List<IngInfo<?>> list = Lists.newArrayList();
        list.addAll(this.ingItems);
        list.addAll(this.ingDic);
        return list.toArray(new IngInfo[list.size()]);
    }

    public boolean matches(ItemStack container, ItemStack[] inputs)
    {
        // Container
        if (!this.isContainerItem(container)) return false;

        // Ingredients
        int inputCount = 0;
        for (ItemStack input : inputs) inputCount += input != null ? 1 : 0;

        List<IngInfo<?>> list = Lists.newArrayList(this.getIngredients());
        if (list.size() != inputCount) return false;

        Iterator<IngInfo<?>> itr = list.iterator();
        while (itr.hasNext())
        {
            IngInfo<?> ing = itr.next();
            for (ItemStack stack : inputs)
            {
                if (ing.ingredient.matches(stack))
                {
                    itr.remove();
                    break;
                }
            }
        }
        return list.size() == 0;
    }

    public boolean isContainerItem(ItemStack container)
    {
        return container != null && this.containerItem.matches(container);
    }

    public boolean isCatalystItem(ItemStack target)
    {
        IngInfo<?> ing = this.getIngredientMatchingWith(target);
        return ing != null && ing.isCatalyst;
    }

    public IngInfo<?> getIngredientMatchingWith(ItemStack target)
    {
        IngInfo<?>[] ingredients = this.getIngredients();

        for (IngInfo<?> info : ingredients)
        {
            if (info.ingredient.matches(target)) return info;
        }
        return null;
    }

    public int hashCode()
    {
        int hash = this.containerItem.hashCode();
        for (IngInfo<?> info : this.getIngredients())
        {
            hash *= 13;
            hash += info.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof TfReformerRecipe)) return false;
        TfReformerRecipe other = (TfReformerRecipe)obj;

        if (!this.containerItem.equals(other.containerItem)) return false;

        IngInfo<?>[] list1 = this.getIngredients();
        IngInfo<?>[] list2 = other.getIngredients();

        if (list1.length != list2.length) return false;

        for (int i = 0; i < list1.length; i++)
        {
            if (!list1[i].equals(list2[i])) return false;
        }
        return true;
    }

    public abstract static class IngInfo<T> implements Comparable<IngInfo<T>>
    {
        public final boolean isCatalyst;
        public final Ingredient<T> ingredient;

        public IngInfo(Ingredient<T> ing, boolean isCatalyst)
        {
            this.ingredient = ing;
            this.isCatalyst = isCatalyst;
        }

        @Override
        public String toString()
        {
            return String.format("%s, isCatalyst=%s", ingredient.toString(), isCatalyst);
        }

        @Override
        public boolean equals(Object obj)
        {
            return obj instanceof IngInfo && ingredient.equals(((IngInfo<?>)obj).ingredient);
        }

        @Override
        public int hashCode()
        {
            return ingredient.hashCode();
        }

    }
    public static class IngInfoItem extends IngInfo<ItemStack>
    {
        public IngInfoItem(ItemStack item, boolean isCatalyst)
        {
            super(new IngredientItem(item), isCatalyst);
        }

        @Override
        public int compareTo(IngInfo<ItemStack> o)
        {
            return ItemUtils.compareToItemStacks(this.ingredient.itemObj, o.ingredient.itemObj);
        }
    }
    public static class IngInfoDic extends IngInfo<String[]>
    {
        public IngInfoDic(String[] dicName, boolean isCatalyst)
        {
            super(new IngredientDicSet(dicName), isCatalyst);
        }

        @Override
        public int compareTo(IngInfo<String[]> o)
        {
            return Integer.valueOf(this.hashCode()).compareTo(o.hashCode());
        }
    }
}
