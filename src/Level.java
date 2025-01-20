import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Level {
    public enum Tile_Type {
        Empty,
        Wall
    }

    private Point tile_position;
    private BufferedImage tile_image;
    private Tile_Type type;
    private static final int TILE_SIZE = 32;
    private static final String WALL_IMAGE_PATH = "fantasy_tiles.png";

    public Level(Tile_Type type, Point position) {
        this.type = type;
        tile_position = position;
        tile_image = (type == Tile_Type.Wall) ? load_png_from_file(WALL_IMAGE_PATH) : null;
        tile_image = (tile_image != null) ? tile_image.getSubimage(120, 128, 16, 16) : null;
    }

    public Level(Tile_Type type, Point position,
                 Rect rect) {
        this.tile_position = position;
        tile_image = (type == Tile_Type.Wall) ? load_png_from_file(WALL_IMAGE_PATH) : null;
        tile_image = (tile_image != null) ? tile_image.getSubimage(rect.x, rect.y, rect.w, rect.h) : null;
    }

    private BufferedImage load_png_from_file(String file_path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/" + file_path)));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setTilePosition(Point position) {
        this.tile_position = position;
    }

    public void drawLevel(Graphics g) {
        if (tile_image != null) {
            g.drawImage(tile_image,
                    tile_position.x * TILE_SIZE,
                    tile_position.y * TILE_SIZE,
                    TILE_SIZE, TILE_SIZE,null);
        }
    }



}
