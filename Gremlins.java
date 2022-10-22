package gremlins;

import java.util.Random;

import processing.core.PImage;

public class Gremlins{

    private int speed = 1;

    private String mapPrint[][];
    public Vector2 location;

    public Vector2 direction = Vector2.DOWN;
    public String movement = "down";
    private int checkMargin = 19;


    public Gremlins(int y, int x, String mapPrint[][], App app ) {
        this.location = new Vector2(x,y);
        this.mapPrint = mapPrint;
    }

    public void shootSlime(App app){
        Random random = new Random();
        int i = random.nextInt(10000) ;
        if(i<30){
        SlimeGremlins slime = new SlimeGremlins(app,location.x, location.y, this.mapPrint, this.movement);
        app.slimes.add(slime);
        }
    }

    public void ranDirection(){
        Random random = new Random();
        int i = random.nextInt(16) +1;
        if (i <=4){
            this.direction = Vector2.LEFT;
            this.movement= "left";
        }else if(i >4 && i <= 8){
            this.direction = Vector2.RIGHT;
            this.movement= "right";
        }else if( i > 8 && i <= 12){
            this.direction = Vector2.UP;
            this.movement= "up";
        }else{
            this.direction=Vector2.DOWN;
            this.movement= "down";
        }
    }

    public void respawn(Wizard wizard){
        Random random = new Random();
        int x = random.nextInt(32);  
        int y = random.nextInt(35);  
        while(mapPrint[x][y]!= "S"){
            x = random.nextInt(32);  
            y = random.nextInt(35);  
        }
            this.location.x = x*20 ;
            this.location.y = y*20;
    }

    public boolean collision(Vector2[] checkPoints) {
        for (Vector2 v : checkPoints) {
            Vector2 new_loc = v.getAdded(this.direction);
            int x = (int) (new_loc.x / 20);
            int y = (int) (new_loc.y / 20);
            if ((mapPrint[y][x] == "X" || mapPrint[y][x] == "B")) {
                ranDirection();
                return false;
            }            
        }
        return true;
    }
    

    
    public void draw(App app, PImage image) {
        Vector2[] checkPoints = new Vector2[2];
        if (direction == Vector2.RIGHT) {
            checkPoints[0] = location.getAdded(direction.getMultiplied(checkMargin));
            checkPoints[1] = checkPoints[0].getAdded(Vector2.DOWN.getMultiplied(checkMargin));
        } else if (direction == Vector2.LEFT) {
            checkPoints[0] = location;
            checkPoints[1] = checkPoints[0].getAdded(Vector2.DOWN.getMultiplied(checkMargin));
        } else if (direction == Vector2.UP) {
            checkPoints[0] = location;
            checkPoints[1] = checkPoints[0].getAdded(Vector2.RIGHT.getMultiplied(checkMargin));
        } else if (direction == Vector2.DOWN) {
            checkPoints[0] = location.getAdded(direction.getMultiplied(checkMargin));
            checkPoints[1] = checkPoints[0].getAdded(Vector2.RIGHT.getMultiplied(checkMargin));
        }
        if (checkPoints[0] != null && checkPoints[1] != null)
            if (collision(checkPoints)) {
                location = location.getAdded(direction.getMultiplied(speed));
            }
        app.image(image, (int) location.x, (int) location.y);
    }


}
