package strategyvisualizer;

import guicomponent.JPanelQuery;
import proto.Remotemethod;
import protoadapter.ListaDomandeProtoAdapter;
import protoadapter.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

        boolean changingState = false;
        Lock lock = new ReentrantLock();

        ActionListener azioneJButton = e -> {
            lock.lock();
            if( !changingState ) {
                if (jPanelQueryIterator.hasNext())
                    listaRisposte.add(jPanelQueryIterator.next().getOpzione());
                layout.next(pannello);
            }
        };
        JButton conferma = new JButton("Conferma");
        conferma.addActionListener(azioneJButton);
        pannello.add(conferma, BorderLayout.SOUTH);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int cnt = 0;
            @Override
            public void run() {
                if(cnt < domande.size()){
                    azioneJButton.actionPerformed(null);
                    cnt++;
                } else {
                    timer.cancel();
                }

            }
        }, 0, TIMER * 60 * 1000);


        return null;
    }
}
