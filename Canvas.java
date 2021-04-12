/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Canvas.java

Canvas object, will have most of the game within this class

Mostly involves member variables that determine speeds and locations and methods which
control them and change them according to the mechanics of the game

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

// public class Canvas extends JPanel implements ActionListener, KeyListener, MouseListener {
public class Canvas extends JPanel implements MouseListener, KeyListener {

    private Model model;
    private Map map;
    private int panx, pany, curr_zoom;

    public Canvas (Model model) {
        // remembers panning/zoom increments/factors
        panx = 0;
        pany = 0;
        curr_zoom = 1;
        this.model = model;
        setLayout(null);
        setBorder (new LineBorder(Color.RED, 2));

        // map is the background
        map = new Map (0, 0, 1000, 800);
        map.resize(1);
        // allows for a keyboard and mouse listeners
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

    }

    public void paintComponent (Graphics g) {
        // paints JPanel
        super.paintComponent(g);
        // draws remaining components
        map.draw(g, this, panx, pany);
        model.draw(g, this, panx, pany);
        this.setOpaque(true);
    }

    // map size is determined based on window size and centering it in the frame
    public void set_map_size(int width, int height) {
        model.pass_dimensions(width, height);
        int width_diff = (1000 - width)/2;
        int height_diff = (800 - height)/2;
        map.incr_x(-1 * width_diff);
        map.incr_y(-1 * height_diff);
        model.incr(map.get_incr_x(), map.get_incr_y());
    }

    // zoom changes remaining factors
    public void set_zoom(int zoom) {
        if (zoom == 1) {
            curr_zoom = 1;
            pany = 0;
            panx = 0;
            map.resize(1.0);
            model.sfactor(1.0);
        }
        if (zoom == 2) {
            curr_zoom = 2;
            pany = 0;
            panx = 0;
            map.resize(1.3);
            model.sfactor(1.3);
        }
        if (zoom == 3) {
            curr_zoom = 3;
            map.resize(1.5);
            model.sfactor(1.5);
        }
    }

    // panning controls
    public void pan (String direction) {
        if (map.get_scale() != 1.0) {
            if (direction == "up") {
                pany += 20;
            }
            if (direction == "down") {
                pany -= 20;
            }
            if (direction == "left") {
                panx += 20;
            }
            if (direction == "right") {
                panx -= 20;
            }
            // limitations depending on zoom
            if (pany > 0)
                pany = 0;
            if (panx > 0)
                panx = 0;
            if (pany < -260 && curr_zoom == 2)
                pany = -260;
            if (panx < -300 && curr_zoom == 2)
                panx = -300;
            if (pany < -420 && curr_zoom == 3)
                pany = -420;
            if (panx < -500 && curr_zoom == 3)
                panx = -500;
        }
    }

    // returns current scale factor
    public double get_map_zoom() {
        return map.get_scale();
    }


    // Keyboard actions
    public void keyPressed (KeyEvent e){
        int c = e.getKeyCode();
        model.key_press(c);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        // when various keys are released, different actions occur
        int c = e.getKeyCode();
        model.key_release(c);
    }

    // passes in "real" mouse value even when the window is resized or scaled
    public void mousePressed (MouseEvent event) {
        System.out.println(((event.getPoint().x + " " + (map.get_incr_x() * -1)) + " " + map.get_scale()));
        System.out.println(((event.getPoint().x + (map.get_incr_x() * -1)) + " " + map.get_scale()));
        System.out.println(((event.getPoint().x + (map.get_incr_x() * -1)) * map.get_scale()));
        model.mouse_loc((int)((event.getPoint().x + (map.get_incr_x() * -1)) / map.get_scale()), (int)((event.getPoint().y + (map.get_incr_y() * -1)) / map.get_scale()));
    }
    //
    public void mouseReleased (MouseEvent event) {}
    public void mouseClicked (MouseEvent event) {}
    public void mouseEntered (MouseEvent event) {}
    public void mouseExited (MouseEvent event) {}

}
