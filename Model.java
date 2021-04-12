/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Model.java

Model object, will have most of the game within this class

Mostly involves member variables that determine speeds and locations and methods which
control them and change them according to the mechanics of the game. Then a
function is called from Canvas that allows for its paint function to run.

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import javax.imageio.*;

public class Model {
    // constant values
    private static final int ANIMATION_DELAY = 100;

    private Random rand;

    // main components
    private Game game;
    private Canvas canvas;
    private Controls controls;
    private Status status;

    // array of all vehicles
    private Vehicle vehicles[];

    // universal properties
    private int game_timer, all_speed;
    private double factor;

    // width and height of frame
    private int width, height;

    // cooldown after adding vehicles
    private Boolean cooldown;
    private int cooldown_timer;

    // score properties
    private int score;
    private String score_str = "SCORE: " + String.valueOf(score);

    // game function
    private Boolean reset;
    private Boolean start, stop, maxed;

    // counts for timers
    private int count, create_count, alert_count, stop_count, meter, create, curr_vehicle, last_sel, incrx, incry;


    public Model (Game game) {
        // All default values are set below this:
        this.game = game;
        game_timer = 0;

        score = 0;
        score_str = "SCORE: " + String.valueOf(score);

        reset = false;

        start = false;
        stop = false;
        maxed = false;

        cooldown = false;
        cooldown_timer = 0;
        count = 0;
        stop_count = 0;
        alert_count = 0;
        create_count = 0;
        create = 0;
        curr_vehicle = 0;
        last_sel = 0;

        maxed = false;

        incrx = 0;
        incry = 0;
        factor = 1.0;

        all_speed = 2;

        vehicles = new Vehicle[20];
        rand = new Random();

        create_vehicles();
    }

    // vehicles are instantiated with appropriate parameters
    private void create_vehicles() {
        for (int i = 0; i < 20; i++) {
            int rand_dir = rand.nextInt(4) + 1;
            int rand_type = rand.nextInt(3) + 1;
            int rand_speed = rand.nextInt(4) + 2;
            if (rand_type == 2)
                vehicles[i] = new Truck(1, rand_dir, -100, -100, i, game, this);
            else
                vehicles[i] = new Car(rand_speed, rand_dir, -100, -100, i, game, this);
            System.out.println("Type: " + rand_type + " Direction: " + rand_dir);
        }
        // allows for drawing vehicles at random intervals
        create = rand.nextInt(60) + 0;
    }

    // passes this canvas to appropriate objects for label creation
    public void pass (Canvas canvas, Controls controls, Status status) {
        this.canvas = canvas;
        this.controls = controls;
        this.status = status;
        for (int i = 0; i < 20; i++) {
            vehicles[i].set_label(canvas);
        }
        // updates the table of data
        for (int i = 0; i < 6; i++) {
            status.pass_data(i, vehicles[i].get_name(), false, false, vehicles[i].get_direction());
        }
    }

    // passes incrememnt to all the vehicles
    public void incr (int incrx, int incry) {
        this.incrx = incrx;
        this.incry = incry;
        for (int i = 0; i < 20; i++) {
            vehicles[i].incr_x(incrx);
            vehicles[i].incr_y(incry);
        }
    }

    // passes scale to all the vehicles
    public void sfactor (double factor) {
        this.factor = factor;
        for (int i = 0; i < 20; i++) {
            vehicles[i].sfactor(factor, incrx, incry);
        }
    }

