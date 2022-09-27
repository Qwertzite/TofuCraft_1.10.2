package remiliaMarine.tofu;

import net.minecraft.world.DimensionType;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.TfMaterialRegistry;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipeRegistry;
import remiliaMarine.tofu.command.CommandTofuCreeperSpawn;
import remiliaMarine.tofu.command.CommandTofuSlimeCheck;
import remiliaMarine.tofu.data.MorijioManager;
import remiliaMarine.tofu.data.TcSaveHandler;
import remiliaMarine.tofu.enchantment.TcEnchantment;
import remiliaMarine.tofu.entity.TofuCreeperSeed;
import remiliaMarine.tofu.eventhandler.EventBonemeal;
import remiliaMarine.tofu.eventhandler.EventEntityLiving;
import remiliaMarine.tofu.eventhandler.EventFillBucket;
import remiliaMarine.tofu.eventhandler.EventItemPickup;
import remiliaMarine.tofu.eventhandler.EventLootTableLoad;
import remiliaMarine.tofu.eventhandler.EventPlayer;
import remiliaMarine.tofu.eventhandler.EventPlayerInteract;
import remiliaMarine.tofu.eventhandler.EventWorld;
import remiliaMarine.tofu.eventhandler.TcCraftingHandler;
import remiliaMarine.tofu.fishing.TofuFishing;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.init.TcAchievements;
import remiliaMarine.tofu.init.TcBiomes;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.init.TcSoundEvents;
import remiliaMarine.tofu.init.TcVillages;
import remiliaMarine.tofu.init.block.LoaderConstructionBlock;
import remiliaMarine.tofu.init.block.LoaderDecorationBlock;
import remiliaMarine.tofu.init.block.LoaderExternalBlock;
import remiliaMarine.tofu.init.block.LoaderTofuBlock;
import remiliaMarine.tofu.init.item.LoaderCombat;
import remiliaMarine.tofu.init.item.LoaderConstruction;
import remiliaMarine.tofu.init.item.LoaderDecoration;
import remiliaMarine.tofu.init.item.LoaderExternalItem;
import remiliaMarine.tofu.init.item.LoaderFood;
import remiliaMarine.tofu.init.item.LoaderMaterial;
import remiliaMarine.tofu.init.item.LoaderTofuItem;
import remiliaMarine.tofu.init.item.LoaderTool;
import remiliaMarine.tofu.network.PacketManager;
import remiliaMarine.tofu.potion.TcPotion;
import remiliaMarine.tofu.recipe.Recipes;
import remiliaMarine.tofu.recipe.craftguide.CraftGuideLoader;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;
import remiliaMarine.tofu.tileentity.TileEntityMorijio;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.versionAdapter.InitialisationAdapter;
import remiliaMarine.tofu.versionAdapter.RegisterAdapter;
import remiliaMarine.tofu.world.TcChunkProviderEvent;
import remiliaMarine.tofu.world.WorldProviderTofu;
/**
 * The Main Class of the TofuCraft
 *
 * @author Tsuteto (original author)
 * @author RemiliaMarine (translator)
 *
 */
@Mod(
	    modid = TofuCraftCore.MODID,
	    name = TofuCraftCore.MOD_NAME,
	    version = TofuCraftCore.VERSION,
	    acceptedMinecraftVersions = TofuCraftCore.MOD_ACCEPTED_MC_VERSIONS,
	    dependencies = "after:BambooMod;after:IC2"
	)
