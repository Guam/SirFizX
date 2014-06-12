package fizxFP.managers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.managers.controls.DestructiblesControl;
import fizxFP.FizXBaseApp;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 */
public class DestructiblesManager extends Node {
  
    private FizXBaseApp app;
    private DestructiblesControl destroyControl;
    public List<Spatial> destructibles = new ArrayList<Spatial>();
    
    /**
     * Simple constructor 
     * @param app
     */
    public DestructiblesManager(FizXBaseApp app){
        
        this.app = app;
        this.destroyControl = new DestructiblesControl(this);
        this.destroyControl.setEnabled(true);
        this.addControl(this.destroyControl);
    }
    
     public void addDestructible(Spatial b) {
        
        this.destructibles.add(b);

  }
     
    
}
