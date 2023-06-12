package strategyvisualizer;

import protoadapter.Model;

import javax.swing.*;

public interface Strategy {

    JComponent proietta(Model source, JComponent destination);

}