public class TofuCraftCore {
    public static final String MODID = "tofucraft";
    public static final String VERSION = "1.0.1-MC1.10.2";
    public static final String MOD_NAME = "TofuCraft-RMEdition";
    public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.10.2]";
    public static final String RES = "tofucraft:"; //Resource domain

    @Mod.Instance(MODID)
    public static TofuCraftCore INSTANCE;
    
    @Mod.Metadata(MODID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "remiliaMarine.tofu.ClientProxy", serverSide = "remiliaMarine.tofu.TofuCraftCore$ServerProxy")
    public static ISidedProxy sidedProxy;

    public static final BiomeDictionary.Type BIOME_TYPE_TOFU;

    public static DimensionType TOFU_DIMENSION;

    private Configuration conf;
    private TcSaveHandler saveHandler = null;
    private MorijioManager morijioManager = null;

    static
    {
        ModLog.modId = TofuCraftCore.MODID;
        ModLog.isDebug = Settings.debug;

        BIOME_TYPE_TOFU = InitialisationAdapter.getBiomeTypeTofu();
    }

	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		ModInfo.load(metadata);

        conf = new Configuration(event.getSuggestedConfigurationFile());
        conf.load();

        Settings.load(conf);

        conf.save();
        
        if (event.getSide().isClient()) {
    		OBJLoader.INSTANCE.addDomain(TofuCraftCore.MODID);
        }
		
        // Register basic features
		//TcBlockLoader
		TcFluids.registerFluid();
		new LoaderTofuBlock().load(event);
		new LoaderConstructionBlock().load(event);
		new LoaderDecorationBlock().load(event);
		new LoaderExternalBlock().load(event);

		//TcItemLoader
		new LoaderTofuItem().load(event);
		new LoaderMaterial().load(event);
		new LoaderConstruction().load(event);
		new LoaderDecoration().load(event);
		new LoaderFood().load(event);
		new LoaderCombat().load(event);
		new LoaderTool().load(event);
		new LoaderExternalItem().load(event);

		//TcEntity
		TcEntity.register(this);

		// Register liquid blocks
		TcFluids.register(event);
		sidedProxy.regsterMeshDefAndStateMapper();

        // Prepare Tofu Force Materials
        TfMaterialRegistry.init();
        System.out.println("material");
        
        TfCondenserRecipeRegistry.init();
        
        // Add Achievements
        if (Settings.achievement)
        {
            TcAchievements.load();
        }

        // Register enchantments
        TcEnchantment.register(event);
        //TcEnchantment.register(conf);

        // register soundevents
        TcSoundEvents.register();

        // Register biomes (moved from init)
        TcBiomes.register(conf);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

        // Register usage of the bone meal
        MinecraftForge.EVENT_BUS.register(new EventBonemeal());

        // Register usage of the empty bucket
        MinecraftForge.EVENT_BUS.register(new EventFillBucket());

        // Register event on player interact
        MinecraftForge.EVENT_BUS.register(new EventPlayerInteract());

        // Register event on EntityLiving
        MinecraftForge.EVENT_BUS.register(new EventEntityLiving());

        // Register event on tofu fishing
        MinecraftForge.EVENT_BUS.register(new TofuFishing());

        // Register world event handler
        MinecraftForge.EVENT_BUS.register(new EventWorld());

        // Register event on player
        MinecraftForge.EVENT_BUS.register(new EventPlayer());

        // Register gui handler
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TcGuiHandler());

        // Register crafting handler
        MinecraftForge.EVENT_BUS.register(new TcCraftingHandler());

        // Register pick-up Handler
        MinecraftForge.EVENT_BUS.register(new EventItemPickup());
        
        {
            TcChunkProviderEvent eventhandler = new TcChunkProviderEvent();

            // Nether populating
            MinecraftForge.EVENT_BUS.register(eventhandler);

            // Ore generation
            MinecraftForge.ORE_GEN_BUS.register(eventhandler);
        }

        // Register biomes (moved to preInit)
//        TcBiomes.register(conf);

        // Register the Tofu World
        TOFU_DIMENSION = DimensionType.register("Tofu World", "_tofu", DimensionManager.getNextFreeDimId(), WorldProviderTofu.class, false);
        DimensionManager.registerDimension(TOFU_DIMENSION.getId(), TOFU_DIMENSION);

        // Village and Villager Related
        TcVillages.register();

        // Register Packets
        PacketManager.init(MODID);

        // add chest loots
        MinecraftForge.EVENT_BUS.register(new EventLootTableLoad());

        // Register recipes
        Recipes.unifyOreDicItems();
        Recipes.register();
        Recipes.registerExternalModRecipes();

        // CraftGuide
        if (Loader.isModLoaded("craftguide"))
        {
            CraftGuideLoader.load();
        }

        // Register sided components
        sidedProxy.registerComponents();

        //for coloured items/blocks
        SoymilkFlavour.regieterEffectsAndGrades();//after vanilla registry
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TcEntity.addSpawns();

        // Register potion effects
        TcPotion.register(conf);
	}


	@EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        // Register commands
        event.registerServerCommand(new CommandTofuSlimeCheck());
        event.registerServerCommand(new CommandTofuCreeperSpawn());

        // Initialize world save handler
        SaveHandler saveHandler = (SaveHandler) InitialisationAdapter.getServerForDimention(event.getServer(), 0).getSaveHandler();
        this.saveHandler = new TcSaveHandler(saveHandler.getWorldDirectory());

        // Load Morijio info
        this.morijioManager = this.saveHandler.loadMorijioData();

        // To handle spawn of Tofu Creeper ;)
        TofuCreeperSeed.initialize(12L);
        TofuCreeperSeed.instance().initSeed(InitialisationAdapter.getServerForDimention(event.getServer(), 0).getSeed());

        // notify update
	}


	@EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {
    }



    public static TcSaveHandler getSaveHandler()
    {
        return INSTANCE.saveHandler;
    }

    public static MorijioManager getMorijioManager()
    {
        return INSTANCE.morijioManager;
    }

    @SideOnly(Side.SERVER)
    public static class ServerProxy implements ISidedProxy
    {
        @Override
        public void registerComponents()
        {
        	RegisterAdapter.registerTileEntity(TileEntityMorijio.class, "TmMorijio");
        }

		@Override
		public void regsterMeshDefAndStateMapper() {}
    }

    public static interface ISidedProxy
    {
        public void registerComponents();
        public void regsterMeshDefAndStateMapper();
    }



}
