package Examples;

/*
 * This is an early example application built with the fizxFP library.
 * Please note that use of the fizxFP library requires an expected
 * structure within the Project Assets.
 */
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import fizxFP.*;

/**
 *
 * @author SirFizX
 */
public class Example extends FizXBaseApp {
    
     /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        Example app = new Example();
        app.start();
    }
    
     /**
     * 
     */
    @Override
    public void simpleInitApp() {
       // Set custom FP Player variables here.
       
       super.simpleInitApp();
       //Add your initialization code here. Use type "this." to see 
       //some convenient methods
       this.guiManager.changeTitleScreen("Edward Little Rocks","GBE");
       this.getPlayerManager().player_FP.lookAt(new Vector3f(0f, 20f, -53f));
       this.addAmbientSound("chill.wav");
       this.addAmbientSound("fire.wav", new Vector3f(17,13f,-47));
       this.addBuiding("woodHouse", new Vector3f(0f,0f,-50f));
       this.addGhost("ghost1",new Vector3f(-20f,10f,-30f),"ghostKill.wav",ANIM_YOYO_X);
       this.addTarget("oldFence", new Vector3f(28.5f,0f,-53.1f), INDESTRUCTABLE, 0);
       this.addTarget("oldFence", new Vector3f(-30f,0f,-50f), INDESTRUCTABLE, 0); 
       this.addDynamicBoxTarget("barrel",new Vector3f(30f,1.5f,-51f),"woodBarrelHit.wav",20f);
       this.addDynamicBoxTarget("barrel",new Vector3f(31f,4.5f,-51f),"woodBarrelHit.wav",20f);
       this.addDynamicBoxTarget("barrel",new Vector3f(32f,1.5f,-51f),"woodBarrelHit.wav",20f);
       //this.addMultipleTargets("barrel",new Vector3f(-20f,10f,20f),DISTRIBUTION_VERT_GRID,4, DESTROY_WITH_ALL_WEAPONS,100);
       //this.addDynamicMeshTarget("spiky",new Vector3f(10f,10f,-10f),INDESTRUCTABLE,500,"wood-step.wav");
       //this.addTarget("gasStationSign",new Vector3f(-9f,0f,-55f),INDESTRUCTABLE,500,ANIM_ROTATE_Y);
       this.addTarget("oldSign", new Vector3f(-16f,0f,-45f),INDESTRUCTABLE,0);
       //this.addSign("Artist", new Vector3f(46,0,-49),this.getMysqlConStatus()+" is a name\nread from a MySql db.",DESTROY_WITH_ALL_WEAPONS, 500);
       this.addTerrain("flatDirt", Vector3f.ZERO);
       this.addSpotLightObject("candleStreetLight", new Vector3f(-32f,0f,-45f), 30f, Color_WHITE,new Vector3f(0f, 9f, 0f));
       this.addSpotLightObject("candleStreetLight", new Vector3f(32f,0f,-45f), 30f, Color_WHITE,new Vector3f(0f, 9f, 0f));
       this.setAmbientLightIntensity(0.7f);
       //this.addDynamicMeshTarget("pallet",new Vector3f(35f,8f, -53f),"wood-step.wav");
       this.enableShooting();
       this.setMuzzleVelocity(50);
       this.showCamUpAngle();
       this.addSky("Lagoon");
       this.addFireEffect(new Vector3f(10,17f,-50));
       this.addFireEffect(new Vector3f(11,17f,-50));
       this.addFireEffect(new Vector3f(14,15f,-47));
       this.addFireEffect(new Vector3f(15,15f,-47));
       this.addFireEffect(new Vector3f(17,13f,-44));
       this.addFireEffect(new Vector3f(18,13f,-44));
       this.addFireEffect(new Vector3f(27,7.5f,-53));
       this.addFireEffect(new Vector3f(28,7.5f,-53));
       
    }
    
     @Override
    public void simpleRender(RenderManager rm) {
         super.simpleRender(rm);
        //TODO: add render code
    }
    
    /**
     * 
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        //TODO: add update code
    }
    
}

 
