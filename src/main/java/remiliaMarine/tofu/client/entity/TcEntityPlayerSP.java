package remiliaMarine.tofu.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import remiliaMarine.tofu.gui.inventory.GuiSaltFurnace;
import remiliaMarine.tofu.tileentity.TileEntitySaltFurnace;

public class TcEntityPlayerSP extends AbstractClientPlayer {
    public final NetHandlerPlayClient connection;
    private final StatisticsManager statWriter;
    private int permissionLevel = 0;
    /**
     * The last X position which was transmitted to the server, used to determine when the X position changes and needs
     * to be re-trasmitted
     */
    private double lastReportedPosX;
    /**
     * The last Y position which was transmitted to the server, used to determine when the Y position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosY;
    /**
     * The last Z position which was transmitted to the server, used to determine when the Z position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosZ;
    /**
     * The last yaw value which was transmitted to the server, used to determine when the yaw changes and needs to be
     * re-transmitted
     */
    private float lastReportedYaw;
    /**
     * The last pitch value which was transmitted to the server, used to determine when the pitch changes and needs to
     * be re-transmitted
     */
    private float lastReportedPitch;
    private boolean prevOnGround;
    /** the last sneaking state sent to the server */
    private boolean serverSneakState;
    /** the last sprinting state sent to the server */
    private boolean serverSprintState;
    /**
     * Reset to 0 every time position is sent to the server, used to send periodic updates every 20 ticks even when the
     * player is not moving.
     */
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String serverBrand;
    public MovementInput movementInput;
    protected Minecraft mc;
    /**
     * Used to tell if the player pressed forward twice. If this is at 0 and it's pressed (And they are allowed to
     * sprint, aka enough food on the ground etc) it sets this to 7. If it's pressed and it's greater than 0 enable
     * sprinting.
     */
    protected int sprintToggleTimer;
    /** Ticks left before sprinting is disabled. */
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    /** The amount of time an entity has been in a Portal */
    public float timeInPortal;
    /** The amount of time an entity has been in a Portal the previous tick */
    public float prevTimeInPortal;
    private boolean handActive;
    private EnumHand activeHand;
    private boolean rowingBoat;
    private boolean autoJumpEnabled = true;
    private int autoJumpTime;
    private boolean wasFallFlying;
    public TcEntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatisticsManager statFile)
    {
        super(worldIn, netHandler.getGameProfile());
        this.connection = netHandler;
        this.statWriter = statFile;
        this.mc = mcIn;
        this.dimension = 0;
    }
    
    
    
    
    /**
     * Displays the GUI for interacting with a chest inventory.
     */
    @Override
    public void displayGUIChest(IInventory chestInventory) {
    	String s = chestInventory instanceof IInteractionObject ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
    	
        if ("tofucraft:saltFurnace".equals(s))
        {
        	System.out.println("yonnda?");
            this.mc.displayGuiScreen(new GuiSaltFurnace(this.inventory, (TileEntitySaltFurnace) chestInventory));
        }
    }
    
    
    
    
}
