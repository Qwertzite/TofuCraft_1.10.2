package remiliaMarine.tofu.block.render;

import net.minecraft.client.model.ModelBase;

public class TcModelBase extends ModelBase {
	
    public boolean doesAnimate() {
    	return true;
    }
    
    public int getAnimationTick() {
    	return 0; //TODO
    }
}
