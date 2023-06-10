package strategyvisualizer;

import component.ListaAppelli;
import component.Model;
import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;

public class ListaAppelliView implements Strategy{

    @Override
    public JTable proietta(Model source, JComponent destination) {
        ListaAppelli listaAppelli = (ListaAppelli) source;
        ElementInTable tableList = new ElementInTable(listaAppelli);
        JTable jTable = new JTable(tableList);
        JScrollPane scrollPane = new JScrollPane(jTable);

        JPanel panel = (JPanel) destination; //ricevere direttamente un JPanel

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);

        return jTable;

    }
}
