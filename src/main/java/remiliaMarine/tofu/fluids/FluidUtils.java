package remiliaMarine.tofu.fluids;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidUtils {
    public static Map<String, List<ItemStack>> fluidToFilledItemMap;

    static
    {
        FluidContainerRegistry.FluidContainerData[] list = FluidContainerRegistry.getRegisteredFluidContainerData();
        fluidToFilledItemMap = Maps.newHashMap();

        for (FluidContainerRegistry.FluidContainerData data : list)
        {
            if (data.fluid != null && data.fluid.getFluid() != null)
            {
                String fluidId = data.fluid.getUnlocalizedName();
                if (!fluidToFilledItemMap.containsKey(fluidId))
                {
                    fluidToFilledItemMap.put(fluidId, Lists.<ItemStack>newArrayList());
                }
                fluidToFilledItemMap.get(fluidId).add(data.filledContainer);
            }
        }
    }
    
    public static ItemStack getSampleFilledItemFromFluid(Fluid fluid)
    {
        if (fluid != null)
        {
            List<ItemStack> list = fluidToFilledItemMap.get(fluid.getName());
            if (list != null && !list.isEmpty())
            {
                return list.get(0);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    
    public static List<ItemStack> getFilledItemFromFluid(Fluid fluid)
    {
        List<ItemStack> list = fluidToFilledItemMap.get(fluid.getName());
        if (list != null)
        {
            return list;
        }
        else
        {
            return Lists.newArrayList();
        }
    }

    public static boolean isContainerForFluid(FluidStack fluid, ItemStack container)
    {
        return FluidContainerRegistry.fillFluidContainer(fluid, container) != null;
    }
    
    
    
    
}
