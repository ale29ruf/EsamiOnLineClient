package strategyvisualizer;

import protoadapter.Model;

import javax.swing.*;

public interface Strategy {

    JTable proietta(Model source, JComponent destination);
}
