package guicomponent;

import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JPanelQuery extends JPanel {
    private JLabel testoDomanda;
    private ButtonGroup gruppoScelte;
    private List<JRadioButton> listaButtoni;
    JProgressBar progressBar;
    ProgressBarHandler gestoreBarra;

    public JPanelQuery(Remotemethod.Domanda domanda) {
        if(domanda == null)
            throw new IllegalArgumentException();

        progressBar = new JProgressBar(0, 100); //deve poter essere acceduto sia dal thread che dal bottone di conferma
        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        this.testoDomanda = new JLabel(domanda.getTesto());
        add(testoDomanda, BorderLayout.EAST);

        listaButtoni = new LinkedList<>();
        gruppoScelte = new ButtonGroup();
        for(Remotemethod.Info scelta : domanda.getScelte().getSceltaList()) {
            JRadioButton button = new JRadioButton(scelta.getTesto());
            listaButtoni.add(button);
            gruppoScelte.add(button);
            add(button);
        }

        add(progressBar, BorderLayout.NORTH);

    }

    public void avvia(){
        gestoreBarra = new ProgressBarHandler(progressBar);
        gestoreBarra.start();
    }

    public int getOpzione() {
        gestoreBarra.interrupt();
        ButtonModel button = gruppoScelte.getSelection();
        if(button != null)
            return listaButtoni.indexOf(button);
        return -1;
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
            } catch (InterruptedException e) {
                System.out.println();
            }
            i++;
            jProgressBar.setValue(i);
        }

    }
}

