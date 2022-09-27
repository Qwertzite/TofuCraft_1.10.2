package remiliaMarine.tofu.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.model.ModelTofunian;

public class RenderTofunian extends RenderBiped<EntityTofunian> {

    private static final ResourceLocation texture = new ResourceLocation("tofucraft", "textures/mob/tofunian.png");
    /** field_82434_o */
    //private final ModelBiped field_82434_o;
    //private final int field_82431_q = 1;
	
    public RenderTofunian(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTofunian(), 0.5F, 1.0F);
        //this.field_82434_o = this.modelBipedMain;
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelTofunian(0.5F, true);
                this.modelArmor = new ModelTofunian(1.0F, true);
            }
        });
    }
    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTofunian entity)
    {
        return texture;
    }

    
    
}
