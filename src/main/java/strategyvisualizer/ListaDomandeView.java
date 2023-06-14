package strategyvisualizer;

import guicomponent.JPanelQuery;
import guiprova.PannelloQuery;
import guiprova.Prova;
import proto.Remotemethod;
import protoadapter.ListaDomandeProtoAdapter;
import protoadapter.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListaDomandeView implements Strategy{

    static int nDomande;
    static int timeInMilliSecond = 10;
    static int pass = 0;


    @Override
    public JButton proietta(Model source, JComponent destination) {

        JToolBar barraControllo = (JToolBar) destination;
        JPanel pannello = (JPanel) SwingUtilities.getAncestorOfClass(JPanel.class, barraControllo);

        pannello.removeAll();
        pannello.revalidate();
        pannello.repaint();


        Queue<JPanelQuery> codaJPanelQueryDaMostrare = new LinkedList<>(); //mantiene i riferimento ai pannelli del cardlayout in modo da ottenere la risposta selezionata dal relativo JPanelQuery

        List<Remotemethod.Domanda> domande = ((ListaDomandeProtoAdapter) source).getDomandeList();
        for(Remotemethod.Domanda d : domande){
            JPanelQuery jPanelQuery = new JPanelQuery(d);
            codaJPanelQueryDaMostrare.add(jPanelQuery);
        }
        nDomande = domande.size();

        PannelloQuery pannelloQuery = new PannelloQuery(); //cambiato ogni volta per mostrare il pannello contenente la query corretta

        JPanelQuery primoPannello = codaJPanelQueryDaMostrare.peek();
        pannelloQuery.setPannello(primoPannello);
        primoPannello.avvia();

        List<Integer> listaRisposte = new LinkedList<>(); //raccoglie le risposte di ogni domanda mostrata

        JButton conferma = new JButton("Conferma");
        JButton concludiTest = new JButton("Concludi Test");
        concludiTest.setVisible(false);

        LinkedList<HandlerTimeout> timeouts = new LinkedList<>();
        for(int i=0; i<nDomande; i++){
            HandlerTimeout timeout = new HandlerTimeout(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest,timeInMilliSecond*i + timeInMilliSecond);
            timeouts.add(timeout);
            timeout.start();
        }


        ActionListener azioneDiConferma = e -> {
            while(timeouts.get(pass).eInterrotto()){
                pass++;
            }
            System.out.println("All'interno dell'ActionListener");
            timeouts.get(pass).interrompi();
            pass++;

            if(pass < timeouts.size()){
                HandlerTimeout timeoutSuccessivo = timeouts.get(pass);
                timeoutSuccessivo.ritarda();
            }

            changeQuery(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
        };

        conferma.addActionListener(azioneDiConferma);

        pannello.add(pannelloQuery, BorderLayout.CENTER);

        pannello.add(concludiTest, BorderLayout.EAST);
        pannello.add(conferma, BorderLayout.EAST);

        return concludiTest;
    }

    public static void changeQuery(java.util.Queue<JPanelQuery> codaJPanelQueryDaMostrare,java.util.List<Integer> listaRisposte,PannelloQuery pannelloQuery,
                                   JButton conferma,JButton concludiTest){
        JPanelQuery jPanelQuery = codaJPanelQueryDaMostrare.poll();
        listaRisposte.add((jPanelQuery.getOpzione()));
        pannelloQuery.removePannello();
        if(codaJPanelQueryDaMostrare.isEmpty()){
            System.out.println(listaRisposte);
            conferma.setVisible(false);
            concludiTest.setVisible(true);
        } else {
            pannelloQuery.setPannello(codaJPanelQueryDaMostrare.peek());
        }
    }

}

class HandlerTimeout extends Thread{

    boolean stop = false;
    boolean ritarda = false;
    int time;
    Queue<JPanelQuery> codaJPanelQueryDaMostrare; List<Integer> listaRisposte; PannelloQuery pannelloQuery; JButton conferma; JButton concludiTest;
    public HandlerTimeout(java.util.Queue<JPanelQuery> codaJPanelQueryDaMostrare, List<Integer> listaRisposte, PannelloQuery pannelloQuery, JButton conferma, JButton concludiTest, int time) {
        this.time = time;
        this.codaJPanelQueryDaMostrare = codaJPanelQueryDaMostrare;
        this.listaRisposte = listaRisposte;
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
        if(stop) {
            System.out.println("Non faccio nulla dato che sono interrotto");
            return;
        }

        if(ritarda){
            try {
                TimeUnit.SECONDS.sleep(ListaDomandeView.timeInMilliSecond);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(stop) {
                System.out.println("Non faccio nulla dato che sono interrotto");
                return;
            }
        }

        if(stop) {
            System.out.println("Non faccio nulla dato che sono interrotto");
            return;
        }
        Prova.changeQuery(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
    }

    public void interrompi() {
        stop=true;
    }

    public boolean eInterrotto(){
        return stop;
    }

    public void ritarda() {
        ritarda = true;
        interrupt();
    }
}
