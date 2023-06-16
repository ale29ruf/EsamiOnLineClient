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
    JProgressBar progressBar;
    ProgressBarHandler gestoreBarra;

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
        for(Remotemethod.Scelta scelta : domanda.getScelte().getScelteList()) {
            JCheckBoxWithId button = new JCheckBoxWithId(scelta.getTesto(),scelta.getId());
            listaButtoni.add(button);
            add(button);
        }

        add(progressBar, BorderLayout.NORTH);

    }

    public void avvia(){
        gestoreBarra = new ProgressBarHandler(progressBar);
        System.out.println("Thread settato da "+Thread.currentThread().getName());
        gestoreBarra.start();
    }

    public int getOpzione() {
        gestoreBarra.interrupt();
        for(int i=0; i<listaButtoni.size(); i++)
            if(listaButtoni.get(i).isSelected())
                return ((JCheckBoxWithId)(listaButtoni.get(i))).getIdScelta();
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
            } catch (InterruptedException e) {
                System.out.println();
            }
            i++;
            jProgressBar.setValue(i);
        }

    }
}