    // gets dimensions of the JFrame
    public void pass_dimensions(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < 20; i++) {
            vehicles[i].pass_dimensions(width, height);
        }
    }

    // randomizes every vehicle's colors
    public void randomize_colors () {
        for (int i = 0; i < 20; i++) {
            vehicles[i].randomize_colors();
        }
    }

    // deselects all vehicles
    public void deselect () {
        for (int i = 0; i < 20; i++) {
            vehicles[i].set_selected(false);
            vehicles[i].set_speed(vehicles[i].get_dspeed());
        }
    }

    public void draw (Graphics g, Canvas canvas, int panx, int pany) {
        if (start) {
            // draws vehicles initially after intervals
            if (create_count >= create && !maxed) {
                create_count = 0;
                create = rand.nextInt(120) + 100;
                status.pass_data(curr_vehicle, vehicles[curr_vehicle].get_name(), true, false, vehicles[curr_vehicle].get_direction());
                curr_vehicle++;
            }
            // caps off vehicles created automatically
            if (curr_vehicle > 5) {
                maxed = true;
            }
            // draws curr_vehicle amount of vehicles
            for (int i = 0; i < curr_vehicle; i++) {
                vehicles[i].draw(g, canvas, panx, pany);
                if (game_timer % all_speed == 0) {
                    if (!vehicles[i].get_selected()) {
                        vehicles[i].move(incrx, incry);
                    }
                    else {
                        // slows vehicle if selected
                        vehicles[i].slow(incrx, incry);
                    }
                }
                status.pass_data(i, vehicles[i].get_name(), true, false, vehicles[i].get_direction());
            }
            // resets vehicles if off frame
            for (int i = 0; i < 20; i++) {
                if (vehicles[i].get_direction() == 1) {
                    if (vehicles[i].get_x() > width + 50) {
                        vehicles[i].randomize_reset();
                    }
                }
                if (vehicles[i].get_direction() == 2) {
                    if (vehicles[i].get_x() < -50) {
                        vehicles[i].randomize_reset();
                    }
                }
                if (vehicles[i].get_direction() == 3) {
                    if (vehicles[i].get_y() > height + 50) {
                        vehicles[i].randomize_reset();
                    }
                }
                if (vehicles[i].get_direction() == 4) {
                    if (vehicles[i].get_y() < -50) {
                        vehicles[i].randomize_reset();
                    }
                }
            }
            // cooldown for adding vehicles
            if (cooldown) {
                cooldown_timer++;
            }
            if (cooldown_timer > 100) {
                cooldown_timer = 0;
                cooldown = false;
                controls.vehicle_enabled(true);
            }
            // manages remaining vehicle mechanics
            control_vehicles();
        }
        else {
            for (int i = 0; i < curr_vehicle; i++) {
                vehicles[i].draw(g, canvas, panx, pany);
            }
        }
    }

    // adds a new vehicle
    public void add_vehicle() {
        if (curr_vehicle >= 20) {
            System.out.println("Maxed!");
            status.set_alert("Max Vehicles!");
        }
        else if (!cooldown && start) {
            controls.vehicle_enabled(false);
            status.pass_data(curr_vehicle, vehicles[curr_vehicle].get_name(), true, false, vehicles[curr_vehicle].get_direction());
            curr_vehicle++;
            cooldown = true;
        }
    }

    // controls all vehicles collisions and possible collisions
    public void control_vehicles() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                vehicles[i].check_vehicle(vehicles[j]);
                if (vehicles[i].get_name() != vehicles[j].get_name() && vehicles[i].on_screen() && vehicles[j].on_screen()) {
                    if (check_crash(vehicles[i], vehicles[j])) {
                        vehicles[i].randomize_reset();
                        vehicles[j].randomize_reset();
                        System.out.println("CRASH with " + vehicles[i].get_name() + " and " + vehicles[j].get_name());
                        score -= 100;
                        status.set_alert("CRASH! -100");
                    }
                }
            }
        }
    }

    // checks if two vehicles crashed
    public Boolean check_crash(Vehicle v1, Vehicle v2) {
        if (v1.get_bounding().intersects(v2.get_bounding())) {
            if (v1.get_direction() != v2.get_direction())
                return true;
            else {
                v1.randomize_reset();
                return false;
            }
        }
        return false;
    }

    // this runs every game_tick, called through TListener
    public void game_tick() {
        if (start) {
            create_count++;
            game_timer++;
            score_str = "SCORE: " + String.valueOf(score);
            if (status.get_alert() != " ")
                alert_count++;
            if (alert_count > 50) {
                alert_count = 0;
                status.set_alert(" ");
            }
        }
        // score updated
        status.set_score(score_str);
        canvas.repaint();

        if (score < 0) {
            stop();
            status.set_alert("GAME OVER!");
        }
    }

    // sets the speed of all vehicles by changing the frequency of drawing
    public void set_speed (int speed) {
        all_speed = speed;
    }

    // start and stop program
    public void start() {
        controls.set_stop();
        start = true;
    }
    public void stop() {
        controls.set_start();
        start = false;
    }

    public void change_score() {
        score += 10;
        status.set_alert("+10");
    }

    // called to either reset the game completely (force == true) or when a car moves off screen (force == false)
    public void reset(Boolean force) {
        if (force) {
            game_timer = 0;
            reset = false;
            start = false;
            maxed = false;
            score = 0;

            cooldown = false;
            count = 0;
            stop_count = 0;
            alert_count = 0;
            create_count = 0;
            create = 0;
            curr_vehicle = 0;
            last_sel = 0;

            status.set_alert(" ");

            maxed = false;

            incrx = 0;
            incry = 0;
            factor = 1.0;

            for (int i = 0; i < 20; i++) {
                vehicles[i].randomize_reset();
                vehicles[i].set_selected(false);
                status.pass_data(i, " ", true, true, 0);
            }
            create = rand.nextInt(60) + 0;
            for (int i = 0; i < 6; i++) {
                status.pass_data(i, vehicles[i].get_name(), false, false, vehicles[i].get_direction());
            }

        }
    }

    public void actionPerformed(ActionEvent e){

    }

    // manages selection of vehicles depending on mouse location
    public void mouse_loc(int x, int y) {
        if (start) {
            for (int i = 0; i < 20; i++) {
                if (vehicles[i].get_bounding().contains(x, y)) {
                    if (!vehicles[i].get_selected()) {
                        vehicles[i].select(true);
                        last_sel = i;
                    }
                    else {
                        vehicles[i].select(false);
                        vehicles[i].set_speed(vehicles[i].get_dspeed());
                    }
                }
            }
        }
    }

    // Keyboard actions
    public void key_press (int c){
        if (c == KeyEvent.VK_SPACE) {
            if (vehicles[last_sel].get_selected() && !vehicles[last_sel].is_following()) {
                vehicles[last_sel].set_speed(vehicles[last_sel].get_dspeed() + 2);
                vehicles[last_sel].set_selected(false);
            }
        }
        if (c == KeyEvent.VK_ENTER) {
            System.out.println("Restarting");
            reset(true);
            controls.set_start();
        }
        if (c == KeyEvent.VK_G) {

        }
    }

    public void key_release(int c) {

    }
}
