package DragableObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class DrawObject implements Draggable {
    protected BufferedImage image;
    protected Point position;
    protected BufferedImage renderImage;

    DrawObject(String file_path) {
        image = load_png_from_file(file_path);
        renderImage = null;
    }

    protected BufferedImage getSubImage(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

    private BufferedImage load_png_from_file(String file_path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/" + file_path)));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void setRenderImage(int x, int y) {
        renderImage = getSubImage(x, y, 32, 32);
    }

    void setPosition(Point position) { this.position = position; }

    public abstract String getStringInfo();

    public abstract void draw(Graphics g);
    public abstract  DrawObject getCopy();
    public abstract  boolean isInBounds(Point position);
}
