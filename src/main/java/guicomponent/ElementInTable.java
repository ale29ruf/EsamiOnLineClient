package guicomponent;

import proto.Remotemethod;
import protoadapter.AppelliProtoAdapter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * La classe viene utilizzata per far visualizzare la lista degli appelli al client.
 */
public class ElementInTable extends AbstractTableModel {
    private final String[] columnNames = {"Id appello", "Nome", "Data e ora", "Durata"};
    List<Remotemethod.Appello> appelli;

    public ElementInTable(AppelliProtoAdapter appelli){
        this.appelli = appelli.getAppelliList();
    }

    @Override
    public int getRowCount() {
        return appelli.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Remotemethod.Appello appello = appelli.get(rowIndex);
        if(columnIndex == 0)
            return appello.getId();
        if(columnIndex == 1)
            return appello.getNome();
        if(columnIndex == 2)
            return ""+appello.getOra();
        if(columnIndex == 3)
            return appello.getDurata();
        return null;
    }


}
