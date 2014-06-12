/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers.controls;

import fizxFP.managers.TerrainManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Computer
 */
public class TerrainControl extends AbstractControl implements Savable, Cloneable {
    
    private TerrainManager terrainManager;
    
    /**
     * 
     */
    public TerrainControl(){}
    
    /**
     * 
     * @param bm
     */
    public TerrainControl(TerrainManager tm){
        this.terrainManager = tm;
        // can't access spatial here
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
    super.setSpatial(spatial);
        // optional init method
        // you can get and set user data in the spatial here
    }
    @Override
    protected void controlUpdate(float tpf){
        if(spatial != null && terrainManager != null) {
            // Implement your custom control here ...
            // Change scene graph, access and modify userdata in the spatial, etc
            
        }
        System.out.println(spatial+" from TerrainControl update");
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp){
        // optional rendering manipulation (for advanced users)
    }
    @Override
    public Control cloneForSpatial(Spatial spatial){
        final TerrainControl control = new TerrainControl();
        control.setSpatial(spatial);
        return control;
    }
    /**
     * 
     * @param im
     * @throws IOException
     */
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        // im.getCapsule(this).read(...);
    }
    
    /**
     * 
     * @param ex
     * @throws IOException
     */
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        // ex.getCapsule(this).write(...);
    }
} 
