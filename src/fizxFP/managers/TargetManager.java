/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fizxFP.managers.items.DropTarget;
import fizxFP.FizXBaseApp;
import fizxFP.managers.items.Ghost;
import fizxFP.managers.items.Target;
import fizxFP.managers.controls.TargetControl;
//import fizxFP.managers.controls.TargetManagerControl;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 */
public class TargetManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl targetPhys;
    // Eliminated TargetManagerControl class in favor 
    //of calling target updates from the FizXBaseApp class
    //private TargetManagerControl targetManagerControl;
    public List<Spatial> targets = new ArrayList<Spatial>();
    public List<Spatial> ghosts = new ArrayList<Spatial>(); 
    public List<Spatial> indestructibles = new ArrayList<Spatial>();
    public List<Spatial> dropables = new ArrayList<Spatial>();
    private float scale;
    public float padding = 10f;
    
    public static int DISTRIBUTION_GRID = 1;
    public static int DISTRIBUTION_SPIRAL = 2;
    private static String x = "x";
    private static String z = "z";
    private static String y = "y";
    
    /**
     * Simple constructor 
     * @param app
     */
    public TargetManager(FizXBaseApp app){
        
        this.app = app; 
        this.assetManager = app.getAssetManager();
        // Dropped TargetManagerControl Class
        //this.targetManagerControl = new TargetManagerControl(this);
        //this.targetManagerControl.setEnabled(true);
        //this.addControl(this.targetManagerControl);
        this.scale = FizXBaseApp.GLOBAL_SCALE;
    }
 
    public void addTarget(String targetName, Vector3f location) {
        Target target = new Target(targetName,app,location);
        //May want to NOT add the target control if the user
        //wishes to have all targets behave the same
        //TODO: update code to consider the above.
        TargetControl targetControl = new TargetControl(target);
        targetControl.setEnabled(true);
        target.addControl(targetControl);
        target.setName(targetName+this.targets.size());
        //target.move(location);
        //target.setLocalScale(scale);
        target.setUserData("Points", 10);
        target.setUserData("Destructibility", 1);
        target.setUserData("Target_Type", 1);
        this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
        this.targets.add(target);
        this.app.getWeaponsManager().getDestructiblesManager().attachChild(target); // testing
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    public void addDynamicBoxTarget(String targetName, Vector3f location,String hitsound,float mass) {
        Spatial target = new DropTarget(this.app,targetName,location,hitsound,mass).t;
        target.setName(targetName+this.dropables.size());
        System.out.println(target+" is the drop target.");
        System.out.println(target.getWorldTranslation()+" is the drop target position.");
        dropables.add(target);
        this.app.getRootNode().attachChild(target);
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    public void addDynamicBoxTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE,String hitsound) {
        Spatial target = new DropTarget( app,targetName,location,hitsound,10).t;
        target.setName(targetName+"ind"+this.indestructibles.size());
        
        System.out.println(DESTRUCTIBILITY+" is the btw!");
        System.out.println(target.getWorldTranslation()+" is the target position.");
        target.setUserData("Points", POINT_VALUE);
        target.setUserData("Destructibility", DESTRUCTIBILITY);
        target.setUserData("Target_Type", 1);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
        } else {
           this.indestructibles.add(target);
         }
        this.app.getRootNode().attachChild(target);
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE) {
        Spatial target = new Target(targetName, app,location).t;
        TargetControl targetControl = new TargetControl(target);
        targetControl.setEnabled(true);
        target.addControl(targetControl);
        target.setName(targetName+this.targets.size());
        System.out.println(target);
        System.out.println(target.getWorldTranslation()+" is the target position.");
        target.setUserData("Points", POINT_VALUE);
        target.setUserData("Destructibility", DESTRUCTIBILITY);
        target.setUserData("Target_Type", 1);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
        }
        this.app.getRootNode().attachChild(target);
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE, int ANIMATION) {
        Spatial target = new Target(targetName, app,location).t;
        TargetControl targetControl = new TargetControl(target);
        targetControl.setEnabled(true);
        target.addControl(targetControl);
        target.setName(targetName+this.targets.size());
        System.out.println(target);
        System.out.println(target.getWorldTranslation()+" is the target position.");
        target.getControl(RigidBodyControl.class).setKinematic(true);
        target.setUserData("Animation", ANIMATION);
        if(ANIMATION==FizXBaseApp.ANIM_DROP){
            dropables.add(target);
        }
        target.setUserData("Points", POINT_VALUE);
        target.setUserData("Destructibility", DESTRUCTIBILITY);
        target.setUserData("Target_Type", 1);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
        }
        this.targets.add(target);
        this.app.getRootNode().attachChild(target);
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    public void addDropTarget(String targetName, Vector3f location) {
        Spatial target = new DropTarget(targetName,this.app,location).t;
        target.setName(targetName+this.dropables.size());
        System.out.println(target+" is the drop target.");
        System.out.println(target.getWorldTranslation()+" is the drop target position.");
        dropables.add(target);
        this.app.getRootNode().attachChild(target);
        this.app.bulletAppState.getPhysicsSpace().add(target);
  }
    
    //Archimedean Spiral r=a+b*theta
    public void addMultipleTargets(String targetName,Vector3f firstPosition, int DISTRIBUTION, int distributionSize, int DESTRUCTIBILITY, int POINT_VALUE){
        Spatial target = new Target(targetName, app,firstPosition).t;
        float offsetX = getBound(target,x)+padding;
        float offsetZ = getBound(target,z)+padding;
        float offsetY = getBound(target,y)+padding;
        int s = distributionSize;
        if(DISTRIBUTION<3){      //GRID
            if(DISTRIBUTION==FizXBaseApp.DISTRIBUTION_HORIZ_GRID){
                    for(int i=1;i<s+1;i++){
                        for(int j=1;j<s+1;j++){
                            float tox = (i-1)*offsetX;
                            float toz = (j-1)*offsetZ;
                            TargetControl targetControl = new TargetControl(target);
                            targetControl.setEnabled(true);
                            target.addControl(targetControl);
                            target.setName(targetName+this.targets.size());
                            target = new Target(targetName,app,firstPosition.add(tox,0f,toz)).t;
                            target.setUserData("Points", POINT_VALUE);
                            target.setUserData("Destructibility", DESTRUCTIBILITY);
                            target.setUserData("Target_Type", 1);
                            if(DESTRUCTIBILITY<4){
                               this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
                            }
                            this.targets.add(target);
                            this.app.getRootNode().attachChild(target);
                            this.app.bulletAppState.getPhysicsSpace().add(target);  
                        }
                    }
            } else if(DISTRIBUTION==FizXBaseApp.DISTRIBUTION_VERT_GRID) {
                     for(int i=1;i<s+1;i++){
                        for(int j=1;j<s+1;j++){
                            float tox = (i-1)*offsetX;
                            float toy = (j-1)*offsetY;
                            TargetControl targetControl = new TargetControl(target);
                            targetControl.setEnabled(true);
                            target.addControl(targetControl);
                            target.setName(targetName+this.targets.size());
                            target = new Target(targetName,app,firstPosition.add(tox,toy,0f)).t;
                            target.setUserData("Points", POINT_VALUE);
                            target.setUserData("Destructibility", DESTRUCTIBILITY);
                            target.setUserData("Target_Type", 1);
                            if(DESTRUCTIBILITY<4){
                               this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
                            }
                            this.targets.add(target);
                            this.app.getRootNode().attachChild(target);
                            this.app.bulletAppState.getPhysicsSpace().add(target);          
                        }
                    }
            }
               
            
        } else if(DISTRIBUTION==FizXBaseApp.DISTRIBUTION_VERT_SPIRAL){  // VERTICAL SPIRAL
            System.out.println("Making vertical spiral target pattern.");
            for(int j=1;j<s*12;j++){
                            float tox = (j-1)*offsetX;
                            float toy = (j-1)*offsetY;
                            float tor =(float) Math.sqrt(tox*tox+toy*toy);
                            Vector3f sph = new Vector3f(tor,0f,3.1459f*j/3f);
                            Vector3f cart = new Vector3f();
                            FastMath.sphericalToCartesian(sph,cart);
                            TargetControl targetControl = new TargetControl(target);
                            targetControl.setEnabled(true);
                            target.addControl(targetControl);
                            target.setName(targetName+this.targets.size());
                            target = new Target(targetName,app,firstPosition.add(cart)).t;
                            target.setUserData("Points", POINT_VALUE);
                            target.setUserData("Destructibility", DESTRUCTIBILITY);
                            target.setUserData("Target_Type", 1);
                            if(DESTRUCTIBILITY<4){
                               this.app.getWeaponsManager().getDestructiblesManager().addDestructible(target); 
                            }
                            this.targets.add(target);
                            this.app.getRootNode().attachChild(target);
                            this.app.bulletAppState.getPhysicsSpace().add(target);          
                        }
        }
        
    }
    
    private float getBound(Spatial s,String x_or_y_or_z){
        if(x_or_y_or_z=="x"){
            return ( (BoundingBox)s.getWorldBound()).getXExtent();
        }else if(x_or_y_or_z=="y"){
            return ( (BoundingBox)s.getWorldBound()).getYExtent();
        }else{
            return ( (BoundingBox)s.getWorldBound()).getZExtent();
        }
    }
    
  
    
    public void setPadding(float padding){
        this.padding = padding;
    }
    
    public void addGhost(String targetName, Vector3f location,String hitsound,int animation) {
        Spatial ghost = new Ghost(targetName,this.app,location,hitsound,animation).t;
        ghost.setName(targetName+"GHOSTY"+this.ghosts.size());
        ghost.setUserData("Points", 10);
        ghost.setUserData("Destructibility", 1);
        ghost.setUserData("Target_Type",3);
        targets.add(ghost);
        this.app.getWeaponsManager().getDestructiblesManager().addDestructible(ghost); 
        this.ghosts.add(ghost);
        this.app.getRootNode().attachChild(ghost);
        this.app.bulletAppState.getPhysicsSpace().add(ghost);
  }
    
}
