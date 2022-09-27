package remiliaMarine.tofu.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;

public class TcSoundEvents {

	public static SoundEvent BUGGLE;

	public static void register() {
		BUGGLE = registerSound("tofubugle");
	}

	private static SoundEvent registerSound(String name) {
		ResourceLocation res = new ResourceLocation(TofuCraftCore.MODID, name);
		return GameRegistry.register(new SoundEvent(res).setRegistryName(res));
	}
}
