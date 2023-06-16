package guicomponent;

import proto.Remotemethod;
import protoadapter.ModuloProtoAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JDialogModul extends JDialog {

    public JDialogModul(JFrame f, ModuloProtoAdapter modulo){
        super(f, "Finestra punteggio", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel risposteEtichetta = new JLabel("Risposte esatte: ");

        List<Remotemethod.Risposta> listaRisposta = modulo.getListaRisposte().getRisposteList();

        //Ordinando le risposte in base all'id delle domande sono sicuro che l'utente le visualizzi correttamente
        Comparator<Remotemethod.Risposta> comparator = Comparator.comparingInt(Remotemethod.Risposta::getIdDomanda);
        List<Remotemethod.Risposta> listaRispostaOrd = new ArrayList<>(listaRisposta);
        Collections.sort(listaRispostaOrd,comparator);

        DefaultListModel<String> risposte = new DefaultListModel<>();
        for(int i=0; i<listaRispostaOrd.size(); i++){
            risposte.addElement("Domanda n. "+listaRispostaOrd.get(i).getIdDomanda()+" : "+listaRispostaOrd.get(i).getTesto());
        }
        JList<String> risposteJList = new JList<>(risposte);
        JScrollPane barraRisposte = new JScrollPane(risposteJList);

        JLabel punteggio = new JLabel("Punteggio ottenuto: " + modulo.getPunteggio());

        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel pannello = new JPanel();
        pannello.add(BorderLayout.WEST,risposteEtichetta);
        pannello.add(BorderLayout.CENTER,barraRisposte);
        pannello.add(BorderLayout.SOUTH,punteggio);
        pannello.add(closeButton);

        setContentPane(pannello);
        setSize(400, 300);
        setLocationRelativeTo(f); //centra la finestra di dialogo rispetto alla finestra principale
    }

}
