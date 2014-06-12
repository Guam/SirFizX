/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.List;
import com.jme3.scene.LightNode;
import com.jme3.scene.Spatial;
import fizxFP.FizXBaseApp;
import fizxFP.managers.controls.FizXLightControl;
import java.util.ArrayList;


/*
 * 
 */
public class LightManager extends Node {
  
    private FizXBaseApp app;
    private FizXLightControl lightControl;
    private List<PointLight> pointLights = new ArrayList<PointLight>();
    private List<SpotLight> lampLights = new ArrayList<SpotLight>();
    private List<Spatial> lamps = new ArrayList<Spatial>();
    private AmbientLight ambientLight;
    private RigidBodyControl lampPhys;
    
    /**
     * Simple constructor 
     * @param app
     */
    public LightManager(FizXBaseApp app){
        
        this.app = app;
        this.lightControl = new FizXLightControl(this);
        this.lightControl.setEnabled(true);
        this.addControl(this.lightControl);
    }
    
   
    
    public void addPointLight(String lightName, Vector3f location, float radius, ColorRGBA color ){
        PointLight l = new PointLight();
        l.setPosition(location);
        l.setRadius(radius);
        l.setColor(color);
        this.pointLights.add(l);
        this.app.getRootNode().addLight(l);
        System.out.println("point light added");
    }
    
    public void addAmbientLight(String lightName, ColorRGBA color ){
        AmbientLight l = new AmbientLight();
        l.setColor(color);
        this.ambientLight = l;
        this.app.getRootNode().addLight(l);
    }
    
    public void setAmbientLightIntensity(float intensity){
        this.ambientLight.setColor(new ColorRGBA(intensity,intensity,intensity,1));
    }
    
    public void removeAmbientLight(){
        this.app.getRootNode().removeLight(ambientLight);
    }
    
    public void addSpotLightObject(String lightObjectName, Vector3f location, float radius, ColorRGBA color,Vector3f lightOffset){
        Spatial lightObject = this.app.getAssetManager().loadModel("Models/Lights/"+lightObjectName+"/"+lightObjectName+".j3o");
        lightObject.setName(lightObjectName);
        lightObject.setUserData("Destructibility", 4);
        lightObject.move(location);
        addMeshLight(lightObject);
        
        SpotLight l = new SpotLight();
        l.setPosition(location.add(lightOffset));
        l.setSpotInnerAngle(.01f);
        l.setSpotOuterAngle(4f);
        l.setDirection(new Vector3f(0f,-1f,0f));
        l.setSpotRange(100);
        l.setColor(color);
        this.lampLights.add(l);
        this.app.getRootNode().addLight(l);
        
    }
    
    private void addMeshLight(Spatial m) {
        
        this.lamps.add(m);
        com.jme3.bullet.collision.shapes.CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) m);
        lampPhys = new RigidBodyControl(sceneShape, 0);
        m.addControl(lampPhys);


        this.app.getRootNode().attachChild(m);
        this.app.bulletAppState.getPhysicsSpace().add(m);

  }
    
    
}
