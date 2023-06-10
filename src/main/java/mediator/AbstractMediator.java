package mediator;

import component.CollegueViewFactory;
import component.ListaAppelli;
import proto.Remotemethod;
import strategyvisualizer.Strategy;

import javax.swing.*;
import java.awt.*;
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

    public void setPartecipaButton(JButton prenotaButton){
        this.prenotaButton = prenotaButton;
        prenotaButton.setEnabled(false);
        prenotaButton.setVisible(false);
    }

    public void setAppelli(ListaAppelli appelli){
        this.appelli = appelli;
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
        JList<Remotemethod.Appello> appelloJList = visualizer.proietta(listaAppelli,pannello);

        pannello.revalidate();
        pannello.repaint();

        prenotaButton.setVisible(true);
        appelloJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                prenotaButton.setEnabled(true);
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
