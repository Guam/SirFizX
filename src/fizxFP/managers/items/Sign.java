/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers.items;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import fizxFP.FizXBaseApp;

/**
 *
 * @author Computer
 */
public class Sign{
    
    private FizXBaseApp app;
    private Vector3f location;
    private String boardtext;
    
    public Sign(FizXBaseApp app, Vector3f location, String text){
        this.app = app;
        this.location = location;
        this.boardtext = text;
        initBoard();
        initText();
    }
    
    private void initBoard(){
        Quad q = new Quad(6, 3);
        Geometry g = new Geometry("quad", q);
        g.setLocalTranslation(location.x,location.y-3, location.z-0.0001f);
        g.setMaterial(this.app.getAssetManager().loadMaterial("Common/Materials/RedColor.j3m"));
        this.app.getRootNode().attachChild(g);
    }
    
    private void initText(){
        BitmapFont fnt = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, 6, 3));
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( 0.5f );
        txt.setText(boardtext);
        txt.setLocalTranslation(location);
        this.app.getRootNode().attachChild(txt);
    }
    
    
    
}
