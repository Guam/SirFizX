package fizxFP;

import fizxFP.managers.controls.TargetControl;
import fizxFP.managers.controls.GhostControl;
import fizxFP.managers.PlayerManager;
import fizxFP.managers.LightManager;
import fizxFP.managers.WeaponsManager;
import fizxFP.managers.SignManager;
import fizxFP.managers.MysqlManager;
import fizxFP.managers.TerrainManager;
import fizxFP.managers.BldgManager;
import fizxFP.managers.GUIManager;
import fizxFP.managers.TargetManager;
import fizxFP.managers.EffectsManager;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.font.BitmapFont;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * FizX Base Application
 * 
 * Sets up physics based 3d world with first person player
 * control.
 * 
 * Holds references to manager classes which control specific
 * categorical interactions such as adding buildings through
 * the BldgManager etc.
 * 
 * @author SirFizX
 */
public class FizXBaseApp extends SimpleApplication implements ActionListener{
    
    private PlayerManager playerManager;
    private BldgManager bldgManager;
    private SignManager signManager;
    private TerrainManager terrainManager;
    private LightManager lightManager;
    public GUIManager guiManager;
    private WeaponsManager weaponsManager;
    private TargetManager targetManager;
    private EffectsManager effectsManager;
    private MysqlManager mysqlManager;
    private Vector3f playerStartPosition = new Vector3f(0f,5,0f);
    private float jumpSpeed = 20f;
    public float camMoveSpeed = 100f;
    private Vector3f walkDirection = new Vector3f();
    public boolean left = false, right = false, up = false, down = false;
    public boolean begin = false;
    public int score = 0;
    //public constants to be assigned to visual 3d elements to describe
    //their destructability
    public static final int DESTROY_WITH_ALL_WEAPONS = 1;
    public static final int DESTROY_WITH_HEAVY_WEAPONS = 2;
    public static final int DESTROY_ONLY_WITH_BOMBS = 3;
    public static final int INDESTRUCTABLE = 4;
    
    public static final int DISTRIBUTION_HORIZ_GRID = 1;
    public static final int DISTRIBUTION_VERT_GRID = 2;
    public static final int DISTRIBUTION_HORIZ_SPIRAL = 3;
    public static final int DISTRIBUTION_VERT_SPIRAL = 4;
    
    public static final int ANIM_ROTATE_Y = 1;
    public static final int ANIM_SINSCALE = 2;
    public static final int ANIM_YOYO_X = 3;
    public static final int ANIM_DROP = 4;
    
    // ADD OTHER TARGET TYPES AND REWRITE TARGET RELATED CODE
    public  static final int TARGET_TYPE_PLAIN = 1;
    public  static final int TARGET_TYPE_DROPTARGET = 2;
    public  static final int TARGET_TYPE_GHOST = 3;
    
    public static float GLOBAL_SCALE = 4.0f;
    /**
     * 
     */
    public static ColorRGBA Color_DUSK = new ColorRGBA(0.2f,0.18f,0.23f,1);
    /**
     * 
     */
    public static ColorRGBA Color_BLUE_SKY = new ColorRGBA(0.6f,0.8f,3.6f,1);
    
