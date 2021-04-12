/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Map.java

Map object, inherits ImageIcon and acts as the background

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Map extends ImageIcon {

    protected int x, y, width, height, incrx, incry;
    protected double scale;
    private Image img;

    private int[] h_constraints;
    private int[] v_constraints;

    public Map (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // incrx and incry store the increment due to window adjustment
        incrx = 0;
        incry = 0;
        // scale is the scale due to zoom
        scale = 1.0;

        try {
            img = ImageIO.read(getClass().getResource("map.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(img);
    }

    // resizes the map - since I used an image for the roads, I had to resize this element
    // to allow for zooming as opposed to zooming without resizing. The vehicles do not resize upon zoom.
    public void resize(double scale) {
        Image resize = img.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_SMOOTH);
        setImage(resize);
        this.scale = scale;
    }

    public double get_scale () {
        return scale;
    }

    // getters and setters
    public int get_x () {
        return x;
    }
    public int get_y () {
        return y;
    }
    public void set_x (int x) {
        this.x = x;
    }
    public void set_y (int y) {
        this.y = y;
    }

    public void draw (Graphics g, Canvas canvas, int panx, int pany) {
        this.paintIcon(canvas, g, incrx + panx, incry + pany);
    }

    // resets increment when called using scale
    public void incr_x (int incr) {
        incrx = (int)(incr * scale) + x;
    }
    public void incr_y (int incr) {
        incry = (int)(incr * scale) + y;
    }

    public int get_incr_x () {
        return incrx;
    }

    public int get_incr_y () {
        return incry;
    }
}
