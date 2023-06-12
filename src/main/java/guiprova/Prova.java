package guiprova;

import guicomponent.JPanelQuery;
import proto.Remotemethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Prova {
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


        java.util.List<JPanelQuery> pannelliMostrati = new LinkedList<>(); //mantiene i riferimento ai pannelli del cardlayout in modo da ottenere la risposta selezionata dal relativo JPanelQuery

        java.util.List<Remotemethod.Domanda> domande = ottieniDomande();
        for(Remotemethod.Domanda d : domande){
            JPanelQuery jPanelQuery = new JPanelQuery(d);
            pannelliMostrati.add(jPanelQuery);
        }

        JPanel pannelloQuery = new JPanel();
        pannelloQuery.add(pannelliMostrati.get(0),BorderLayout.CENTER);
        pannelloQuery.revalidate();
        pannelloQuery.repaint();

        ListIterator<JPanelQuery> jPanelQueryIterator = pannelliMostrati.listIterator();
        java.util.List<Integer> listaRisposte = new LinkedList<>();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);


        Timer timer = creaTimer(progressBar,pannelloQuery,jPanelQueryIterator);
        timer.start();


        ActionListener azioneJButton = e -> {

            JPanelQuery jPanelQuery = (JPanelQuery) pannelloQuery.getComponent(0);
            listaRisposte.add(jPanelQuery.getOpzione());

            pannelloQuery.removeAll();

            if(jPanelQueryIterator.hasNext()){
                pannelloQuery.add(jPanelQueryIterator.next(),BorderLayout.CENTER);
                pannelloQuery.revalidate();
                pannelloQuery.repaint();
                timer.stop();
                creaTimer(progressBar,pannelloQuery,jPanelQueryIterator);
            }

        };

        JButton conferma = new JButton("Conferma");
        conferma.addActionListener(azioneJButton);

        pannello.add(pannelloQuery, BorderLayout.CENTER);

        pannello.add(conferma, BorderLayout.EAST);
        pannello.add(progressBar, BorderLayout.SOUTH);


    }

    public static Timer creaTimer(JProgressBar progressBar,JPanel pannelloQuery,ListIterator<JPanelQuery> jPanelQueryIterator){
        return new Timer(1000, new ActionListener() {
            int time = 0;
            int timeProg = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                time++; // Incrementa il tempo

                // Aggiorna la JProgressBar con il nuovo valore
                if(time%3 == 2){
                    timeProg++;
                    progressBar.setValue(timeProg);
                }

                // Ferma il timer quando il tempo raggiunge 300 secondi (5 minuti)
                if (time == 300) {
                    progressBar.setValue(0);

                    pannelloQuery.removeAll();

                    if(jPanelQueryIterator.hasNext()){
                        pannelloQuery.add(jPanelQueryIterator.next(),BorderLayout.CENTER);
                        pannelloQuery.revalidate();
                        pannelloQuery.repaint();
                        ((Timer) e.getSource()).restart();
                    }


                }
            }
        });
    }

    private static java.util.List<Remotemethod.Domanda> ottieniDomande() {
        Remotemethod.Domanda domanda1 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 1?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda2 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 2?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda3 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 3?").setScelte(ottieniScelte()).build();
        Remotemethod.Domanda domanda4 = Remotemethod.Domanda.newBuilder().setTesto("Capitale d'Italia 4?").setScelte(ottieniScelte()).build();
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
