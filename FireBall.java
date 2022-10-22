package gremlins;

import processing.core.PImage;

public class FireBall {
    private int speed = 4;
    public boolean collisionChecker;
    public Vector2 direction;
    public Vector2 location;
    public boolean fire = false;
    private int checkMargin =19;
    private String[][] mapPrint;
    
    public FireBall(App app,Double x, double y, String mapPrint[][], String movement){
        location = new Vector2(x,y);
        this.mapPrint= mapPrint;
        collisionChecker= true;
        movement(movement);
        collisionGremelins(app);
    }

    public void movement(String movement){
        if (movement == "up"){
            this.direction = Vector2.UP;
        }else if(movement == "down" ){
            this.direction = Vector2.DOWN;
        }else if(movement == "left" ){
            this.direction = Vector2.LEFT;
        }else if(movement == "right" ){
            this.direction = Vector2.RIGHT;
        }
    }

    public boolean collision(Vector2[] checkPoints, App app) {
        for (Vector2 v : checkPoints) {
            Vector2 new_loc = v.getAdded(this.direction);
            int x = (int) (new_loc.x / 20);
            int y = (int) (new_loc.y / 20);
            for (Gremlins s: app.mapdisplay.gremlinsArray){
                int xGrem= (int) (s.location.x / 20);
                int yGrem= (int) (s.location.y / 20);
                if (x == xGrem && y == yGrem){
                    s.respawn(app.wizard);
                    collisionChecker= false;
                    return false;
                }
            if ((mapPrint[y][x] == "X")) {
                collisionChecker= false;
                return false;
            }
            else if(mapPrint[y][x] == "B"){
                mapPrint[y][x]= "S";
                collisionChecker= false;
                return false;
            }
            
        }
        collisionChecker= true;
        return true;
    }
    public void collisionGremelins(App app){
        for(Gremlins s: app.mapdisplay.gremlinsArray){
            if(location.x == s.location.x && location.y == s.location.y){
                s= new Gremlins(20, 20, mapPrint,app);
            }
        }
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
            if (collision(checkPoints,app)) {
                location = location.getAdded(direction.getMultiplied(speed));
            }
        
        app.image(image, (int) location.x, (int) location.y);
    }

}