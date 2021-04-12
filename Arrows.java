/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Arrows.java

Arrows object, inherits JPanel, acts as controls for panning

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Arrows extends JPanel implements MouseListener {

    private ImageIcon arrow_icon;
    private Image arrow_image, arrow_image_2;
    private Rectangle up, down, left, right;
    private Canvas canvas;

    public Arrows (Canvas canvas) {
        this.canvas = canvas;
        // reads enabled/disabled arrows
        try {
            arrow_image = ImageIO.read(getClass().getResource("arrows.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        try {
            arrow_image_2 = ImageIO.read(getClass().getResource("arrows2.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        arrow_icon = new ImageIcon();
        // creates rectangles to check click
        up = new Rectangle(58, 0, 56, 55);
        down = new Rectangle(58, 107, 56, 55);
        left = new Rectangle(0, 56, 56, 55);
        right = new Rectangle(115, 56, 56, 55);

        addMouseListener(this);
        arrow_icon.setImage(arrow_image);

    }

    public void set_enabled (Boolean enabled) {
        if (enabled)
            arrow_icon.setImage(arrow_image);
        if (!enabled)
            arrow_icon.setImage(arrow_image_2);
        repaint();
    }

    public void mousePressed (MouseEvent event) {
        // checks if clicks are in rectangles
        if (up.contains(event.getPoint().x, event.getPoint().y)) {
            System.out.println("UP");
            canvas.pan("up");
        }
        if (down.contains(event.getPoint().x, event.getPoint().y)) {
            System.out.println("DOWN");
            canvas.pan("down");
        }
        if (left.contains(event.getPoint().x, event.getPoint().y)) {
            System.out.println("LEFT");
            canvas.pan("left");
        }
        if (right.contains(event.getPoint().x, event.getPoint().y)) {
            System.out.println("RIGHT");
            canvas.pan("right");
        }
    }
    //
    public void mouseReleased (MouseEvent event) {}
    public void mouseClicked (MouseEvent event) {}
    public void mouseEntered (MouseEvent event) {}
    public void mouseExited (MouseEvent event) {}

    // paints the meter using a background image, a moving rectangle, and a white border
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.setOpaque(false);
        arrow_icon.paintIcon(this, g, 0, 0);
    }
}
