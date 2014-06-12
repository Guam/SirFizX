package fizxFP.managers;

import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import fizxFP.FizXBaseApp;
import fizxFP.managers.items.Fire;

/**
 *
 * @author SirFizX
 */
public class EffectsManager {
    
    private FizXBaseApp app;
    
    public EffectsManager(FizXBaseApp app){
        this.app = app;
    }
    
    public void AddAmbientSound(String soundFile){
        AudioNode ambience = new AudioNode(app.getAssetManager(), "Sounds/Ambient/"+soundFile, false);
        ambience.setDirectional(false);
        ambience.setPositional(false);
        ambience.setLooping(true);
        ambience.play();
    }
    
    public void AddAmbientSound(String soundFile, Vector3f location){
        AudioNode ambience = new AudioNode(app.getAssetManager(), "Sounds/Ambient/"+soundFile, false);
        ambience.setLocalTranslation(location);
        ambience.setDirectional(true);
        ambience.setPositional(true);
        ambience.setRefDistance(0.1f);
        ambience.setMaxDistance(200f);
        ambience.setVolume(50f);
        ambience.setLooping(true);
        ambience.play();
    }
    
    public void AddFireEffect(Vector3f location){
        Fire fe = new Fire(app,location);
    }
    
    
    
}// END OF CLASS
