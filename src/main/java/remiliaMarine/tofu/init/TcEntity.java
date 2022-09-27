package remiliaMarine.tofu.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.block.render.RenderFallingChikuwaPlatform;
import remiliaMarine.tofu.entity.EntityFallingChikuwaPlatform;
import remiliaMarine.tofu.entity.EntityFukumame;
import remiliaMarine.tofu.entity.EntityTofuCreeper;
import remiliaMarine.tofu.entity.EntityTofuSlime;
import remiliaMarine.tofu.entity.EntityTofunian;
import remiliaMarine.tofu.entity.EntityZundaArrow;
import remiliaMarine.tofu.entity.RenderFukumame;
import remiliaMarine.tofu.entity.RenderTofuCreeper;
import remiliaMarine.tofu.entity.RenderTofuSlime;
import remiliaMarine.tofu.entity.RenderTofunian;
import remiliaMarine.tofu.entity.RenderZundaArrow;

public class TcEntity {

    public static String IdTofuSlime = "TofuSlime";
    public static String IdTofuCreeper = "TofuCreeper";
    public static String IdTofunian = "Tofunian";
    public static String IdZundaArrow = "ZundaArrow";
    public static String IdFukumame = "Fukumame";


    public static Biome[] allBiomesList;

	public static void register(TofuCraftCore core) {

        // Tofu Slime
		EntityRegistry.registerModEntity(EntityTofuSlime.class, IdTofuSlime, 1, core, 64, 2, true, 0xefeedf, 0xc2c1b8);

        // Tofu Creeper
		EntityRegistry.registerModEntity(EntityTofuCreeper.class, IdTofuCreeper, 2, core, 64, 4, true, 0xefeedf, 0x82817b);

		// Tofunian
		EntityRegistry.registerModEntity(EntityTofunian.class, IdTofunian, 3, core, 64, 4, true, 0xefeedf, 0x82817b);

	    // Zunda Arrow
        EntityRegistry.registerModEntity(EntityZundaArrow.class, IdZundaArrow, 4, core, 64, 2, true);
 		// fukumame
        EntityRegistry.registerModEntity(EntityFukumame.class, IdFukumame, 5, core, 64, 2, true);

        // Falling Chikuwa Platform
        EntityRegistry.registerModEntity(EntityFallingChikuwaPlatform.class, "ChikuwaPlatform", 6, core, 64, 20, false);


        allBiomesList = (Biome[]) Biome.EXPLORATION_BIOMES_LIST.toArray(new Biome[0]);
	}

    public static void addSpawns()
    {

        EntityRegistry.addSpawn(EntityTofuSlime.class, 8, 1, 1, EnumCreatureType.MONSTER, allBiomesList);
        EntityRegistry.addSpawn(EntityTofuCreeper.class, 2, 1, 1, EnumCreatureType.MONSTER, allBiomesList);
    }

    @SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT) // added on purpose!
    public static void registerEntityRenderer()
    {
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	RenderManager rendermanager = new RenderManager(mc.renderEngine, mc.getRenderItem());

		RenderingRegistry.registerEntityRenderingHandler(EntityTofuSlime.class, new RenderTofuSlime(rendermanager, new ModelSlime(16), 0.25F));
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuCreeper.class, new RenderTofuCreeper(rendermanager));
        RenderingRegistry.registerEntityRenderingHandler(EntityTofunian.class, new RenderTofunian(rendermanager));
        RenderingRegistry.registerEntityRenderingHandler(EntityZundaArrow.class, new RenderZundaArrow(rendermanager));
        RenderingRegistry.registerEntityRenderingHandler(EntityFukumame.class, new RenderFukumame(rendermanager));
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingChikuwaPlatform.class, new RenderFallingChikuwaPlatform(rendermanager));

    }

}
