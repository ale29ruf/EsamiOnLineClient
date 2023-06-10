package strategyvisualizer;

import component.Model;
import proto.Remotemethod;

import javax.swing.*;

public interface Strategy {

    JTable proietta(Model source, JComponent destination);
}
