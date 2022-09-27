package remiliaMarine.tofu.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class AsmPetitUtil
{
    public static String unmap(String typeName)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(typeName);
    }

    public static String getActualClass(String targetClassPath)
    {
        if (TofuCraftCorePlugin.isObfuscated)
        {
            return AsmPetitUtil.unmap(targetClassPath);
        }
        return targetClassPath;
    }
}
