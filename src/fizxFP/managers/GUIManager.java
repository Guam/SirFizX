/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fizxFP.managers;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import fizxFP.FizXBaseApp;


/**
 * <code>GUIManager</code> extends the {@link com.jme3.app.state.AbstractAppState}
 * class to provide an easy means to organize and control interactions with
 * the graphical user interface.
 */
public class GUIManager extends AbstractAppState implements ScreenController{
  
    private FizXBaseApp app;
    public Nifty nifty;
    private BitmapText ch;
    public boolean hasStartScreen = false;
    private boolean camAngleShowing = false;
    /**
     * Simple constructor.
     * @param app
     */
 public GUIManager(FizXBaseApp app){
        
         this.app = app;
        
  }
 public void addStartScreen(){
       initNifty();
 }
  public void changeTitleScreen(String title, String logoName) {
      NiftyImage newImage = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(),"Textures/gui/"+logoName+".png", false);
      // code below not working
      //this.nifty.getScreen("start").findElementByName("startLayer").findElementByName("logo").getRenderer(ImageRenderer.class).setImage(newImage);
  }
  public void changeTitleScreen(String title) {

  }
  
  private void initNifty(){
      //Nifty GUI Stuff
    NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay( this.app.getAssetManager(),
                                                        this.app.getInputManager(),
                                                        this.app.getAudioRenderer(),
                                                        this.app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/ghs.xml", "start", this);
   
        // attach the nifty display to the gui view port as a processor
        this.app.getGuiViewPort().addProcessor(niftyDisplay);
        // disable the fly cam
        //flyCam.setEnabled(false);
        //flyCam.setDragToRotate(true);
        this.app.getInputManager().setCursorVisible(true);
        this.hasStartScreen = true;
  } 
   
 
  //Nifty Scene Controller Methods Required
   public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        
        System.out.println("onStartScreen");
        //ch is the crosshairs overlay
        if(ch!=null){
        this.app.getGuiNode().detachChild(ch);
        }
        
       
    }

    
    public void onEndScreen() {
        System.out.println("onEndScreen");
       
        
    }
    
    public void gotoScreenTwo() {
    
    nifty.gotoScreen("screenTwo");
    
    }
    
    public void exit(){
    
    this.app.getAudioRenderer().cleanup();
    this.app.stop();
    System.exit(0);
    
    }
    
    public void pauseGame(){
         
         this.app.pauseGame();
    }

    public void playGame(){
        
        this.app.playGame();  
    }
    
 
    public void initCrossHairs() {
//        this.app.setGUIFont(this.app.getAssetManager().loadFont("Fonts/aurulent-sans-16.fnt"));
//        ch = new BitmapText(this.app.getGUIFont(), false);
//        ch.setSize(this.app.getGUIFont().getCharSet().getRenderedSize() * 2);
//        ch.setText("+"); // crosshairs
//        ch.setLocalTranslation( // center
//                this.app.getWindowWidth() / 2 - this.app.getGUIFont().getCharSet().getRenderedSize() / 3 * 2,
//                this.app.getWindowHeight() / 2 + ch.getLineHeight() / 2, 0);
//        this.app.getGuiNode().attachChild(ch);
        nifty.getScreen("gameplay").getLayerElements().get(0).setVisible(true);
        System.out.println("cross hairs initialized");
        System.out.println(nifty.getCurrentScreen().getScreenId());
        
    }
    
    public void showCamUpAngle() {

        nifty.getScreen("gameplay").getLayerElements().get(2).setVisible(true);
        System.out.println("set cam angle hud visible");
        System.out.println(nifty.getCurrentScreen().getScreenId());
        this.camAngleShowing = true;
    }
    
    public void updateUpCamAngle(){
        int angle = this.app.getCamUp();
        this.nifty.getScreen("gameplay")
                .findElementByName("camUpAngleLayer")
                .findElementByName("camUpAngleText")
                .getRenderer(TextRenderer.class)
                .setText("Cam Angle: "+angle+" degrees   ");
    }
    
    public void addToScore(int points){
        
        this.nifty.getScreen("gameplay")
                .findElementByName("score")
                .findElementByName("scoretext")
                .getRenderer(TextRenderer.class)
                .setText("     "+this.app.score+" points");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
       super.initialize(stateManager, app);
       this.app = (FizXBaseApp)app;
    }
    
    
    
    @Override
    public void update(float tpf){
        /* JME update Loop! */
        if(this.camAngleShowing)updateUpCamAngle();
       // System.out.println("hello from gui manager");
    }
    
    
}
