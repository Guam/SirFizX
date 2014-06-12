/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import fizxFP.managers.controls.SignControl;
import fizxFP.FizXBaseApp;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class SignManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl signPhys;
    private SignControl signControl;
    public List<Spatial> signs = new ArrayList<Spatial>();
    public List<BitmapText> signtextlist = new ArrayList<BitmapText>();
    private float scale = 4f;
    private String signType = "plainWood";
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public SignManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.signControl = new SignControl(this);
        this.signControl.setEnabled(true);
        this.addControl(this.signControl);
    }
    
    /**
     * The buildingName parameter is the name of the .j3o file found
      * in the Models/Buildings/buildingName/ directory. The location parameter
     * is the desired world space translation you want for the building.
     * @param app
     * @param buildingName
      * @param location
     */
    public SignManager(FizXBaseApp app, String signName, Vector3f location){
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.signControl = new SignControl(this);
        this.signControl.setEnabled(true);
        this.addControl(this.signControl); 
        Spatial sign = this.assetManager.loadModel("Models/Signs/"+signName+"/"+signName+".j3o");
        sign.setName(signName);
        sign.setUserData("Destructibility", 4);
        sign.move(location);
        addSign(sign);
    }
    
     private void addSign(Spatial s) {
        
        this.signs.add(s);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) s);
        signPhys = new RigidBodyControl(sceneShape, 0);
        s.addControl(signPhys);


        this.app.getRootNode().attachChild(s);
        this.app.bulletAppState.getPhysicsSpace().add(s);

  }
    
    public void addSign(String signName, Vector3f location, String text) {
        Spatial sign = this.assetManager.loadModel("Models/Signs/"+signType+"/"+signType+".j3o");
        sign.setName(signName);
        sign.setUserData("Destructibility", 4);
        sign.setUserData("IsSign", 1);
        //sign.move(new Vector3f(location.x+4f,location.y-3.2f,location.z-.7f));
        sign.move(location);
        sign.setLocalScale(scale);
        this.signs.add(sign);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) sign);
        signPhys = new RigidBodyControl(sceneShape, 0);
        sign.addControl(signPhys);

        this.app.getRootNode().attachChild(sign);
        this.app.bulletAppState.getPhysicsSpace().add(sign);
        
        BitmapFont fnt = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setName(signName+"text");
        System.out.println("txt name is "+txt.getName());
        this.signtextlist.add(txt);
        txt.setBox(new Rectangle(0, 0, 8, 4));
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( scale/4f );
        txt.setText(text);
        txt.setLocalTranslation(location);
        this.app.getRootNode().attachChild(txt);

  }
    
       public void addSign(String signName, Vector3f location, String text, int DESTRUCTIBILITY, int POINTS) {
        System.out.println("Please say "+signName);
        Spatial sign = this.assetManager.loadModel("Models/Signs/"+signType+"/"+signType+".j3o");
        sign.setName(signName);
        sign.setUserData("Points", POINTS);
        sign.setUserData("Destructibility", DESTRUCTIBILITY);
        sign.setUserData("IsSign", 1);
        sign.move(location);
        sign.setLocalScale(scale);
        this.signs.add(sign);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) sign);
        signPhys = new RigidBodyControl(sceneShape, 0);
        sign.addControl(signPhys);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(sign); 
        }
        this.app.getRootNode().attachChild(sign);
        this.app.bulletAppState.getPhysicsSpace().add(sign);
        
        BitmapFont fnt = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setName(signName+"text");
        System.out.println("the sgntxt is "+txt.getName());
        this.signtextlist.add(txt);
        txt.setBox(new Rectangle(7, 10, 10, 5)); 
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( scale/4f );
        txt.setText(text);
        txt.move(location);
        //txt.setLocalTranslation(location);
        this.app.getRootNode().attachChild(txt);

  }
    
    
    
}
