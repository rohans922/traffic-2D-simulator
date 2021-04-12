/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Button.java

Button object, inherits JButton

*/

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Button extends JButton implements ActionListener {

private Model model;
private Canvas canvas;
private Controls controls;

    public Button (Model model, Canvas canvas,  Controls controls, String text)
    {
        setText(text);
        // allows for access of canvas
        this.model = model;
        this.canvas = canvas;
        this.controls = controls;
    	addActionListener (this);
    }

    // performs actions depending on button text
    public void actionPerformed (ActionEvent e) {
        String text = getText();
        if (text == "Restart") {
            System.out.println("Restarting");
            model.reset(true);
            controls.set_start();
        }
        if (text == "Start") {
            System.out.println("Started");
            model.start();
        }
        if (text == "Stop") {
            System.out.println("Stopped");
            model.stop();
        }
        if (text == "Add Vehicle") {
            System.out.println("Adding Vehicle");
            model.add_vehicle();
        }
        if (text == "Randomize Colors") {
            System.out.println("Randomizing Colors");
            model.randomize_colors();
        }
        if (text == "Deselect All") {
            System.out.println("Deslecting");
            model.deselect();
        }
        canvas.requestFocus();
    }
}
