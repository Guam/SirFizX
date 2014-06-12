/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers.items;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import fizxFP.FizXBaseApp;

/**
 *
 * @author Computer
 */
public class Fire {
    
    private FizXBaseApp app;
    private Vector3f location;
    
    public Fire(FizXBaseApp app, Vector3f location){
        
    this.app = app;
    this.location = location;
    init();
}

    private void init(){
         /** Uses Texture from jme3-test-data library! */
        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", app.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
        fire.setMaterial(mat_red);
        fire.setImagesX(2); fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 0.5f));   // red
        fire.setStartColor(new ColorRGBA(5f, 0.1f, 0f, 0.5f)); // yellow
        //fire.setStartVel(new Vector3f(0, 2, 0));
        fire.setStartSize(1.7f);
        fire.setEndSize(0.1f);
        fire.setGravity(0, -2, 0);
        fire.setLowLife(0.5f);
        fire.setHighLife(3f);
        fire.setLocalTranslation(location);
        app.getRootNode().attachChild(fire);
       

    }
}

