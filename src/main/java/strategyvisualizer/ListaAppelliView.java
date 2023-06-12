package strategyvisualizer;

import protoadapter.AppelliProtoAdapter;
import protoadapter.Model;
import guicomponent.ElementInTable;

import javax.swing.*;
import java.awt.*;

public class ListaAppelliView implements Strategy{

    @Override
    public JTable proietta(Model source, JComponent destination) {
        AppelliProtoAdapter listaAppelli = (AppelliProtoAdapter) source;
        ElementInTable tableList = new ElementInTable(listaAppelli);
        JTable jTable = new JTable(tableList);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(jTable);

        JPanel panel = (JPanel) destination; //ricevere direttamente un JPanel

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);

        return jTable;

    }
}
