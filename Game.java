/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Game.java

This class includes the main function and acts as a runner.

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Game extends JFrame implements ComponentListener {

    // Delay is the delay between each frame
    private static final int DELAY = 20;

    private Canvas canvas;
    private Controls controls;
    private Model model;
    private Status status;

    private boolean created;

    public static void main (String [] args) {
	new Game ();

    }

    public Game () {
        created = false;
        addComponentListener (this);
    	// Window setup
    	setLocation (50, 50);
    	setSize (1400, 900);
        setMinimumSize(new Dimension(600, 200));
        setMaximumSize(new Dimension(1400, 800));
    	setDefaultCloseOperation (EXIT_ON_CLOSE);
    	setLayout (new BorderLayout());

        // these two elements make up the main JFrame
        model = new Model (this);
        canvas = new Canvas (model);
        controls = new Controls(model, canvas);
        status = new Status(model, canvas);
        model.pass(canvas, controls, status);

        // this is the listener that allows for a timer to run, creating a game tick
        // TListener listener = new TListener(model, controls, canvas);
        TListener listener = new TListener(model, canvas);

        // adds to JFrame
    	add (canvas, BorderLayout.CENTER);
        add (controls, BorderLayout.EAST);
        add (status, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible (true);
        created = true;
        Dimension size = getSize();
        model.pass_dimensions(size.width - 400, size.height);
        canvas.set_map_size(size.width - 400, size.height);
        // creates the Timer object itself
        new Timer(DELAY, listener).start();

    }

    public void componentResized (ComponentEvent e) {
        if (created) {
            Dimension resize = getSize();
            int frame_width = resize.width - 400;
            int frame_height = resize.height;
            canvas.set_map_size(frame_width, frame_height);
        }
    }

    public int get_width () {
        Dimension resize = getSize();
        int frame_width = resize.width - 400;
        return frame_width;
    }

    public int get_height () {
        Dimension resize = getSize();
        int frame_height = resize.height;
        return frame_height;
    }

    // The other abstract methods
    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}

}
