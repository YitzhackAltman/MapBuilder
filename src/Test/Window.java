package Test;

import DragableObjects.DragPanel;

import javax.swing.*;
import java.awt.*;

public class Window {
    private DragPanel panel;

    Window() {
        panel = new DragPanel();
        JFrame frame = new JFrame();
        frame.setTitle("Test Frame");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.add(panel);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
