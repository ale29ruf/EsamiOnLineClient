package guicomponent;

import proto.Remotemethod;
import protoadapter.ModuloProtoAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JDialogModul extends JDialog {

    public JDialogModul(JFrame f, ModuloProtoAdapter modulo){
        super(f, "Finestra punteggio", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel risposteEtichetta = new JLabel("Risposte esatte: ");

        List<Remotemethod.Risposta> listaRisposta = modulo.getListaRisposte().getRisposteList();
        DefaultListModel<String> risposte = new DefaultListModel<>();
        for(int i=0; i<listaRisposta.size(); i++){
            //risposte.addElement(i+1+"."+listaRisposta.get(i).getRisposta());
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
