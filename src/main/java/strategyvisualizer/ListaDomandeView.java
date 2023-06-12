package strategyvisualizer;

import guicomponent.JPanelQuery;
import proto.Remotemethod;
import protoadapter.ListaDomandeProtoAdapter;
import protoadapter.Model;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListaDomandeView implements Strategy{
    final int TIMER = 5; //misurato in minuti

    @Override
    public JTable proietta(Model source, JComponent destination) {

        JToolBar barraControllo = (JToolBar) destination;
        JPanel pannello = (JPanel) SwingUtilities.getAncestorOfClass(JPanel.class, barraControllo);

        //JPanel pannello = new JPanel();
        CardLayout layout = new CardLayout(); // -> strategy
        pannello.setLayout(layout);

        List<JPanelQuery> pannelliMostrati = new LinkedList<>(); //mantiene i riferimento ai pannelli del cardlayout in modo da ottenere la risposta selezionata dal relativo JPanelQuery

        List<Remotemethod.Domanda> domande = ((ListaDomandeProtoAdapter)source).getDomandeList();
        for(Remotemethod.Domanda d : domande){
            JPanelQuery jPanelQuery = new JPanelQuery(d);
            pannello.add(jPanelQuery);
            pannelliMostrati.add(jPanelQuery);
        }

        Iterator<JPanelQuery> jPanelQueryIterator = pannelliMostrati.iterator();
        List<Integer> listaRisposte = new LinkedList<>();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        Timer timer = new Timer(1000, new ActionListener() {
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
                    if (jPanelQueryIterator.hasNext())
                        listaRisposte.add(jPanelQueryIterator.next().getOpzione());
                    layout.next(pannello);
                    time = 0; //utile?
                    timeProg = 0; //utile?
                    ((Timer) e.getSource()).restart();
                }
            }
        });


        ActionListener azioneJButton = e -> {
            timer.stop();
            if (jPanelQueryIterator.hasNext()) {
                listaRisposte.add(jPanelQueryIterator.next().getOpzione());
                if(jPanelQueryIterator.hasNext()){
                    layout.next(pannello);
                    timer.start();
                }
            }
        };
        JButton conferma = new JButton("Conferma");
        conferma.addActionListener(azioneJButton);
        pannello.add(conferma, BorderLayout.EAST);

        pannello.add(progressBar, BorderLayout.SOUTH);

        layout.next(pannello); //visualizza prima domanda
        timer.start();

        return null;
    }

}

