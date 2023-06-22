package strategyvisualizer;

import guicomponent.JPanelQuery;
import guicomponent.JSenderButton;
import guicomponent.PannelloQuery;
import proto.Remotemethod;
import protoadapter.ListaDomandeProtoAdapter;
import protoadapter.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * La classe gestisce i vari JPanelQuery per un determinato appello.
 */
public class ListaDomandeView implements Strategy{

    private int timeInSecond = 100; //tempo massimo a disposizione per ogni domanda
    private Lock lock = new ReentrantLock();

    private int nDomande; //numero di domande per l'appello da gestire
    private int pass; //indice della domanda attualmente mostrata


    @Override
    public JSenderButton proietta(Model source, JComponent destination) {
        nDomande = 0;
        pass = 0;

        JPanel pannello = (JPanel) destination;

        pannello.removeAll();
        pannello.revalidate();
        pannello.repaint();


        Queue<JPanelQuery> codaJPanelQueryDaMostrare = new LinkedList<>(); //mantiene i riferimento ai pannelli del cardlayout in modo da ottenere la risposta selezionata dal relativo JPanelQuery

        List<Remotemethod.Domanda> domande = ((ListaDomandeProtoAdapter) source).getDomandeList();
        List<Remotemethod.Domanda> domandeOrd = new ArrayList<>(domande);
        //Ordino le domande in base all'id in modo da restituire le risposte nella classe JDialogModul con lo stesso ordine
        Comparator<Remotemethod.Domanda> comparator = Comparator.comparingInt(Remotemethod.Domanda::getId);
        Collections.sort(domandeOrd,comparator);

        for(Remotemethod.Domanda d : domandeOrd){
            JPanelQuery jPanelQuery = new JPanelQuery(d);
            codaJPanelQueryDaMostrare.add(jPanelQuery);
        }
        nDomande = domandeOrd.size();

        PannelloQuery pannelloQuery = new PannelloQuery(); //cambiato ogni volta per mostrare il pannello contenente la query corretta

        JPanelQuery primoPannello = codaJPanelQueryDaMostrare.peek();
        pannelloQuery.setPannello(primoPannello);
        primoPannello.avvia();

        JButton conferma = new JButton("Conferma");
        JSenderButton concludiTest = new JSenderButton("Concludi Test"); //raccoglie le risposte di ogni domanda mostrata
        concludiTest.setVisible(false);

        LinkedList<HandlerTimeout> timeouts = new LinkedList<>();
        for(int i=0; i<nDomande; i++){
            HandlerTimeout timeout = new HandlerTimeout(codaJPanelQueryDaMostrare,pannelloQuery,conferma,concludiTest, timeInSecond *i + timeInSecond);
            timeouts.add(timeout);
            timeout.start();
        }

        ActionListener azioneDiConferma = e -> {
            try{
                lock.lock();
                while(timeouts.get(pass).eInterrotto()){
                    pass++;
                }

                timeouts.get(pass).interrompi();
                pass++;

                if(pass < timeouts.size()){
                    HandlerTimeout timeoutSuccessivo = timeouts.get(pass);
                    timeoutSuccessivo.ritarda();
                }

                changeQuery(codaJPanelQueryDaMostrare,pannelloQuery,conferma,concludiTest);

                pannello.revalidate();
                pannello.repaint();
            } finally {
                lock.unlock();
            }

        };

        conferma.addActionListener(azioneDiConferma);

        pannello.add(pannelloQuery, BorderLayout.CENTER);

        pannello.add(concludiTest, BorderLayout.SOUTH);
        pannello.add(conferma, BorderLayout.EAST);

        //Operazioni necessarie altrimenti occorre ridimensionare leggermente la finestra per vedere gli elementi aggiunti
        pannello.revalidate();
        pannello.repaint();

        return concludiTest;
    }

    void changeQuery(Queue<JPanelQuery> codaJPanelQueryDaMostrare,PannelloQuery pannelloQuery,
                                   JButton conferma,JSenderButton concludiTest){
        try{
            lock.lock();
            JPanelQuery jPanelQuery = codaJPanelQueryDaMostrare.poll();
            concludiTest.addRisposta(Remotemethod.Risposta.newBuilder().setIdDomanda(jPanelQuery.getIdDomanda()).setIdScelta(jPanelQuery.getOpzione())
                    .build());
            pannelloQuery.removePannello();
            if(codaJPanelQueryDaMostrare.isEmpty()){
                conferma.setVisible(false);
                concludiTest.setVisible(true);

                pannelloQuery.revalidate();
                pannelloQuery.repaint();

            } else {
                pannelloQuery.setPannello(codaJPanelQueryDaMostrare.peek());
            }
        } finally {
            lock.unlock();
        }

    }

    private class HandlerTimeout extends Thread{

        private AtomicBoolean stop = new AtomicBoolean(false);
        private AtomicBoolean ritarda = new AtomicBoolean(false);
        private int time;
        Queue<JPanelQuery> codaJPanelQueryDaMostrare; PannelloQuery pannelloQuery; JButton conferma; JSenderButton concludiTest;
        public HandlerTimeout(Queue<JPanelQuery> codaJPanelQueryDaMostrare, PannelloQuery pannelloQuery, JButton conferma, JSenderButton concludiTest, int time) {
            this.time = time;
            this.codaJPanelQueryDaMostrare = codaJPanelQueryDaMostrare;
            this.pannelloQuery = pannelloQuery;
            this.conferma = conferma;
            this.concludiTest = concludiTest;
        }

        public void run(){
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
            }
            if(stop.get()) {
                return;
            }

            if(ritarda.get()) {
                try {
                    TimeUnit.SECONDS.sleep(timeInSecond);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(stop.get()) {
            } else {
                changeQuery(codaJPanelQueryDaMostrare,pannelloQuery,conferma,concludiTest);
            }

        }

        public void interrompi() {
            stop.set(true);
        }

        public boolean eInterrotto(){
            return stop.get();
        }

        public void ritarda() {
            ritarda.set(true);
            interrupt();
        }
    }

}


