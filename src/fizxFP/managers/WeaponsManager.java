package fizxFP.managers;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import fizxFP.managers.controls.BombControl;
import fizxFP.FizXBaseApp;
import fizxFP.managers.controls.WeaponsControl;
import java.util.ArrayList;
import java.util.List;


/**
 * The WeaponsManager keeps track of and controls weapons and
 * ammunition.
 */
public class WeaponsManager extends Node implements ActionListener{
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private DestructiblesManager destructiblesManager;
    private RigidBodyControl weaponPhys;
    private WeaponsControl weaponsControl;
    private List<Spatial> weapons = new ArrayList<Spatial>();
    private float scale = 4f;
    private static Sphere bullet;
    private Material mat;
    private Material mat2;
    private AudioNode audio_shoot;
    public boolean shootingEnabled = false;
    private SphereCollisionShape bulletCollisionShape;
    private int muzzle_velocity = 55;
    
    /**
     * Simple constructor used when you want to use impactBomb
     * as the default weapon type.
     * @param app
     */
    public WeaponsManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.destructiblesManager = new DestructiblesManager(this.app);
        this.weaponsControl = new WeaponsControl(this);
        this.weaponsControl.setEnabled(true);
        this.addControl(this.weaponsControl);
        initMaterial();
        initAudio();
        initBullet();
    }
    
    /**
     * The weaponName parameter sets the default weapon type.
     * @param app
     * @param weaponName
     */
    public WeaponsManager(FizXBaseApp app, String weaponName){
        // run switch on weaponName
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.weaponsControl = new WeaponsControl(this);
        this.weaponsControl.setEnabled(true);
        this.addControl(this.weaponsControl); 
        switch(weaponName){
            case "impactBomb": 
                initMaterial();
                initAudio();
                initBullet();
                break;
            case "blastPistol":
                break;
        }
        
    }
    /////////////  FOR IMPACT BOMB WEAPON ////////////////////////
    public void initMaterial() {
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/BrickWall.jpg");
        key.setGenerateMips(true);
        Texture tex = assetManager.loadTexture(key);
        mat.setTexture("ColorMap", tex);

        mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/redGlassPattern.jpg");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        mat2.setTexture("ColorMap", tex2);
    }
    
    private void initBullet(){
        bullet = new Sphere(32, 32, 1.0f, true, false);
        bullet.setTextureMode(Sphere.TextureMode.Projected);
        bulletCollisionShape = new SphereCollisionShape(1.0f);
    }
    
    ///////////////////////////////////////////////////////////////
    
    private void initAudio() {
    audio_shoot = new AudioNode(assetManager,"Sounds/Effects/Bang.wav",false);
    audio_shoot.setLooping(false);
    audio_shoot.setVolume(10);
    this.app.getRootNode().attachChild(audio_shoot); 
  }
    
    public void enableShooting(){
            setUpKeys();
            this.shootingEnabled = true;
    }
    
    public void disableShooting(){
        deleteKeys();
        this.shootingEnabled = false;
    }
 
  // for weapons displayed in FP as a 3D model
  private void addWeapon(Spatial b) {
        
        this.weapons.add(b);
    }
     
  public void setMuzzleVelocity(int vel){
      this.muzzle_velocity = vel;
  }
 
  private void setUpKeys() {
    this.app.getInputManager().addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    this.app.getInputManager().addListener(this, "shoot");
    this.app.getInputManager().addMapping("gc", new KeyTrigger(KeyInput.KEY_X));
    this.app.getInputManager().addListener(this, "gc");
 
  }
  
  private void deleteKeys() {
    this.app.getInputManager().deleteMapping("shoot");
    this.app.getInputManager().deleteMapping("gc");
    this.app.getInputManager().removeListener(this);
  }
  
  
  
    
  public void onAction(String binding, boolean value, float tpf) {
            int relvel = this.muzzle_velocity;
           
            if (binding.equals("shoot") && !value && this.app.begin ) {
                        Geometry bulletg = new Geometry("bullet", bullet);
                        bulletg.setMaterial(mat2);
                        bulletg.setName("Bullet");
                        bulletg.setShadowMode(shadowMode.CastAndReceive);
                        bulletg.setLocalTranslation(this.app.getCamera().getLocation().add(this.app.getCamera().getDirection().mult(new Vector3f(3.5f,3.5f,3.5f))));
                        this.app.getRootNode().attachChild(bulletg);
                        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
                        
                        RigidBodyControl bulletNode = new BombControl(this.app,assetManager, bulletCollisionShape, 55);
        //                RigidBodyControl bulletNode = new RigidBodyControl(bulletCollisionShape, 1);
                        bulletg.addControl(bulletNode);
                        if(this.app.up&&!this.app.down)relvel+=30;
                        if(this.app.down&&!this.app.up)relvel-=30;
                        bulletNode.setLinearVelocity(this.app.getCamera().getDirection().mult(relvel));
                        
                        this.app.bulletAppState.getPhysicsSpace().add(bulletNode);
                        
                        
                        audio_shoot.playInstance();
                    }
                    if (binding.equals("gc") && !value) {
                        System.gc();
                    }
     }
     
     public DestructiblesManager getDestructiblesManager(){
         return this.destructiblesManager;
     }
    
    
}
