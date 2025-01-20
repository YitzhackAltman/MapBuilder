package DragableObjects;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Wall extends DrawObject {
    private static final int WALL_SIZE = 32;

    private static final String WALL_PATH = "fantasy_tiles.png";
    private final BufferedImage renderImage;
    private final int x, y;
    private final int col;
    // 120 128


    Wall(int x, int y, int col) {
        super(WALL_PATH);
        this.col = col;
        this.x = x;
        this.y = y;
        // this.position = new Point(7, row);
        renderImage = getSubImage(x, y, WALL_SIZE, WALL_SIZE);
        this.position = new Point(col, 15);
    }



    @Override
    public void drag(Point position) {
        this.position.x = position.x / WALL_SIZE; // col
        this.position.y = position.y / WALL_SIZE; // row
    }

    @Override
    public String getStringInfo() {
        return " pos_x = " + position.x + ", pos_y = " + position.y +
                ", x = " + this.x + ", y = " + this.y + ", col = " + col + "\n";
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(renderImage,
                this.position.x * WALL_SIZE, //this.position.x * WALL_SIZE,
                this.position.y * WALL_SIZE,
                WALL_SIZE, WALL_SIZE, null);
    }


    public DrawObject getCopy() {
        return new Wall(x, y, col);
    }


    /*private Point getBounds() {
        return new Point(this.position.x + WALL_SIZE, this.position.y + WALL_SIZE);
    }*/

    @Override
    public boolean isInBounds(Point position) {
        int x = position.x / WALL_SIZE;
        int y = position.y / WALL_SIZE;

        return x >= this.position.x &&
                x <= this.position.x + 1 &&
                y >= this.position.y &&
                y <= this.position.y + 1;
    }

}
