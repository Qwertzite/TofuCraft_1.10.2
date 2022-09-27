package remiliaMarine.tofu.util;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLLog;
import remiliaMarine.tofu.Settings;

public class ModLog {
    public static String modId;
    public static boolean isDebug = Settings.debug;

    public static void log(Level level, Throwable e, String format, Object... data)
    {
        FMLLog.log(modId, level, e, format, data);
    }

    public static void log(Level level, String format, Object... data)
    {
        FMLLog.log(modId, level, format, data);
    }

    public static void info(String format, Object... data)
    {
        FMLLog.log(modId, Level.INFO, format, data);
    }

    public static void debug(Object format, Object... data)
    {
        if (isDebug)
        {
            //System.out.printf("[" + modId + "] " + format + "%n", data);
            FMLLog.log(modId, Level.INFO, "(DEBUG) " + String.valueOf(format), data);
        }
    }
}
