package fizxFP.managers.controls;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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

/*
 * @author SirFizX
 */
public class DropTargetControl extends RigidBodyControl implements PhysicsCollisionListener, PhysicsTickListener, Savable, Cloneable {
    
    private Spatial target;
    private double tt;
    private float displacement = 60f;
    private float speed = 10f;
    private AudioNode thonk;
    private boolean dropped = false;
    private fizxFP.FizXBaseApp app;
    /*
     * 
     */
    public DropTargetControl(){}
    
    /**
     * 
     * @param bm
     */
    public DropTargetControl(FizXBaseApp fba,BoxCollisionShape shape, float mass){
        super(shape, mass);
        this.app =fba;
        thonk = new AudioNode(fba.getAssetManager(), "Sounds/Effects/Bang.wav", false);
   
        setPhysicsSpace(fba.getPhysicsSpace());
        // can't access spatial here
    }
    
    public DropTargetControl(FizXBaseApp fba,BoxCollisionShape shape, float mass,String hitsound){
        super(shape, mass);
        
        thonk = new AudioNode(fba.getAssetManager(), "Sounds/Effects/"+hitsound, false);
        
        setPhysicsSpace(fba.getPhysicsSpace());
        this.app = fba;
        // can't access spatial here
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
    super.setSpatial(spatial);
        // optional init method
        // you can get and set user data in the spatial here
    }
    
    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        if (space != null) {
            space.addCollisionListener(this);
        }
    }
    
    protected void controlUpdate(float tpf){
        System.out.println("troopy");
        super.update(tpf);
        if(spatial != null && target != null) {
            // Implement your custom control here ...
            // Change scene graph, access and modify userdata in the spatial, etc
            if(target.getUserData("Animation")!=null){
                switch((Integer)target.getUserData("Animation")){
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
        final DropTargetControl control = new DropTargetControl();
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

    public void collision(PhysicsCollisionEvent event) {
        //if(event.getNodeA().getName()=="Player" || event.getNodeB().getName()=="Player")return;
        if(event.getPositionWorldOnA().distance(this.app.getPlayerManager().player_FP.characterControl.getPhysicsLocation())<8)return;
        //System.out.println(event.getNodeA().getName()+" collision impulse with "+event.getNodeB().getName()+" is "+event.getAppliedImpulse());
        //System.out.println("Player position is "+this.app.getPlayerManager().player_FP.characterControl.getPhysicsLocation());
        if (event.getObjectA() == this || event.getObjectB() == this){
            if(event.getAppliedImpulse()>50){
               playThonk(event.getPositionWorldOnA(),event.getAppliedImpulse());
                //playThonk(new Vector3f(4f, 3f, 20f),100f);
            }
           
        }
        
    }

    public void prePhysicsTick(PhysicsSpace ps, float f) {
        
    }

    public void physicsTick(PhysicsSpace ps, float f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void playThonk(Vector3f location,float vol){
        thonk.setPositional(true);
        thonk.setDirectional(true);
        thonk.setLocalTranslation(location);
        thonk.setMaxDistance(200);
        thonk.setVolume(vol);
        thonk.setRefDistance(0.01f);
        thonk.playInstance();
    }
    
   
} 