     public static ColorRGBA Color_WHITE = new ColorRGBA(1f,1f,1f,1);
    /**
     * 
     */
    public BulletAppState bulletAppState;

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        FizXBaseApp app = new FizXBaseApp();
        app.start();
    }

    /**
     * 
     */
    @Override
    public void simpleInitApp() {
        //don't show stats
        setDisplayFps(false);
        setDisplayStatView(false);
        
        this.bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
   
        this.playerManager = new PlayerManager(this);
        
        this.lightManager = new LightManager(this);
        this.lightManager.addAmbientLight("ambientLight", ColorRGBA.White);
        
        this.guiManager = new GUIManager(this);
        stateManager.attach(guiManager);
        
        this.weaponsManager = new WeaponsManager(this);
        
        this.targetManager = new TargetManager(this);
        
        this.mysqlManager = new MysqlManager();
        
        this.effectsManager = new EffectsManager(this);
         
        // We re-use the flyby camera for rotation, while positioning is handled by physics
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        flyCam.setMoveSpeed(camMoveSpeed);
        flyCam.setRotationSpeed(5);
        flyCam.setEnabled(false);
        this.cam.setFrustumFar(2000f);
        setUpKeys();
        //setUpPlayer();  //recently removed as I encapsulate player stuff via player manager
        addStartScreen();
        pauseGame();
    }

    /**
     * 
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        //For player movement and cam follow
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        camDir.y*=0.3f;
        camDir.normalize();
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left)  { walkDirection.addLocal(camLeft); }
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        if (up)    { walkDirection.addLocal(camDir); }
        if (down)  { walkDirection.addLocal(camDir.negate()); }
        playerManager.player_FP.characterControl.setWalkDirection(walkDirection);
        cam.setLocation(playerManager.player_FP.characterControl.getPhysicsLocation());  
        
        //for 3d audio
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
        
        //Calls relevant updates for the various types of targets
        for(Spatial s:targetManager.targets){
            if(s.getUserData("Animation")!=null){
                switch((int)s.getUserData("Target_Type")){
                    case TARGET_TYPE_GHOST:
                        s.getControl(GhostControl.class).controlUpdate(tpf);
                        break;
                    case TARGET_TYPE_DROPTARGET:
                        s.getControl(TargetControl.class).controlUpdate(tpf);
                        break;
                    case TARGET_TYPE_PLAIN:
                        s.getControl(TargetControl.class).controlUpdate(tpf);
                }
               
            }
            
            
        } 
    }// END OF SIMPLE UPDATE

    /**
     * 
     * @param rm
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /**
     * Make sure you have a .j3o building model in the Models/Buidings/(buildingName)/
     * folder.
     * @param buildingName
     * @param location
     */
    public void addBuiding(String buildingName, Vector3f location){
       if(this.bldgManager==null){
          this.bldgManager = new BldgManager(this); 
       } 
       
       this.bldgManager.addBldg(buildingName, location);  
    }
    
     public void addBuiding(String buildingName, Vector3f location, int DESTRUCTIBILITY){
       if(this.bldgManager==null){
          this.bldgManager = new BldgManager(this); 
       } 
       
       this.bldgManager.addBldg(buildingName, location, DESTRUCTIBILITY);  
    }
     
     public void addSign(String name, Vector3f position, String text){
          if(this.signManager==null){
          this.signManager = new SignManager(this); 
       } 
         this.signManager.addSign(name, position,text);
     }
     
      public void addSign(String name, Vector3f position, String text, int DESTRUCTIBILITY, int POINTS){
          if(this.signManager==null){
          this.signManager = new SignManager(this); 
       } 
         this.signManager.addSign(name, position,text, DESTRUCTIBILITY, POINTS);
     }
     
     public void addDynamicBoxTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE,String hitsound){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addDynamicBoxTarget(targetName, location, DESTRUCTIBILITY, POINT_VALUE,hitsound);
     }
     
     //This one is indestructible by default
     public void addDynamicBoxTarget(String targetName, Vector3f location,String hitsound,float mass){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addDynamicBoxTarget(targetName, location, hitsound,mass);
     }
     
      public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE, int ANIMATION){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addTarget(targetName, location, DESTRUCTIBILITY, POINT_VALUE, ANIMATION);
     }
      
      public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addTarget(targetName, location, DESTRUCTIBILITY, POINT_VALUE);
     }
      
      public void addDropTarget(String targetName, Vector3f location){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addDropTarget(targetName, location);
     }
     
     public void addMultipleTargets(String targetName, Vector3f firstPosition, int DISTRIBUTION, int distributionSize, int DESTRUCTIBILITY, int POINT_VALUE){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addMultipleTargets(targetName, firstPosition, DISTRIBUTION, distributionSize, DESTRUCTIBILITY,POINT_VALUE);
     }
     
     public void addGhost(String targetName, Vector3f location,String hitsound,int animation){
         if(this.targetManager==null){
             this.targetManager= new TargetManager(this);
         }
         this.targetManager.addGhost(targetName, location, hitsound,animation);
     }
     
      public void addFireEffect(Vector3f location){
       if(this.effectsManager==null){
             this.effectsManager = new EffectsManager(this);
         }
       this.effectsManager.AddFireEffect(location);
     }
    
      /**
     * This method adds a background sound that sounds the same way
     * everywhere in your scene. The sound file should be in the
     * Sounds/Ambient folder.
     * @param soundFile
     */
      public void addAmbientSound(String soundFile){
       if(this.effectsManager==null){
          this.effectsManager = new EffectsManager(this); 
       } 
       
       this.effectsManager.AddAmbientSound(soundFile);  
    }
      /**
     * This method adds a background a 3D sound in your scene. 
     * The sound file should be in the
     * Sounds/Ambient folder.
     * @param soundFile
     * @param location
     */
      public void addAmbientSound(String soundFile, Vector3f location){
       if(this.effectsManager==null){
          this.effectsManager = new EffectsManager(this); 
       } 
       
       this.effectsManager.AddAmbientSound(soundFile,location);  
    }
     
    
    /**
     * This method adds a premade terrain to your scene. The terrain .j3o
     * file should be located in the Models/Terrains/ folder.
     * @param terrainName
     * @param location
     */
    public void addTerrain(String terrainName, Vector3f location){
       if(this.terrainManager==null){
          this.terrainManager = new TerrainManager(this); 
       } 
       
       this.terrainManager.addTerrain(terrainName, location);  
    }
    
    /**
     * 
     * @param lightName
     * @param location
     * @param radius
     * @param color
     */
    public void addPointLight(String lightName, Vector3f location, float radius, ColorRGBA color){
       if(this.lightManager==null){
          this.lightManager = new LightManager(this); 
       }
       this.lightManager.addPointLight(lightName, location, radius, color); 
    }
    
     public void addSpotLightObject(String lightObjectName, Vector3f location, float radius, ColorRGBA color,Vector3f lightOffset){
       if(this.lightManager==null){
          this.lightManager = new LightManager(this); 
       }
       this.lightManager.addSpotLightObject(lightObjectName, location, radius, color,lightOffset); 
    }
    
    /**
     * 
     */
    public void removeAmbientLight(){
        this.lightManager.removeAmbientLight();
    }
    
    /**
     * 
     * @param intensity
     */
    public void setAmbientLightIntensity(float intensity){
        this.lightManager.setAmbientLightIntensity(intensity);
    }
    
    /**
     * 
     * @param color
     */
    public void setBackgroundColor(ColorRGBA color){
        this.viewPort.setBackgroundColor(color);
    }
    private void addStartScreen(){
        if(!this.guiManager.hasStartScreen){
            this.guiManager.addStartScreen();
        }
    }
    public void enableShooting(){
        if(!this.weaponsManager.shootingEnabled){
            this.guiManager.initCrossHairs();
            this.weaponsManager.enableShooting();
        }
    }
    public void disableShooting(){
        if(this.weaponsManager.shootingEnabled){
            this.weaponsManager.disableShooting();
        }
    }
    public boolean isShooting(){
        return this.weaponsManager.shootingEnabled;
    }
    public void stopFlyCam(){
        this.flyCam.setRotationSpeed(0);
    }
    public void goFlyCam(){
        this.flyCam.setRotationSpeed(5f);
    }
    public void setGUIFont(BitmapFont bitmapFont){
        this.guiFont = bitmapFont;
    }
    public BitmapFont getGUIFont(){
        return this.guiFont;
    }
    public int getWindowWidth(){
        return this.settings.getWidth();
    }
    public int getWindowHeight(){
        return this.settings.getHeight();
    }
    
    public void pauseGame(){
         //this.getStateManager().detach(this.bulletAppState);
         this.bulletAppState.setEnabled(false);
         this.flyCam.setEnabled(false);
         this.begin = false;
         this.getInputManager().setCursorVisible(true);
         
    }
    
     public void playGame(){
        this.getInputManager().setCursorVisible(false);
        //this.getStateManager().attach(this.bulletAppState);
        this.bulletAppState.setEnabled(true);
        this.guiManager.nifty.gotoScreen("gameplay");
        if(this.isShooting()){
            
            this.guiManager.initCrossHairs();
        } else {
            //this.guiManager.nifty.gotoScreen("gameplay");
        }
        this.begin = true;
        this.flyCam.setEnabled(true);
        
    }
     
    public void addToScore(int points){
        this.score+=points;
        this.guiManager.addToScore(points);
    }
    
    public void setMuzzleVelocity(int vel){
        this.weaponsManager.setMuzzleVelocity(vel);
    }
    
    public void showCamUpAngle(){
        this.guiManager.showCamUpAngle();
    }
    
    public int getCamUp(){
        float[] a = cam.getRotation().toAngles(null);
        //System.out.println(a[1]);
        int x = (int)(a[0]*180/3.14159);
        int z = (int)(a[2]*180/3.14159);
        return -(x+z);
        //return -((int)this.cam.getUp().multLocal(90f).y-90);
        //return (int)this.cam.getDirection().y;
    }
     
    public PhysicsSpace getPhysicsSpace(){
        return this.bulletAppState.getPhysicsSpace();
    }
    
    public WeaponsManager getWeaponsManager(){
        return this.weaponsManager;
    }
    
    public SignManager getSignManager(){
        return this.signManager;
    }
    
    public PlayerManager getPlayerManager(){
        return this.playerManager;
    }
    
    public TargetManager getTargetManager(){
        return this.targetManager;
    }
    
    public void addSky(String skyname) {
  
        Texture west = assetManager.loadTexture("Textures/Sky/"+skyname+"/west.jpg");
        Texture east = assetManager.loadTexture("Textures/Sky/"+skyname+"/east.jpg");
        Texture north = assetManager.loadTexture("Textures/Sky/"+skyname+"/north.jpg");
        Texture south = assetManager.loadTexture("Textures/Sky/"+skyname+"/south.jpg");
        Texture up = assetManager.loadTexture("Textures/Sky/"+skyname+"/up.jpg");
        Texture down = assetManager.loadTexture("Textures/Sky/"+skyname+"/down.jpg");

        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);
  
  }
    
    /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setUpKeys() {
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        inputManager.addMapping("Left", new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(keyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(keyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(keyInput.KEY_SPACE));
        inputManager.addMapping("Menu", new KeyTrigger(keyInput.KEY_ESCAPE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Menu");
  }
  
   /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed.
   * @param binding
   * @param value  
   */
  @Override
  public void onAction(String binding, boolean value, float tpf) {
        switch (binding) {
            case "Left":
                if (value) { left = true; } else { left = false; }
                break;
            case "Right":
                if (value) { right = true; } else { right = false; }
                break;
            case "Up":
                if (value) { up = true; } else { up = false; }
                break;
            case "Down":
                if (value) { down = true; } else { down = false; }
                break;
            case "Jump":
                this.playerManager.player_FP.characterControl.jump();
                break;
            case "Menu":
                this.guiManager.nifty.gotoScreen("screenTwo");
                this.pauseGame();
                break;
        }
  }
  
    public String getMysqlConStatus(){
            try {
                return mysqlManager.GetConStatus();
            } catch (IOException ex) {
                Logger.getLogger(FizXBaseApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    
    
}
