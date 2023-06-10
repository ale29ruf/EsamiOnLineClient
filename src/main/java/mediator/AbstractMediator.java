package mediator;

import component.CollegueViewFactory;
import component.ListaAppelli;
import proto.Remotemethod;
import strategyvisualizer.Strategy;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractMediator implements Mediatore{ //Si occupa della comunicazione locale

    boolean interrupt = false;
    Lock l = new ReentrantLock();

    JPanel pannello;
    JToolBar barraControllo;
    JTextArea logger;
    JButton caricaAppelli;
    JButton prenotaButton;
    JButton partecipaButton;
    JButton interrompiOpButton;
    ListaAppelli appelli = new ListaAppelli(this);

    public void setPannello(JPanel pannello){
        this.pannello = pannello;
    }

    public void setBarraControllo(JToolBar barraControllo){
        this.barraControllo = barraControllo;
    }

    public void setLogger(JTextArea logger){
        this.logger = logger;
        logger.setEditable(false);
    }

    public void setCaricaAppelliButton(JButton caricaAppelli){
        this.caricaAppelli = caricaAppelli;
        caricaAppelli.setVisible(true);
        caricaAppelli.setEnabled(true);
    }

    public void setPartecipaAppelliButton(JButton partecipaButton){
        this.partecipaButton = partecipaButton;
        partecipaButton.setVisible(true);
        partecipaButton.setEnabled(true);
    }

    public void setPrenotaButton(JButton prenotaButton){
        this.prenotaButton = prenotaButton;
        prenotaButton.setEnabled(false);
        prenotaButton.setVisible(false);
    }

    public void setInterrompiOpButton(JButton interrompiOpButton){
        this.interrompiOpButton = interrompiOpButton;
    }


    public void notificaComponenti(ListaAppelli listaAppelli){
        try{
            l.lock();
            if(interrupt){
                logger.setText("Appelli non caricati");
                return;
            }
        } finally {
            l.unlock();
        }

        pannello.removeAll();
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(ListaAppelli.class);
        JTable appelloJTabel = visualizer.proietta(listaAppelli,pannello);

        pannello.revalidate();
        pannello.repaint();


        prenotaButton.setVisible(true);
        appelloJTabel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Codice da eseguire quando viene selezionata una riga
                if (!event.getValueIsAdjusting()) {
                    //int selectedRow = appelloJTabel.getSelectedRow();
                    prenotaButton.setEnabled(true);
                }
            }
        });

        logger.setText("Appelli caricati");
    }

    public void comunicaCaricamentoAppello(){
        logger.setText("Caricamento appelli in corso ...");
    }

    @Override
    public void interrompiCaricamento() {
        logger.setText("Richiesta interruzione operazione in corso ...");
        try{
            l.lock();
            interrupt = true;
        } finally {
            l.unlock();
        }
    }




}
