package remiliaMarine.tofu.block.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.block.BlockSaltPan;
import remiliaMarine.tofu.tileentity.TileEntitySaltPan;

public class RenderSaltPan extends TileEntitySpecialRenderer<TileEntitySaltPan> {
	
	public static final ResourceLocation[] WATER = new ResourceLocation[32];
	public static final ResourceLocation[] BITTERN = new ResourceLocation[32];
	public static final ResourceLocation WATER_TEXTURE = new ResourceLocation("minecraft","textures/blocks/water_still.png");
	public static final ResourceLocation SALT_TEXTURE = new ResourceLocation("tofucraft","textures/blocks/blockSalt.png");
	public static final ResourceLocation BITTERN_TEXTURE = new ResourceLocation("tofucraft","textures/blocks/nigari.png");
	
	private final ModelSaltpan modelSaltpan = new ModelSaltpan();
	
	static {
		for (int i = 0; i < 32; i++) {
			WATER[i] = new ResourceLocation("tofucraft", "textures/blocks/animated/water/water_still_"+ String.valueOf(i) +".png");
			BITTERN[i] = new ResourceLocation("tofucraft", "textures/blocks/animated/bittern/nigari_"+ String.valueOf(i) +".png");
		}
	}
	
	
	@Override
    public void renderTileEntityAt(TileEntitySaltPan te, double x, double y, double z, float partialTicks, int destroyStage) {
        
		BlockSaltPan panframe = (BlockSaltPan)te.getBlockType();
		World worldIn = te.getWorld();
		BlockPos framePos = te.getPos();
		BlockSaltPan.Stat myStat = panframe.getStat(worldIn, framePos);
		
        GL11.glPushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
        GL11.glTranslatef((float)x, (float)y, (float)z);

         
        //content
    	ResourceLocation contentTexture;
    	int frame = (int)te.getWorld().getWorldInfo().getWorldTime()%64/2;
    	if (myStat == BlockSaltPan.Stat.WATER){
    		contentTexture = WATER[frame];
    	}
    	else if (myStat == BlockSaltPan.Stat.BITTERN){
    		contentTexture = BITTERN[frame];

    	}
    	else if (myStat == BlockSaltPan.Stat.SALT){
    		contentTexture = SALT_TEXTURE;
    	}
    	else contentTexture = null;
    	
    	if (contentTexture != null) {
    		
    		boolean edgeNorth = this.shouldSideBeRendered(worldIn, myStat, framePos.north(), panframe);
    		boolean edgeEast = this.shouldSideBeRendered(worldIn, myStat, framePos.east(), panframe);
    		boolean edgeSouth = this.shouldSideBeRendered(worldIn, myStat, framePos.south(), panframe);
    		boolean edgeWest = this.shouldSideBeRendered(worldIn, myStat, framePos.west(), panframe);
    		
        	this.bindTexture(contentTexture);
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        	if(myStat != BlockSaltPan.Stat.SALT) GlStateManager.depthMask(false);

        	GlStateManager.enableAlpha();
        	
        	this.modelSaltpan.renderContent();
        	this.modelSaltpan.renderContentEdge(edgeNorth, edgeEast, edgeSouth, edgeWest);
        	
        	GlStateManager.depthMask(true);
    	}
        
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }
	
	private boolean shouldSideBeRendered(World worldIn, BlockSaltPan.Stat myStat, BlockPos pos, BlockSaltPan panframe) {
		BlockSaltPan.Stat stat = panframe.getStat(worldIn, pos);
		return stat == BlockSaltPan.Stat.EMPTY;// ||
				//(myStat == BlockSaltPan.Stat.SALT &&(stat == BlockSaltPan.Stat.WATER || stat == BlockSaltPan.Stat.BITTERN));
	}
	
}
