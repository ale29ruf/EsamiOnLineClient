package guiprova;

import guicomponent.JPanelQuery;
import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Prova {

    static HandlerTimeout gestoreQuery;

    public static void main(String[] args){

        JFrame f = new JFrame("Applicazione client-server");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(1000, 700));
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JPanel pannello = new JPanel();
        pannello.setSize(new Dimension(800,1000));

        JToolBar selettore  = new JToolBar();
        JButton visualizzaAppelliButton = new JButton("Visualizza appelli");
        JButton prenotaButton = new JButton("Prenota");
        JButton partecipaAppelliButton = new JButton("Partecipa Appello");
        //JButton confermaButton = new JButton("Conferma");
        selettore.add(visualizzaAppelliButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(prenotaButton);
        //selettore.add(confermaButton);

        JTextArea logger = new JTextArea();
        selettore.add(logger);
        f.add(selettore, BorderLayout.PAGE_START);
        f.add(pannello, BorderLayout.CENTER);

        f.pack();

        visualizzaAppelliButton.setEnabled(false);
        prenotaButton.setEnabled(false);
        partecipaAppelliButton.setEnabled(false);

        pannello.removeAll();
        pannello.revalidate();
        pannello.repaint();


        java.util.Queue<JPanelQuery> codaJPanelQueryDaMostrare = new LinkedList<>(); //mantiene i riferimento ai pannelli del cardlayout in modo da ottenere la risposta selezionata dal relativo JPanelQuery

        java.util.List<Remotemethod.Domanda> domande = ottieniDomande();
        for(Remotemethod.Domanda d : domande){
            JPanelQuery jPanelQuery = new JPanelQuery(d);
            codaJPanelQueryDaMostrare.add(jPanelQuery);
        }

        PannelloQuery pannelloQuery = new PannelloQuery(); //cambiato ogni volta per mostrare il pannello contenente la query corretta


        JPanelQuery primoPannello = codaJPanelQueryDaMostrare.peek();
        pannelloQuery.setPannello(primoPannello);
        primoPannello.avvia();

        java.util.List<Integer> listaRisposte = new LinkedList<>(); //raccoglie le risposte di ogni domanda mostrata

        JButton conferma = new JButton("Conferma");
        JButton concludiTest = new JButton("Concludi Test");
        concludiTest.setVisible(false);

        gestoreQuery = new HandlerTimeout(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
        gestoreQuery.start();

        ActionListener azioneDiConferma = e -> {
            gestoreQuery.interrupt();
            changeQuery(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
            gestoreQuery = new HandlerTimeout(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
            gestoreQuery.start();
        };

        conferma.addActionListener(azioneDiConferma);

        pannello.add(pannelloQuery, BorderLayout.CENTER);

        pannello.add(concludiTest, BorderLayout.EAST);
        pannello.add(conferma, BorderLayout.EAST);



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

    private static java.util.List<Remotemethod.Domanda> ottieniDomande() {
        Remotemethod.Domanda domanda1 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 1 ?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda2 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 2 ?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda3 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 3 ?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda4 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 4 ?").setScelte(ottieniScelte()).build();
        java.util.List<Remotemethod.Domanda> lista = new LinkedList<>();
        lista.add(domanda1);
        lista.add(domanda2);
        lista.add(domanda3);
        lista.add(domanda4);
        return lista;
    }

    private static Remotemethod.ListaScelte ottieniScelte() {
        java.util.List<Remotemethod.Info> lista = new LinkedList<>();
        Remotemethod.Info scelta1 = Remotemethod.Info.newBuilder().setTesto("Berlino").build();
        Remotemethod.Info scelta2 = Remotemethod.Info.newBuilder().setTesto("Roma").build();
        Remotemethod.Info scelta3 = Remotemethod.Info.newBuilder().setTesto("Tokyo").build();
        Remotemethod.Info scelta4 = Remotemethod.Info.newBuilder().setTesto("Milano").build();
        lista.add(scelta1);
        lista.add(scelta2);
        lista.add(scelta3);
        lista.add(scelta4);
        Remotemethod.ListaScelte result = Remotemethod.ListaScelte.newBuilder().addAllScelta(lista).build();
        return result;
    }
}

class HandlerTimeout extends Thread{

    Queue<JPanelQuery> codaJPanelQueryDaMostrare; List<Integer> listaRisposte; PannelloQuery pannelloQuery; JButton conferma; JButton concludiTest;
    public HandlerTimeout(Queue<JPanelQuery> codaJPanelQueryDaMostrare, List<Integer> listaRisposte, PannelloQuery pannelloQuery, JButton conferma, JButton concludiTest) {
        this.codaJPanelQueryDaMostrare = codaJPanelQueryDaMostrare;
        this.listaRisposte = listaRisposte;
        this.pannelloQuery = pannelloQuery;
        this.conferma = conferma;
        this.concludiTest = concludiTest;
    }

    public void run(){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println();
        }
        JPanelQuery jPanelQuery = codaJPanelQueryDaMostrare.poll();
        listaRisposte.add((jPanelQuery.getOpzione()));
        pannelloQuery.removePannello();
        if(codaJPanelQueryDaMostrare.isEmpty()){
            System.out.println(listaRisposte);
            conferma.setVisible(false);
            concludiTest.setVisible(true);
        } else {
            pannelloQuery.setPannello(codaJPanelQueryDaMostrare.peek());

            Runnable cambiaHandlerTimeout = () -> {
                Prova.gestoreQuery = new HandlerTimeout(codaJPanelQueryDaMostrare,listaRisposte,pannelloQuery,conferma,concludiTest);
                Prova.gestoreQuery.start();
            };
            Thread thread = new Thread(cambiaHandlerTimeout);
            thread.start();

        }
    }
}

