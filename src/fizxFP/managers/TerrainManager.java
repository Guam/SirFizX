package fizxFP.managers;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.FizXBaseApp;
import fizxFP.managers.controls.TerrainControl;


/**
 * 
 */
public class TerrainManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl terrainPhys;
    private TerrainControl terrainControl;
    private Spatial terrain;
    private float scale = 2f;
    
    /**
     * Simple constructor 
     * @param app
     */
    public TerrainManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.terrainControl = new TerrainControl(this);
        this.terrainControl.setEnabled(true);
        this.addControl(this.terrainControl);
    }
    
    
     private void addTerrain(Spatial b) {
        
        this.terrain = b;
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) b);
        terrainPhys = new RigidBodyControl(sceneShape, 0);
        terrainPhys.setFriction(1.5f);
        b.addControl(terrainPhys);


        this.app.getRootNode().attachChild(b);
        this.app.bulletAppState.getPhysicsSpace().add(terrainPhys);

  }
    
    public void addTerrain(String terrainName, Vector3f location) {
        Spatial terr = this.assetManager.loadModel("Models/Terrains/"+terrainName+".j3o");
        terr.setName(terrainName);
        terr.move(location);
        terr.setLocalScale(scale);
        this.terrain = terr;
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) terr);
        terrainPhys = new RigidBodyControl(sceneShape, 0);
        terrainPhys.setEnabled(true);
        terr.addControl(terrainPhys);
        
        this.app.getRootNode().attachChild(terr);
        this.app.bulletAppState.getPhysicsSpace().add(terr);

  }
    
    
}
