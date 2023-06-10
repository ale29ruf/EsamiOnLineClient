package strategyvisualizer;

import component.CollegueViewFactory;
import component.ListaAppelli;
import component.Model;
import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;

public class ListaAppelliView implements Strategy{

    @Override
    public JList<Remotemethod.Appello> proietta(Model source, JComponent destination) {
        ListaAppelli listaAppelli = (ListaAppelli) source;
        DefaultListModel<Remotemethod.Appello> lista = new DefaultListModel<>();
        for (Remotemethod.Appello appello : listaAppelli.getAppelloList()) {
            lista.addElement(appello);
        }
        JList<Remotemethod.Appello> jList = new JList<>(lista);
        JScrollPane scrollPane = new JScrollPane(jList);

        JPanel panel = (JPanel) destination; //ricevere direttamente un JPanel

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);

        return jList;

    }
}
