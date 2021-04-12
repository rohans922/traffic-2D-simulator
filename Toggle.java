/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Toggle.java

Toggle object, inherits JToggleButton, controls instruction display

*/

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Toggle extends JToggleButton implements ActionListener {

private Status status;
private Canvas canvas;

    public Toggle (Status status, Canvas canvas, String text) {
        setText(text);
        this.status = status;
        this.canvas = canvas;
    	addActionListener (this);
    }

    public void actionPerformed (ActionEvent e) {
        String text = getText();
        if (text == "How to Play") {
            setText("Hide");
            status.show_text(true);
        }
        else {
            setText("How to Play");
            status.show_text(false);
        }
        canvas.requestFocus();
    }
}
