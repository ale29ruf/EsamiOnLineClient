package strategyvisualizer;

import component.Model;
import proto.Remotemethod;

import javax.swing.*;

public interface Strategy {

    JList<Remotemethod.Appello> proietta(Model source, JComponent destination);
}
