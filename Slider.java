/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Slider.java

Slider object, inherits JSlider

*/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Slider extends JSlider implements ChangeListener {

private Model model;
private Canvas canvas;
private String type;
private Controls controls;

    public Slider (Model model, Canvas canvas, String type, Controls controls)
    {
        this.controls = controls;
        this.type = type;
        // setting default values for slider
        setMinimum(1);
        setMaximum(3);
        if (type == "speed")
            setValue(2);
        if (type == "zoom")
            setValue(1);
        // allows for access of canvas
        this.model = model;
        this.canvas = canvas;
    	addChangeListener (this);
        setSnapToTicks(true);
        setPaintTicks(true);
        setMajorTickSpacing(1);
        setPaintLabels(true);
    }

    // checks changes and calls appropriate functions
    public void stateChanged (ChangeEvent e) {
        if (type == "speed") {
            // 4 - reverses it because speed control is reversed
            System.out.println("Speed set to " + (4 - getValue()));
            model.set_speed(4 - getValue());
            canvas.requestFocus();
        }
        if (type == "zoom") {
            System.out.println("Zoom set to " + getValue());
            canvas.set_zoom(getValue());
            canvas.requestFocus();
            if (getValue() == 1)
                controls.enable_pan(false);
            else
                controls.enable_pan(true);
        }
    }
}
