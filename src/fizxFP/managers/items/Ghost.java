package fizxFP.managers.items;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;
import fizxFP.FizXBaseApp;
import fizxFP.managers.controls.GhostControl;

/**
 *  This class loads an intended "target" model from the appropriate
 *  folder and assigns it a collision shape, rigid body control, and
 *  target control.
 * 
 * @author SirFizX
 */
public class Ghost extends Node{
    
public Spatial t;
    
    
public Ghost(String targetName,FizXBaseApp app,Vector3f location,String hitsound,int animation){
        //Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o").clone();
        
        /** A translucent/transparent texture. */
    Quad quad = new Quad(2f,2f);
    Geometry target = new Geometry("ghost", quad);
    Material mat_tt = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat_tt.setTexture("ColorMap", app.getAssetManager().loadTexture("Textures/ghost1/ghost1.png"));
    mat_tt.getAdditionalRenderState().setBlendMode(BlendMode.Alpha); // activate transparency
    target.setMaterial(mat_tt);
    target.setQueueBucket(Bucket.Transparent);
    //app.getRootNode().attachChild(quadGeom);
        
        
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.center().move(location);
        target.setUserData("startPosition", location);
        target.setUserData("Animation", animation);
        float xBound = ((BoundingBox)target.getWorldBound()).getXExtent();
        float yBound = ((BoundingBox)target.getWorldBound()).getYExtent();
        float zBound = ((BoundingBox)target.getWorldBound()).getZExtent();
        BoxCollisionShape bcs = new BoxCollisionShape(new Vector3f(xBound,yBound,zBound));
        GhostControl GhostPhys = new GhostControl(app, bcs,50f, hitsound, target);
        GhostPhys.setEnabled(true);
        GhostPhys.setKinematic(true); // allows for animation
        GhostPhys.setFriction(0.5f);
        //GhostPhys.setMass(50f);
        GhostPhys.setGravity(new Vector3f(0f,-20f, 0f));
        target.addControl(GhostPhys);// add physics control for animation and weapon collisions
        BillboardControl bbc = new BillboardControl();
        target.addControl(bbc);// add billboard control to control facing cam
        this.t = target;
    }
    
}
