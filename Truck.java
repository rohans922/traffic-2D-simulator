/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Truck.java

Truck object, inherits Vehicle

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

public class Truck extends Vehicle {

    private String color;
    private String[] COLORS = {"truckr.png", "trucko.png"};
    private Random rand;

    public Truck (int speed, int direction, int x, int y, int ID, Game game, Model model) {
        this.model = model;
        this.speed = speed;
        // dspeed is the default speed it is given at creation
        dspeed = speed;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.ID = ID;

        // boolean values for its current state
        following = false;
        approaching = false;
        intersected = false;
        selected = false;

        // increment values for window resizing
        incrx = 0;
        incry = 0;
        // scale factor for zooming
        factor = 1.0;
        name = ID + "-Truck";
        type = "truck";
        label = new JLabel(name);
        label.setFont (label.getFont ().deriveFont (15.0f));
        label.setForeground(Color.LIGHT_GRAY);
        rand = new Random();
        // width and height of frame
        fwidth = game.get_width();
        fheight = game.get_height();
        // gets dimensions based on vehicle orientation
        define_dimensions();
        // bounding rectangle for collision detection
        bounding = new Rectangle(x, y, iwidth, iheight);
        // tail for stopping when approaching
        tail = new Rectangle(x, y, 40, iheight);
        // selection icon that shows
        outline_icon = new ImageIcon();
        read_outline();
        randomize();
    }

    public void randomize() {
        // randomizes properties
        randomize_colors();
        label.setBounds(-100, -100, 200, 50);
        set_start();
        determine_tail();
    }

    public void randomize_colors() {
        // randomizes colors
        int rand_color = rand.nextInt(2) + 0;
        try {
            img = ImageIO.read(getClass().getResource(direction + COLORS[rand_color]));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        setImage(img);
    }

    public void draw (Graphics g, Canvas canvas, int panx, int pany) {
        // draws vehicle with appropriate properties
        define_dimensions ();
        determine_tail();
        // increments score
        if (in_intersection() && !intersected) {
            intersected = true;
            model.change_score();
        }
        if (!in_intersection()) {
            intersected = false;
        }

        bounding.setBounds(x, y, iwidth, iheight);
        this.paintIcon(canvas, g, incrx + panx, incry + pany);
        if (selected)
            outline_icon.paintIcon(canvas, g, incrx + panx, incry + pany);
        Graphics2D g2 = (Graphics2D) g;
        // g2.draw(tail);
        label.setBounds(incrx + panx, incry - 45 + pany, 200, 50);
    }

    public void define_dimensions () {
        // defines dimensions based on orientation
        if (direction == 1 || direction == 2) {
            iwidth = 74;
            iheight = 30;
        }
        else {
            iwidth = 30;
            iheight = 74;
        }
    }

}
