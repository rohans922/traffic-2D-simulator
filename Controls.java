/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Controls.java

Controls object, will have the controls on the right

*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class Controls extends JPanel implements ActionListener {

    private static final int METER_HEIGHT = 50;

    // major objects
    private Canvas canvas;
    private Model model;
    private Button start, restart, vehicle, colors, deselect;
    private Slider speed_slider, zoom_slider;
    private JLabel speed_label, zoom_label, pan_label;
    // arrow object which controls panning
    private Arrows arrows;

    private GridBagLayout gridbag;;
    private GridBagConstraints c;
    private Insets insets;

    public Controls (Model model, Canvas canvas) {
        setPreferredSize(new Dimension(200, 600));

        // allows for access of canvas/model
        this.canvas = canvas;
        this.model = model;

        // layout management
        gridbag = new GridBagLayout();
        c = new GridBagConstraints();
        insets = new Insets(10, 10, 10, 10);

        setLayout (gridbag);

        // instantiates all objects for this panel, passing in appropriate values
        start = new Button (model, canvas, this, "Start");
        restart = new Button (model, canvas, this, "Restart");
        vehicle = new Button (model, canvas, this, "Add Vehicle");
        colors = new Button (model, canvas, this, "Randomize Colors");
        deselect = new Button (model, canvas, this, "Deselect All");
        speed_slider = new Slider (model, canvas, "speed", this);
        zoom_slider = new Slider (model, canvas, "zoom", this);
        arrows = new Arrows(canvas);
        speed_label = new JLabel("Set Speed", SwingConstants.CENTER);
        zoom_label = new JLabel("Set Zoom", SwingConstants.CENTER);
        pan_label = new JLabel("Panning", SwingConstants.CENTER);

        // enables elements according to game state - by default, all disabled
        speed_slider.setEnabled(false);
        zoom_slider.setEnabled(false);
        arrows.set_enabled(false);

        // adds objects to panel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 3;
        insets.set(0, 10, 0, 10);
        c.insets = insets;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 3;
        add (deselect, c);
        c.gridy = 4;
        add (colors, c);
        c.gridy = 5;
        insets.set(0, 10, 10, 10);
        c.insets = insets;
        add (vehicle, c);
        c.gridy = 6;
        c.ipady = 0;
        insets.set(0, 10, 0, 10);
        c.insets = insets;
        add (speed_label, c);
        c.gridy = 7;
        add (speed_slider, c);
        c.gridy = 8;
        add (zoom_label, c);
        c.gridy = 9;
        add (zoom_slider, c);
        c.gridy = 10;
        insets.set(0, 15, 0, 10);
        c.insets = insets;
        add (pan_label, c);
        insets.set(2, 15, 0, 15);
        c.insets = insets;
        c.gridx = 0;
        c.gridy = 11;
        c.ipady = 165;
        add (arrows, c);
        c.ipady = 5;
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 16;
        add (start, c);
        c.gridy = 17;
        add (restart, c);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.setOpaque(true);
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void actionPerformed(ActionEvent e){

    }

    // basic controls for enabling/disabling/starting/stopping

    public void start_enabled (Boolean toggle) {
        if (toggle)
            start.setEnabled(true);
        else
            start.setEnabled(false);
    }

    public void vehicle_enabled (Boolean toggle) {
        if (toggle)
            vehicle.setEnabled(true);
        else
            vehicle.setEnabled(false);
    }

    public void enable_pan (Boolean enable) {
        if (enable)
            arrows.set_enabled(true);
        else
            arrows.set_enabled(false);
    }

    public void set_stop () {
        speed_slider.setEnabled(true);
        zoom_slider.setEnabled(true);
        start.setText("Stop");
    }

    public void set_start () {
        speed_slider.setEnabled(false);
        zoom_slider.setEnabled(false);
        start.setText("Start");
    }
}
