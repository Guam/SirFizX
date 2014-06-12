package fizxFP.managers.items;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.managers.controls.DropTargetControl;
import fizxFP.FizXBaseApp;


/**
 *  This class loads an intended "target" model from the appropriate
 *  folder and assigns it a collision shape, rigid body control, and
 *  target control.
 * 
 * @author SirFizX
 */
public class DropTarget extends Node{
    
   public Spatial t;
    
    public DropTarget(String targetName,FizXBaseApp app,Vector3f location){
        Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o").clone();
        
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.center().move(location);
        target.setUserData("startPosition", location);
        target.setUserData("Target_Type", 2);
        float xBound = ((BoundingBox)target.getWorldBound()).getXExtent();
        float yBound = ((BoundingBox)target.getWorldBound()).getYExtent();
        float zBound = ((BoundingBox)target.getWorldBound()).getZExtent();
        BoxCollisionShape bcs = new BoxCollisionShape(new Vector3f(xBound,yBound,zBound));
        DropTargetControl targetPhys = new DropTargetControl(app,bcs, 30f);
        targetPhys.setEnabled(true);
        targetPhys.setKinematic(true);
        targetPhys.setFriction(0.5f);
        targetPhys.setMass(50f);
        targetPhys.setGravity(new Vector3f(0f,-20f, 0f));
        target.addControl(targetPhys);
        this.t = target;
    }
    
    // Currently adding a drop target with a hitsound parameter in the constructor
    // generates a target immediately under the influence of gravity and
    // impulses from collisions
    public DropTarget(FizXBaseApp app,String targetName,Vector3f location,String hitsound,float mass){
        Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o").clone();
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.center().move(location);
        target.setUserData("startPosition", location);
        target.setUserData("Target_Type", 2);
        float xBound = ((BoundingBox)target.getWorldBound()).getXExtent();
        float yBound = ((BoundingBox)target.getWorldBound()).getYExtent();
        float zBound = ((BoundingBox)target.getWorldBound()).getZExtent();
        BoxCollisionShape bcs = new BoxCollisionShape(new Vector3f(xBound,yBound,zBound));
        DropTargetControl targetPhys = new DropTargetControl(app,bcs, 30f,hitsound);
        targetPhys.setEnabled(true);
        //targetPhys.setKinematic(true);
        targetPhys.setFriction(0.5f);
        targetPhys.setMass(mass);
        targetPhys.setGravity(new Vector3f(0f,-20f, 0f));
        target.addControl(targetPhys);
        this.t = target;
    }
    
}
