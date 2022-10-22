package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import processing.event.KeyEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;

    public PImage brickwall;
    public PImage stonewall;
    public PImage fireball;
    public PImage gremlin;
    public PImage slime;
    public PImage up, down, left, right;

    public MapDisplay mapdisplay = new MapDisplay(this);

    public Wizard wizard = new Wizard(mapdisplay.mapPrint);

    public ArrayList<FireBall> fireBalls= new ArrayList<>();
    public ArrayList<SlimeGremlins> slimes= new ArrayList<>();

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the
     * player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.up = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.down = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.left = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.right = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime =loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball =loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20"," "));

        JSONObject conf = loadJSONObject(new File(this.configPath));

    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 37) { // left arrow
            wizard.left();
        } else if (key == 39) { // right arrow
            wizard.right();
        } else if (key == 38) { // right arrow
            wizard.up();
        } else if (key == 40) { // right arrow
            wizard.down();
        } if( key == 32){
            wizard.shoot(this);
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 37 ||key == 39 || key == 38 || key == 40 ) { // left arrow
            wizard.direction = Vector2.ZERO;
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
    
    public void direction() {
        if (wizard.movement == "up")
            wizard.draw(this, this.up);
        else if (wizard.movement == "down")
            wizard.draw(this, this.down);
        else if (wizard.movement == "left")
            wizard.draw(this, this.left);
        else if (wizard.movement == "right")
            wizard.draw(this, this.right);
    }

    public void draw() {
        background(200);
        mapdisplay.draw(this, this.brickwall, this.stonewall);
        direction();
        for(Gremlins g: mapdisplay.gremlinsArray ){
            g.shootSlime(this);
            g.draw(this,this.gremlin);
        }
        for(SlimeGremlins s: this.slimes){
            if(s.collisionChecker == true){
            s.draw(this, this.slime);
            }
        }
 
        for(FireBall f: this.fireBalls){
            if(f.collisionChecker == true){ 
                f.draw(this, this.fireball);
            }
        }
       
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}