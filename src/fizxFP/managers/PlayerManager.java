/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import fizxFP.managers.items.Player_FP;

/**
 *
 * @author Computer
 */
public class PlayerManager {
    
    private CharacterControl player;
    private BulletAppState bulletAppState;
    private fizxFP.FizXBaseApp app;
    private float jumpSpeed = 20f;
    private Vector3f playerStartPosition = Vector3f.ZERO;
    public Player_FP player_FP;
    
    public PlayerManager(fizxFP.FizXBaseApp app){
        this.app = app;
        createPlayer_FP();
    }
    
    private void createPlayer_FP(){
        this.player_FP = new Player_FP(app);
        this.app.getRootNode().attachChild(player_FP);
    }
    
    
    
}
