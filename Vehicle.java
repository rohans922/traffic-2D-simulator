/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Vehicle.java

Vehicle object, inherits ImageIcon

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

public abstract class Vehicle extends ImageIcon {

    protected int speed, dspeed, direction, x, y, ID, fwidth, fheight, iwidth, iheight, incrx, incry, slow_count;
    protected double factor;
    protected Boolean selected, approaching, following, all_created, intersected;
    protected String name, type;
    protected JLabel label;
    protected Image img, outline;
    protected ImageIcon outline_icon;
    protected Random rand;
    protected Rectangle bounding, tail;
    protected Model model;

    public Vehicle () {

    }

    public Vehicle (int speed, int direction, int x, int y, int ID, Game game, Model model) {

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

    public String get_name () {
        return name;
    }

    public int get_speed () {
        return speed;
    }

    public int get_dspeed () {
        return dspeed;
    }

    public void set_speed (int speed) {
        this.speed = speed;
    }

    public int get_width() {
        return iwidth;
    }

    public int get_height() {
        return iheight;
    }

    public int get_direction () {
        return direction;
    }

    public void set_direction (int direction) {
        this.direction = direction;
    }

    public Boolean get_selected () {
        return selected;
    }

    public void set_selected (Boolean selected) {
        this.selected = selected;
    }

    public Rectangle get_bounding() {
        return bounding;
    }

    public Rectangle get_tail() {
        return tail;
    }

    public void determine_tail() {
        if (direction == 1)
            tail.setBounds(x - 20, y, 20, iheight);
        if (direction == 2)
            tail.setBounds(x + iwidth, y, 20, iheight);
        if (direction == 3)
            tail.setBounds(x, y - 20, iwidth, 20);
        if (direction == 4)
            tail.setBounds(x, y + iheight, iwidth, 20);
    }

    // gets original width and height of canvas
    public void pass_dimensions (int width, int height) {
        fwidth = width;
        fheight = height;
    }

    // reads the outline file depending on the vehicle and orientation
    public void read_outline () {
        try {
            outline = ImageIO.read(getClass().getResource(direction + type + "outline.png"));
        } catch (IOException e) {
            System.out.println("Could not read image!");
        }
        outline_icon.setImage(outline);
    }

    // sets the start position depending on orientation
    public void set_start() {
        rand  = new Random();
        if (direction == 1) {
            int rand_start = rand.nextInt(3) + 0;
            if (rand_start == 0)
                y = 175;
            if (rand_start == 1)
                y = 400;
            if (rand_start == 2)
                y = 635;
            x = -50;
        }
        if (direction == 2) {
            int rand_start = rand.nextInt(3) + 0;
            if (rand_start == 0)
                y = 135;
            if (rand_start == 1)
                y = 365;
            if (rand_start == 2)
                y = 595;
            x = fwidth + 50;
        }
        if (direction == 3) {
            int rand_start = rand.nextInt(2) + 0;
            if (rand_start == 0)
                x = 200;
            if (rand_start == 1)
                x = 730;
            y = -50;
        }
        if (direction == 4) {
            int rand_start = rand.nextInt(2) + 0;
            if (rand_start == 0)
                x = 240;
            if (rand_start == 1)
                x = 770;
            y = fheight + 50;
        }
    }

    // selects or deselects the vehicle
    public void select (Boolean select) {
        if (select) {
            selected = true;
        }
        else {
            selected = false;
        }
    }

    public Boolean is_following () {
        return following;
    }

    // randomizing functions
    public void randomize () {

    }

    public void randomize_reset() {
        int rand_dir = rand.nextInt(4) + 1;
        int rand_speed = rand.nextInt(4) + 2;
        if (type == "car")
            speed = rand_speed;
        else if (type == "truck")
            speed = 1;
        direction = rand_dir;
        selected = false;
        following = false;
        approaching = false;
        // reread new outline with new properties
        read_outline();
        randomize();
    }

    public void randomize_colors() {

    }

    public void set_label (Canvas canvas) {
        canvas.add(label);
        label.setBounds(-100, -100, 200, 50);
    }

    public void draw (Graphics g, Canvas canvas, int panx, int pany) {

    }

    // checks if vehicle is about to hit another and then matches the speed of the
    // vehicle ahead of it.
    public void check_vehicle (Vehicle v) {
        if (v.get_direction() == direction && (v.get_y() == y) || (v.get_x() == x)) {
            if ((x > 0 && direction == 1) || (x < fwidth && direction == 2) || (y > 0 && direction == 3) || (y < fheight && direction == 4)) {
                if (v.get_tail().intersects(bounding)) {
                    following = true;
                    speed = v.get_speed();
                }
            }
        }
    }

    // if the car is fully on screen, returns true
    public Boolean on_screen () {
        if ((x + iwidth) > 100 && x < (fwidth - 100) && (y + iheight) > 100 && y < (fheight - 100)) {
            return true;
        }
        else
            return false;
    }

    // controls all movement
    public void move (int incrx, int incry) {
        approaching = false;
        if (direction == 1) {
            x += speed;
        }
        if (direction == 2) {
            x -= speed;
        }
        if (direction == 3) {
            y += speed;
        }
        if (direction == 4) {
            y -= speed;
        }
        incr_x (incrx);
        incr_y (incry);
    }

    // controls the stopping of the cars
    public void slow (int incrx, int incry) {
        if (approaching())
            approaching = true;
        if (approaching) {
            // slow_count allows for a gradual decrease
            slow_count++;
            if (slow_count < 5 && direction == 1)
                x += speed;
            else if (slow_count < 10 && direction == 1 && speed > 0) {
                speed--;
                x += (speed);
            }
            if (slow_count < 5 && direction == 2)
                x -= speed;
            else if (slow_count < 10 && direction == 2 && speed > 0) {
                speed--;
                x -= (speed);
            }
            if (slow_count < 5 && direction == 3)
                y += speed;
            else if (slow_count < 10 && direction == 3 && speed > 0) {
                speed--;
                y += (speed);
            }
            if (slow_count < 5 && direction == 4)
                y -= speed;
            else if (slow_count < 10 && direction == 4 && speed > 0) {
                speed--;
                y -= (speed);
            }
            incr_x (incrx);
            incr_y (incry);
        }
        else
            move (incrx, incry);
    }

    // determines if a vehicle is approaching an intersection
    public Boolean approaching () {
        if (direction == 1 && (((x + iwidth) >= 175 && (x + iwidth) <=195) || ((x + iwidth) >= 705 && (x + iwidth) <= 725)))
            return true;

        else if (direction == 2 && ((x >= 285 && x <= 305) || (x >= 815 && x <= 835)))
            return true;

        else if (direction == 3 && (((y + iheight) >= 115 && (y + iheight) <= 135) || ((y + iheight) >= 345 && (y + iheight) <= 365) || ((y + iheight) >= 575 && (y + iheight) <= 595)))
            return true;

        else if (direction == 4 && ((y >= 220 && y <= 240) || (y >= 450 && y <= 470) || (y >= 680 && y <= 700)))
            return true;
        else
            return false;
    }

    // determines if a vehicle is in an interesection
    public Boolean in_intersection () {
        if (direction == 1) {
           if (((x + iwidth) > 199 && x < 275) || ((x + iwidth) > 728 && x < 803))
               return true;
        }
        if (direction == 2) {
           if ((x > 199 && (x + iwidth) < 275) || (x > 728 && (x + iwidth) < 803))
               return true;
        }
        if (direction == 3) {
           if (((y + iheight) > 134 && y < 209) || ((y + iheight) > 363 && y < 437) || ((y + iheight) > 597 && y < 672))
               return true;
        }
        if (direction == 4) {
           if ((y > 134 && (y + iheight) < 209) || (y > 363 && (y + iheight) < 437) || (y > 597 && (y + iheight) < 672))
               return true;
        }
        return false;
    }

    // determines the new values of x and y based on window and scale
    public void incr_x (int incr) {
        // incrx = (int)((incr + x) * factor);
        incrx = (incr + (int)(x * factor));
    }
    public void incr_y (int incr) {
        // incry = (int)((incr + y) * factor);
        incry = (incr + (int)(y * factor));
    }

    public int get_incr_x () {
        return incrx;
    }

    public int get_incr_y () {
        return incry;
    }

    // determines new value with scale
    public void sfactor (double factor, int incrx, int incry) {
        this.factor = factor;
        incr_x(incrx);
        incr_y(incry);
    }
}
