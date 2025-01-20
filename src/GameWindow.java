import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;
import java.util.Objects;

public class GameWindow {

    private final JFrame frame;
    private int fps;
    private boolean isRunning;
    private Color screenColor = Color.black;
    private BufferedImage renderImage;
    private BufferedImage image;
    private int index;
    private BufferedImage displayedImage;
    private Level[][] level;

    private BufferedImage load_png_from_file(String file_path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/" + file_path)));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void screenChangeColor() {
        if (screenColor.equals(Color.black)) {
            screenColor = Color.blue;
        }else {
            screenColor = Color.black;
        }
    }

    private static int amountOfCharactersInRow(int row) {
        return switch (row) {
            case 0 -> 13;
            case 1 -> 8;
            case 2, 3, 4 -> 10;
            case 5 -> 6;
            case 6 -> 4;
            case 7 -> 7;
            default -> throw new NoSuchElementException("No such elements in row " + row);
        };
    }

    public GameWindow() {

        int LEVEL_WIDTH = 5;
        int LEVEL_HEIGHT = 5;

        level = new Level[LEVEL_HEIGHT][LEVEL_WIDTH];

        for (int y = 0; y < LEVEL_HEIGHT; ++y) {
            for (int x = 0; x < LEVEL_WIDTH; ++x ) {
                Level.Tile_Type type = Level.Tile_Type.Empty;
                if (y == LEVEL_HEIGHT - 1 && x < LEVEL_WIDTH - 2) {
                    type = Level.Tile_Type.Wall;
                }
                level[y][x] = new Level(type, new Point(x, y));
            }
        }


        displayedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        index = 0;
        fps = 0;
        isRunning = false;

        image = load_png_from_file("adv.png");
        assert image != null;
        final int IMAGE_HEIGHT = image.getHeight();
        final int IMAGE_WIDTH = image.getWidth();
        final int CHAR_AMOUNT_ROW = 8;
        final int CHAR_AMOUNT_COL = 13;
        final int CHAR_HEIGHT = IMAGE_HEIGHT/CHAR_AMOUNT_ROW;
        final int CHAR_WIDTH = IMAGE_WIDTH/CHAR_AMOUNT_COL;

        // 8*CHAR_WIDTH = 8 << 5; CHAR_WIDTH = 32; 2^5 = 32
        renderImage = image.getSubimage(0 << 5, 0 << 5, CHAR_WIDTH, CHAR_HEIGHT);

        frame = new JFrame();
        frame.setTitle("Mario");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == 'q') {
                    stop();
                }else if (c == 'c') {
                    screenChangeColor();
                }
            }
        });
        frame.setVisible(true);
    }

    private void stop() {
        if (!isRunning) return;

        isRunning = false;
        System.exit(0);
    }

    public void start() {
        isRunning = true;
        final double FPS = 60.0f;
        final double ns = 1000000000 / FPS;

        long current_time;
        double delta_time = 0.0f;
        long timer = 0;
        int ticks = 0;

        long start = System.nanoTime();
        while (isRunning) {
            current_time = System.nanoTime();
            delta_time += (current_time - start) / ns;
            timer += (current_time - start);
            start = current_time;
            while (delta_time >= 1) {
                tick();
                render();
                delta_time -= 1;
                ticks += 1;

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            if (timer >= 1000000000) {
                fps = ticks;
                ticks = 0;
                timer = 0;
            }
        }
    }



    private void render() {
        frame.getContentPane().setBackground(screenColor);
        Graphics g = displayedImage.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        for (int y =0; y < level.length; ++y) {
            for (int x = 0; x < level.length; ++x) {
                level[y][x].drawLevel(g);
            }
        }

        int row = 1;
        renderImage = image.getSubimage(index << 5, row << 5, 32, 32);
        int total_amount = amountOfCharactersInRow(row);
        index = (index + 1) % total_amount;
        g.drawImage(renderImage, 100, 100, 50, 50, null);
        g.dispose();

        g = frame.getGraphics();
        g.drawImage(displayedImage, 0,0,null);
        g.dispose();

    }

    private void tick() {

    }
}
