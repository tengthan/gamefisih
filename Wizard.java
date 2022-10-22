package gremlins;


import processing.core.PImage;

public class Wizard {
    
    public Vector2 location = new Vector2(20, 20);
    public Vector2 direction = Vector2.ZERO;
    public String movement = "up";
    public boolean wizardFire = false;

    private int speed = 2;
    private int checkMargin = 19;

    private String[][] mapPrint;

    public Wizard(String mapPrint[][]) {
        this.mapPrint = mapPrint;
    }

    public void left() {
        this.movement="left";
        this.direction = Vector2.LEFT;
    }

    public void right() {
        this.movement="right";
        this.direction = Vector2.RIGHT;
    }

    public void up() {
        this.movement="up";
        this.direction = Vector2.UP;
    }

    public void down() {
        this.movement="down";
        this.direction = Vector2.DOWN;
    }

    public void shoot(App app){
        FireBall fireBall= new FireBall(app,location.x, location.y, this.mapPrint, this.movement);
        app.fireBalls.add(fireBall);
    }

    public boolean collision(Vector2[] checkPoints) {
        for (Vector2 v : checkPoints) {
            Vector2 new_loc = v.getAdded(this.direction);
            int x = (int) (new_loc.x / 20);
            int y = (int) (new_loc.y / 20);
            if ((mapPrint[y][x] == "X" || mapPrint[y][x] == "B")) {
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