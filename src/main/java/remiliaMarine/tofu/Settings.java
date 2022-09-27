package remiliaMarine.tofu;

import net.minecraftforge.common.config.Configuration;

public class Settings {
	public static boolean debug = true;
	public static boolean achievement;
	
    public static int clientGlowTofuLightInterval = 2;
    public static int serverGlowTofuLightInterval = 5;
	
	public static void load(Configuration conf) {
		
		Settings.achievement = conf.getBoolean("achievement", "general", true, "");
		Settings.debug = conf.getBoolean("debug", "system", false, "");
		
	}
}
