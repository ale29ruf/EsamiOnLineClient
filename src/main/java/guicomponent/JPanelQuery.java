package guicomponent;

import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JPanelQuery extends JPanel {
    private JLabel testoDomanda;
    private int idDomanda;
    private List<JCheckBox> listaButtoni;
    private JProgressBar progressBar;
    private ProgressBarHandler gestoreBarra;

    public JPanelQuery(Remotemethod.Domanda domanda) {
        if(domanda == null)
            throw new IllegalArgumentException();

        progressBar = new JProgressBar(0, 100); //deve poter essere acceduto sia dal thread che dal bottone di conferma
        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        this.testoDomanda = new JLabel(domanda.getTesto());
        this.idDomanda = domanda.getId();
        add(testoDomanda, BorderLayout.EAST);

        listaButtoni = new LinkedList<>();
        ButtonGroup group = new ButtonGroup();
        for(Remotemethod.Scelta scelta : domanda.getScelte().getScelteList()) {
            JRadioButtonWithId button = new JRadioButtonWithId(scelta.getTesto(),scelta.getId());
            listaButtoni.add(button);
            group.add(button);
            add(button);
        }

        add(progressBar, BorderLayout.NORTH);
        setPreferredSize(new Dimension(500,300));

    }

    public void avvia(){
        gestoreBarra = new ProgressBarHandler(progressBar);
        gestoreBarra.start();
    }

    public int getOpzione() {
        gestoreBarra.interrupt();
        for(int i=0; i<listaButtoni.size(); i++)
            if(listaButtoni.get(i).isSelected())
                return ((JRadioButtonWithId)(listaButtoni.get(i))).getIdScelta();
        return -1;
    }

    public int getIdDomanda(){
        return idDomanda;
    }
}

class ProgressBarHandler extends Thread {
    JProgressBar jProgressBar;

    public ProgressBarHandler(JProgressBar jProgressBar){
        this.jProgressBar = jProgressBar;
    }

    public void run(){
        int i=0;
        jProgressBar.setValue(i);
        while(i<100){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            i++;
            jProgressBar.setValue(i);
        }

    }
}

