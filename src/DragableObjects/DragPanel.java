package DragableObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DragPanel extends JPanel {
    private final MouseListener mouseListener;
    private final List<DrawObject> drawObjects;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private boolean debug;

    private static final int TILE_SIZE = 32;

    // Draw Grid
    private static final int ROWS = SCREEN_HEIGHT/ TILE_SIZE;
    private static final int COLS = SCREEN_WIDTH/ TILE_SIZE;

    private static final int AMOUNT_OF_ROWS = 3;
    private static final int AMOUNT_OF_COLS = 2;


    public DragPanel() {
        debug = false;

       Wall wall = new Wall(32, 321, 7);
       Wall stone = new Wall(120, 128, 9);
       Wall water = new Wall(32, 288, 5);
       Wall water1 = new Wall(32+32+32, 288-32, 11);
       Wall water2 = new Wall(32+32+32, 288-32-32, 13);
       Wall water3 = new Wall(32+32+32, 288-32-32-32, 15);

        drawObjects = new ArrayList<>();
        drawObjects.add(water);
        drawObjects.add(wall);
        drawObjects.add(stone);
        drawObjects.add(water1);
        drawObjects.add(water2);
        drawObjects.add(water3);

        this.setBackground(Color.black);

        mouseListener = new MouseListener(drawObjects, this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    debug = !debug;
                } else if (e.getKeyChar() == 's') {
                    saveMapState("mapState.txt");
                    System.exit(0);
                } else if (e.getKeyChar() == 'l') {
                    loadMapState("mapState.txt");
                } else if (e.getKeyChar() == 'q') {
                    clearFile("mapState.txt");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawDragPanel(g);

        repaint();
    }


    private void drawDragPanel(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(SCREEN_WIDTH/2 - 600/2, 400, 600, 200);

        for (DrawObject o : drawObjects) {
            o.draw(g);
        }

        if (debug) {
            g.setColor(Color.PINK);
            for (int i = 0; i < ROWS; ++i) {
                g.drawLine(0, i * TILE_SIZE, SCREEN_WIDTH, i * TILE_SIZE);
            }

            for (int i = 0; i < COLS; ++i) {
                g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, SCREEN_HEIGHT);
            }
        }
    }


    void addDrawObject(DrawObject draw) {
        drawObjects.add(draw);
    }

    // TODO: removeDrawObject(DrawObject.size, Point position)
    void removeDrawObject(Point position){
        for (int i =0; i <drawObjects.size(); ++i) {
            if (drawObjects.get(i).position.equals(position)) {
                drawObjects.remove(i);
            }
        }
    }

    private void saveMapState(String mapFile) {
        try {
            File file = new File(mapFile);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);

            for (DrawObject object : drawObjects) {
                String info = object.getStringInfo();
                writer.write(info);
            }

            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Read the object more properly
    void loadMapState(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String outString;
            for (int i = 0; (outString = reader.readLine()) != null; ++i) {
                int[] values = getPositionInFile(outString);
                // wall.setRenderImage(x, y);
                // drawObjects.set(i, wall);
                if (i >= drawObjects.size()) {
                    Point position = new Point(values[0], values[1]);
                    Wall wall = new Wall(values[2], values[3], values[4]);
                    wall.setPosition(position);
                    drawObjects.add(wall);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean isDigit(char x) {
        return x >= '0' && x <= '9';
    }

    static int[] getPositionInFile(String outString) {
        int[] arr = new int[5];
        int k =0;
        for (int i =0; i < outString.length() - 2; ++i) {
            if (outString.charAt(i) == '=') {
                int j = i + 2;
                String value = "";
                while(j < outString.length() && isDigit(outString.charAt(j))) {
                    value += outString.charAt(j);
                    j++;
                }

                arr[k++] = Integer.parseInt(value);
            }
        }
        return  arr;
    }

    static void clearFile(String file_path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_path));
            writer.write("");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
