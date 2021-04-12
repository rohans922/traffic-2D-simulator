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

public class Status extends JPanel {

    private static final int METER_HEIGHT = 50;

    // major objects
    private Canvas canvas;
    private Model model;
    
    private JLabel score_label, score_alert, instructions;
    private Toggle more;
    private JScrollPane scroll;
    private Table data_table;

    private GridBagLayout gridbag;;
    private GridBagConstraints c;
    private Insets insets;

    private String default_str;
    private String instr_str;

    public Status (Model model, Canvas canvas) {
        // instruction strings, empty and full
        default_str = "<html><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></html>";
        instr_str = "<html><div><p><strong>OBJECTIVE:</strong></p></div><div><p>Guide the traffic by clicking cars to avoid crashes. You gain points for every car that passes an intersection.</p><p>&nbsp;</p></div><div><p><strong>CONTROLS:</strong></p></div><div><p><strong>Click</strong> a vehicles to select it and it will stop at the next intersection. Use&nbsp;<strong>shift</strong>&nbsp;while selected to make the vehicles speed up.</p></div><div><p>To restart, hit <strong>enter</strong> or click restart.</p><p>&nbsp;</p><p><strong>WIDGETS:</strong></p><p>The widgets on the right can change the properties of the vehicles and zoom and pan with the arrows and slider. You can also add vehicles and control the game. This display is your status panel which shows your score and which vehicles are in the simulation.</p></div></html>";

        setPreferredSize(new Dimension(200, 600));

        // allows for access of canvas/model
        this.canvas = canvas;
        this.model = model;

        // instantiates all objects for this panel, passing in appropriate values
        data_table = new Table();
        scroll = new JScrollPane(data_table);
        more = new Toggle(this, canvas, "How to Play");
        score_label = new JLabel("Score: ");
        score_label.setFont (score_label.getFont ().deriveFont (26.0f));
        score_label.setForeground(Color.BLACK);
        score_alert = new JLabel(" ", SwingConstants.CENTER);
        score_alert.setFont (score_label.getFont ().deriveFont (24.0f));
        score_alert.setForeground(Color.DARK_GRAY);
        instructions = new JLabel(default_str);

        // layout management
        gridbag = new GridBagLayout();
        c = new GridBagConstraints();
        insets = new Insets(10, 10, 10, 10);

        setLayout (gridbag);

        // adds objects to panel
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 3;
        insets.set(0, 10, 0, 10);
        c.insets = insets;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 3;
        add (score_label, c);
        c.gridx = 2;
        c.gridy = 4;
        add (score_alert, c);
        c.gridx = 0;
        c.gridy = 5;
        c.ipady = 165;
        add (scroll, c);
        c.ipady = 10;
        c.gridy = 6;
        add (more, c);
        c.gridy = 7;
        c.ipady = 20;
        add (instructions, c);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.setOpaque(true);
        this.setBackground(Color.LIGHT_GRAY);
    }

    // passes data to table
    public void pass_data (int row, String name, Boolean screen, Boolean reset, int direction) {
        if (reset) {
            data_table.model_data(row, " ", " ", " ");
        }
        else {
            String screen_name;
            String direction_str = " ";
            if (screen)
                screen_name = "Yes";
            else
                screen_name = "No";
            if (direction == 1)
                direction_str = "Right";
            else if (direction == 2)
                direction_str = "Left";
            else if (direction == 3)
                direction_str = "Down";
            else if (direction == 4)
                direction_str = "Up";
            data_table.model_data(row, name, screen_name, direction_str);
        }
    }

    // setters and getters
    public void set_score (String score) {
        score_label.setText(score);
    }

    public void set_alert (String alert) {
        score_alert.setText(alert);
    }

    public String get_alert () {
        return score_label.getText();
    }

    // toggle for instruction showing
    public void show_text (Boolean show) {
        if (show)
            instructions.setText(instr_str);
        if (!show)
            instructions.setText(default_str);
    }
}
