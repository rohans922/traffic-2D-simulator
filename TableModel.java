/*
Rohan Shaiva
rohan.shaiva@tufts.edu
TableModel.java

TableModel object, inherits AbstractTableModel

*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class TableModel extends AbstractTableModel {

    private String[] columnNames = {"Vehicle", "Visible", "Path"};

    private String[][] data = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void setValueAt(String value, int row, int col) {
        data[row][col] = value;
        // refreshes table
        fireTableDataChanged();
    }
 }
