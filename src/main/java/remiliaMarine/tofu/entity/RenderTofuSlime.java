package remiliaMarine.tofu.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderTofuSlime extends RenderSlime {
	
    private static final ResourceLocation TEXTURE = new ResourceLocation("tofucraft", "textures/mob/tofuslime.png");
    
	public RenderTofuSlime(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn)
    {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }
	
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
	@Override
    protected ResourceLocation getEntityTexture(EntitySlime entity)
    {
        return TEXTURE;
    }
}
