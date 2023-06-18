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

public class ListaDomandeView implements Strategy{

    static int timeInSecond = 10; //tempo massimo a disposizione per ogni domanda
    static Lock lock = new ReentrantLock();

    int nDomande;
    int pass; //indice della domanda attualmente mostrata


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
            } finally {
                lock.unlock();
            }

        };

        conferma.addActionListener(azioneDiConferma);

        pannello.add(pannelloQuery, BorderLayout.CENTER);

        pannello.add(concludiTest, BorderLayout.EAST);
        pannello.add(conferma, BorderLayout.EAST);

        //Operazioni necessarie altrimenti occorre ridimensionare leggermente la finestra per vedere gli elementi aggiunti
        pannello.revalidate();
        pannello.repaint();

        return concludiTest;
    }

    public static void changeQuery(Queue<JPanelQuery> codaJPanelQueryDaMostrare,PannelloQuery pannelloQuery,
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
            } else {
                pannelloQuery.setPannello(codaJPanelQueryDaMostrare.peek());
            }
        } finally {
            lock.unlock();
        }

    }

}

class HandlerTimeout extends Thread{

    AtomicBoolean stop = new AtomicBoolean(false);
    AtomicBoolean ritarda = new AtomicBoolean(false);
    int time;
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
            System.out.println("Catch dell'InterruptedException");
        }
        if(stop.get()) {
            System.out.println("Non faccio nulla dato che sono interrotto");
            return;
        }

        if(ritarda.get()){
            try {
                TimeUnit.SECONDS.sleep(ListaDomandeView.timeInSecond);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(stop.get()) {
                System.out.println("Non faccio nulla dato che sono interrotto");
                return;
            }
        }

        if(stop.get()) {
            System.out.println("Non faccio nulla dato che sono interrotto");
            return;
        }
        ListaDomandeView.changeQuery(codaJPanelQueryDaMostrare,pannelloQuery,conferma,concludiTest);
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
