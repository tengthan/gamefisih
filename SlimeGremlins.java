package gremlins;


import processing.core.PImage;

public class SlimeGremlins{
    private int speed = 2;
    public boolean collisionChecker;
    public Vector2 direction;
    public Vector2 location;
    public double x;
    public double y;
    public boolean checkPixel = true;
    private int checkMargin =19;
    private String[][] mapPrint;

    public SlimeGremlins(App app, double x, double y, String mapPrint[][], String movement){
        location = new Vector2(x,y);
        this.mapPrint= mapPrint;
        collisionChecker= true;
        movement(movement);
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
    
    public boolean collision(Vector2[] checkPoints) {
        for (Vector2 v : checkPoints) {
            Vector2 new_loc = v.getAdded(this.direction);
            int x = (int) (new_loc.x / 20);
            int y = (int) (new_loc.y / 20);
            if ((mapPrint[y][x] == "X" || mapPrint[y][x] == "B")) {
                collisionChecker= false;
                return false;
            }            
        }
        collisionChecker= true;
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
        if (checkPixel){
        app.image(image, (int) location.x, (int) location.y);
        }
        checkPixel = true;
    }


}