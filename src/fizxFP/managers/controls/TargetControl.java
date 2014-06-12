/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers.controls;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import fizxFP.FizXBaseApp;
import java.io.IOException;

/**
 *
 * @author Computer
 */
public class TargetControl extends RigidBodyControl implements PhysicsCollisionListener, PhysicsTickListener, Savable, Cloneable {
    
    private Spatial target;
    private double tt;
    private float displacement = 60f;
    private float speed = 10f;
    private AudioNode hitSound=null;
    private FizXBaseApp fba;
    
    /**
     * 
     */
    public TargetControl(){}
    
    /**
     * 
     * @param bm
     */
    public TargetControl(Spatial t){
        this.target = t;
        // can't access spatial here
    }
    
    public TargetControl(Spatial t,FizXBaseApp fba,CollisionShape shape, float mass,String hitsound){
        super(shape, mass);
        this.target = t;
        System.out.println(shape+" is gooby!");
        this.fba = fba;
        if(hitsound!=null){
           setHitSound(hitsound); 
        }
        setPhysicsSpace(fba.getPhysicsSpace());
        // can't access spatial here
    }
    
    public TargetControl(Spatial t,FizXBaseApp fba,CollisionShape shape, float mass){
        super(shape, mass);
        this.fba = fba;
        this.target =t;
        setPhysicsSpace(fba.getPhysicsSpace());
        // can't access spatial here
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
    super.setSpatial(spatial);
        // optional init method
        // you can get and set user data in the spatial here
    }
    
    public void controlUpdate(float tpf){
        System.out.println("snoopy likes "+spatial);
        if(spatial != null && target != null) {
            
            // Implement your custom control here ...
            // Change scene graph, access and modify userdata in the spatial, etc
            if(target.getUserData("Animation")!=null){
                switch((int)target.getUserData("Animation")){
                    case FizXBaseApp.ANIM_ROTATE_Y :
                        rotateY(tpf);
                        return;
                    case FizXBaseApp.ANIM_SINSCALE :
                        sinScale(tpf);
                        return;
                    case FizXBaseApp.ANIM_YOYO_X :
                        yoyoX(tpf);
                   
                }
            }
            
        }
      
        
    }
    
    protected void controlRender(RenderManager rm, ViewPort vp){
        // optional rendering manipulation (for advanced users)
    }
    @Override
    public Control cloneForSpatial(Spatial spatial){
        final TargetControl control = new TargetControl();
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
    
    private void rotateY(float tpf) {
        target.rotate(0f,tpf,0f);
    }
    
    private void sinScale(float tpf){
        tt+=tpf;
        float s = (float)Math.sin(tt)*5+5f;
        target.setLocalScale(s);
    }
    
    private void yoyoX(float tpf){
        Vector3f sv = (Vector3f)target.getUserData("startPosition");
        float sx = sv.x;
        float cx = target.getWorldTranslation().x;
        if(cx>sx+displacement||cx<sx)speed=-speed;
        target.move(speed*tpf,0f, 0f);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {   
        System.out.println("Barrel Collision impulse is "+event.getAppliedImpulse());
        System.out.println("The event objects are "+event.getObjectA()+" and "+event.getObjectB());
        if (event.getObjectA() == this || event.getObjectB() == this){
                if(event.getAppliedImpulse()>50&&hitSound!=null){
                playHitSound(event.getPositionWorldOnA(),event.getAppliedImpulse()/1300);
               }
        }
    }
    
    public void playHitSound(Vector3f location,float vol){
        hitSound.setPositional(true);
        hitSound.setDirectional(true);
        hitSound.setLocalTranslation(location);
        hitSound.setMaxDistance(400);
        hitSound.setVolume(vol);
        hitSound.setRefDistance(0.01f);
        hitSound.playInstance();
    }
    
    private void setHitSound(String s){
        hitSound = new AudioNode(fba.getAssetManager(), "Sounds/Effects/"+s, false);
    }

    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   
} 
