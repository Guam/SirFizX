package fizxFP.managers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import fizxFP.FizXBaseApp;
import fizxFP.managers.items.Ghost;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SirFizX
 */
public class GhostManager {

private FizXBaseApp app;
public List<Spatial> ghosts = new ArrayList<Spatial>();
public Spatial g;
    
    
public GhostManager(FizXBaseApp app){
        this.app = app;
  }

public void addGhost(String targetName, Vector3f location,String hitsound,int animation) {
        Spatial ghost = new Ghost(targetName,this.app,location,hitsound,animation).t;
        ghost.setName(targetName+this.ghosts.size());
        ghost.setUserData("Points", 10);
        ghost.setUserData("Destructibility", 1);
        this.app.getWeaponsManager().getDestructiblesManager().addDestructible(ghost); 
        this.ghosts.add(ghost);
        this.app.getRootNode().attachChild(ghost);
        this.g = ghost;
  }
    
}
