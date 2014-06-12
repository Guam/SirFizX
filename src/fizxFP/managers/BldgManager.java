package fizxFP.managers;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.managers.controls.BldgControl;
import fizxFP.FizXBaseApp;
import java.util.ArrayList;
import java.util.List;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class BldgManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl bldgPhys;
    private BldgControl bldgControl;
    private List<Spatial> buildings = new ArrayList<Spatial>();
    private float scale = 4f;
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public BldgManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.bldgControl = new BldgControl(this);
        this.bldgControl.setEnabled(true);
        this.addControl(this.bldgControl);
    }
    
    /**
     * The buildingName parameter is the name of the .j3o file found
      * in the Models/Buildings/buildingName/ directory. The location parameter
     * is the desired world space translation you want for the building.
     * @param app
     * @param buildingName
      * @param location
     */
    public BldgManager(FizXBaseApp app, String buildingName, Vector3f location){
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.bldgControl = new BldgControl(this);
        this.bldgControl.setEnabled(true);
        this.addControl(this.bldgControl); 
        Spatial bldg = this.assetManager.loadModel("Models/Buildings/"+buildingName+"/"+buildingName+".j3o");
        bldg.setName(buildingName);
        bldg.setUserData("Destructibility", 4);
        bldg.move(location);
        addBldg(bldg);
    }
    
     private void addBldg(Spatial b) {
        
        this.buildings.add(b);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) b);
        bldgPhys = new RigidBodyControl(sceneShape, 0);
        bldgPhys.setFriction(1.5f);
        b.addControl(bldgPhys);


        this.app.getRootNode().attachChild(b);
        this.app.bulletAppState.getPhysicsSpace().add(b);

  }
    
    public void addBldg(String buildingName, Vector3f location) {
        System.out.println(buildingName);
        Spatial bldg = this.assetManager.loadModel("Models/Buildings/"+buildingName+"/"+buildingName+".j3o");
        bldg.setName(buildingName);
        bldg.setUserData("Destructibility", 4);
        bldg.move(location);
        bldg.setLocalScale(scale);
        System.out.println("The buildings list is "+this.buildings);
        System.out.println(this.app.getStateManager().hasState(this.app.bulletAppState));
        this.buildings.add(bldg);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) bldg);
        bldgPhys = new RigidBodyControl(sceneShape, 0);
        bldgPhys.setFriction(1.5f);
        bldg.addControl(bldgPhys);

        this.app.getRootNode().attachChild(bldg);
        this.app.bulletAppState.getPhysicsSpace().add(bldg);

  }
    
       public void addBldg(String buildingName, Vector3f location, int DESTRUCTIBILITY) {
        System.out.println(buildingName);
        Spatial bldg = this.assetManager.loadModel("Models/Buildings/"+buildingName+"/"+buildingName+".j3o");
        bldg.setName(buildingName+this.buildings.size());
        bldg.setUserData("Points", 10);
        bldg.setUserData("Destructibility", DESTRUCTIBILITY);
        bldg.move(location);
        bldg.setLocalScale(scale);
        this.buildings.add(bldg);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) bldg);
        bldgPhys = new RigidBodyControl(sceneShape, 0);
        bldg.addControl(bldgPhys);
        bldgPhys.setFriction(1.5f);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(bldg); 
        }
        this.app.getRootNode().attachChild(bldg);
        this.app.bulletAppState.getPhysicsSpace().add(bldg);

  }
    
    
    
}
