package remiliaMarine.tofu.asm;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions("remiliaMarine.tofucraft.asm")
public class TofuCraftCorePlugin implements IFMLLoadingPlugin {
	
    static File location;
    //public static Logger log;
    public static String currentMcVersion = (String)net.minecraftforge.fml.relauncher.FMLInjectionData.data()[4];
    public static Boolean isObfuscated;

    public TofuCraftCorePlugin()
    {
        //log = Logger.getLogger("TofuCraft-core");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{TransformerMain.class.getName()};
    }
    
    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        if (data.containsKey("coremodLocation"))
        {
            location = (File) data.get("coremodLocation");
        }
        if (data.containsKey("runtimeDeobfuscationEnabled"))
        {
            isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
        }
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
