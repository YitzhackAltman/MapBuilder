package DragableObjects;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {

    private static final int LEFT_BUTTON = 1;
    private static final int RIGHT_BUTTON = 3;

    private Draggable draggable;
    private final List<DrawObject> drawObjects;
    private final DragPanel dragPanel;

    public MouseListener(List<DrawObject> drawObjects, DragPanel panel) {
        dragPanel = panel;
        this.draggable = null;
        this.drawObjects = drawObjects;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pressed = new Point(e.getX(), e.getY());
        if (e.getButton() == LEFT_BUTTON) {
            for (DrawObject object : drawObjects) {
                if (object.isInBounds(pressed)) {
                    DrawObject d = object.getCopy();
                    dragPanel.addDrawObject(d);
                    this.draggable = d;
                    break;
                }
            }
        }else if (e.getButton() == RIGHT_BUTTON) {
            for (DrawObject object : drawObjects) {
                if (object.isInBounds(pressed)) {
                    // TODO: dragPanel.removeDrawObject(object, object.position);
                    dragPanel.removeDrawObject(object.position);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.draggable = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /// or here
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggable != null) {
            Point position = new Point(e.getX(), e.getY());
            this.draggable.drag(position);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Point position = new Point(e.getX(), e.getY());
        // this.draggable.drag(position);
    }
}
