package guicomponent;

import proto.Remotemethod;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class JPanelQuery extends JPanel {
    private JLabel testoDomanda;
    private ButtonGroup gruppoScelte;
    private List<JRadioButton> listaButtoni;

    public JPanelQuery(Remotemethod.Domanda domanda) {
        if(domanda == null)
            throw new IllegalArgumentException();

        this.testoDomanda = new JLabel(domanda.getTesto());
        add(testoDomanda);

        listaButtoni = new LinkedList<>();
        gruppoScelte = new ButtonGroup();
        for(Remotemethod.Info scelta : domanda.getScelte().getSceltaList()) {
            JRadioButton button = new JRadioButton(scelta.getTesto());
            listaButtoni.add(button);
            gruppoScelte.add(button);
            add(button);
        }
    }

    public int getOpzione() {
        JRadioButton button = (JRadioButton) gruppoScelte.getSelection();
        if(button != null)
            return listaButtoni.indexOf(button);
        return -1;
    }
}
