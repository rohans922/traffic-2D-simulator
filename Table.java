/*
Rohan Shaiva
rohan.shaiva@tufts.edu
Table.java

Table object, inherits JTable

*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class Table extends JTable  {

private TableModel tmodel;

    public Table ()
    {
        // sets default values for table
        setRowSelectionAllowed(false);
        tmodel = new TableModel();
        setModel(tmodel);
        setPreferredScrollableViewportSize(new Dimension(500, 70));
        setFillsViewportHeight(true);
    }

    public void model_data (int row, String name, String screen, String direction) {
        // when called, changes data accordingly
        tmodel.setValueAt(name, row, 0);
        tmodel.setValueAt(screen, row, 1);
        tmodel.setValueAt(direction, row, 2);
    }
}
