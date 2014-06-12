package fizxFP.managers.controls;

import com.jme3.export.Savable;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author SirFizX
 */
public class Player_FPControl extends AbstractControl implements Savable, Cloneable,ActionListener {

    private boolean left = false, right = false, up = false, down = false;
    private fizxFP.FizXBaseApp app;
    
    public Player_FPControl(fizxFP.FizXBaseApp app){
        this.app = app;
    }
    
    
    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   
    
}// end of class
