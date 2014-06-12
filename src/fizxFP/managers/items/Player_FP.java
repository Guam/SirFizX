/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers.items;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fizxFP.managers.controls.Player_FPControl;

/**
 *
 * @author Computer
 */
public class Player_FP extends Node {
    
    private fizxFP.FizXBaseApp app;
    private fizxFP.managers.controls.Player_FPControl playerFPControl;
    public CharacterControl characterControl;
    private float jumpSpeed=8f;
    private Vector3f playerStartPosition = new Vector3f(45f, 5f, -36f);
    private Vector3f worldUp = new Vector3f(0f, 1f, 0f);
    
    
    public Player_FP(fizxFP.FizXBaseApp app){
        this.app = app;
        setUpPlayer(); 
    }
    
    private void setUpPlayer(){
        // We set up collision detection for the player by creating
        // a capsule collision shape and a CharacterControl.
        // The CharacterControl offers extra settings for
        // size, stepheight, jumping, falling, and gravity.
        // We also put the player in its starting position.
        this.setName("Player");
        this.playerFPControl = new Player_FPControl(this.app);
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.3f, 5f, 1);
        this.characterControl = new CharacterControl(capsuleShape, 0.05f);
        this.characterControl.setSpatial(this);
        this.characterControl.setJumpSpeed(jumpSpeed);
        this.characterControl.setFallSpeed(30);
        this.characterControl.setGravity(10);
        this.characterControl.setPhysicsLocation(playerStartPosition);
        //this.app.getCamera().lookAtDirection(new Vector3f(-1f, 0.2f, -0.3f), worldUp);
        //this.app.getCamera().lookAtDirection(new Vector3f(-130f, 6f, -50f), new Vector3f(0f, 1f, 0f));
        //this.addControl(playerFPControl);
        //this.addControl(characterControl);
        this.app.getPhysicsSpace().add(this.characterControl);
        //bulletAppState.startPhysics();
        
        
    }
    
    public void lookAt(Vector3f targetPosition){
        Vector3f targetDir = playerStartPosition.subtract(targetPosition).negate();
        //new Vector3f(-1f, 0.2f, -0.3f)
        this.app.getCamera().lookAtDirection(targetDir, worldUp);
    }
    
    
}
