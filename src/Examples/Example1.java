package Examples;

/*
 * This is an early example of a use case for a small helper library
 * to enable beginners to obtain a somewhat descent result 
 * with fairly little coding.
 */

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import fizxFP.*;

/**
 *
 * @author SirFizX
 */
public class Example1 extends FizXBaseApp {
    
     /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        Example1 app = new Example1();
        app.start();
    }
    
     /**
     * 
     */
    @Override
    public void simpleInitApp() {
       super.simpleInitApp();
       //Add your initialization code here. Use type "this." to see 
       //some convenient methods
       this.addDynamicBoxTarget("spiky",new Vector3f(0f, 2f, -20f),"wood-step.wav",10f);
       this.addTerrain("flatDirt", Vector3f.ZERO);
       this.setAmbientLightIntensity(3f);
       this.enableShooting();
    }
    
     @Override
    public void simpleRender(RenderManager rm) {
         super.simpleRender(rm);
        //TODO: add render code
    }
    
    /**
     * 
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        //TODO: add update code
    }
    
}

 
