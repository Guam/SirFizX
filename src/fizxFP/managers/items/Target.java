package fizxFP.managers.items;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.FizXBaseApp;
import fizxFP.managers.controls.TargetControl;


/**
 *  This class loads an intended "target" model from the appropriate
 *  folder and assigns it a collision shape, rigid body control, and
 *  target control.
 * 
 * @author SirFizX
 */
public class Target extends Node{
    
   public Spatial t;
   public CollisionShape sceneShape;
    
    public Target(String targetName, FizXBaseApp app,Vector3f location,String hitsound){
        Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o").clone();
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.move(location);
        target.setUserData("startPosition", location);
        sceneShape =
                CollisionShapeFactory.createMeshShape((Node) target);
        TargetControl targetPhys = new TargetControl(target,app,sceneShape,20,hitsound);
        targetPhys.setEnabled(true);
        targetPhys.setFriction(0.5f);
        target.addControl(targetPhys);
        this.t = target;
    }
    // for immovable but destroyable targets
    public Target(String targetName, FizXBaseApp app,Vector3f location){
        Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o");
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.move(location);
        target.setUserData("startPosition", location);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) target);
        TargetControl targetPhys = new TargetControl(target,app,sceneShape,0);
        targetPhys.setEnabled(true);
        targetPhys.setKinematic(true);
        targetPhys.setFriction(0.5f);
        target.addControl(targetPhys);
        this.t = target;
    }
    
}
