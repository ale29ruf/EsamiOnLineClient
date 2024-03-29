package guicomponent;

import javax.swing.*;
import java.awt.*;


/**
 * La classe permette all'utente di inserire le sue credenziali per poter prenotare un appello.
 */
public class JDialogLog extends JDialog {

    private final int lenMatricola = 6;
    private final int lenCodFiscale = 16;

    private final JTextField matricola = new JTextField(10);
    private final JTextField codFiscale = new JTextField(20);
    private boolean pronto = false;

    public JDialogLog(JFrame f) {
        super(f, "Finestra di prenotazione", true); //true -> la finestra di dialogo blocca l'interazione con la finestra principale finché non viene chiusa

        JPanel pannelloIn = creaInputPanel();
        JPanel pannelloButton = creaBottonPanel();

        JPanel pannelloOut = new JPanel();
        pannelloOut.setLayout(new BorderLayout());
        pannelloOut.add(pannelloIn, BorderLayout.CENTER);
        pannelloOut.add(pannelloButton, BorderLayout.SOUTH);

        setContentPane(pannelloOut);
        setSize(300, 150);
        setLocationRelativeTo(f); //centra la finestra di dialogo rispetto alla finestra principale
    }

    private JPanel creaBottonPanel() {
        JButton inviaInfo = new JButton("Invia");
        JButton annullaOp = new JButton("Annulla");
        inviaInfo.addActionListener(e -> {
            if(matricola.getText().length() == lenMatricola && codFiscale.getText().length() == lenCodFiscale) {
                pronto = true;
                dispose(); //fa scomparire la finestra
            } else
                JOptionPane.showMessageDialog(null,"Campi inseriti non validi. Riprovare.");
        });

        annullaOp.addActionListener(e -> {
            pronto = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inviaInfo);
        buttonPanel.add(annullaOp);
        return  buttonPanel;
    }

    private JPanel creaInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Matricola:"));
        inputPanel.add(matricola);
        inputPanel.add(new JLabel("Codice fiscale:"));
        inputPanel.add(codFiscale);
        return inputPanel;
    }

    public String getMatricola() {
        return matricola.getText();
    }

    public String getCodFiscale() {
        return codFiscale.getText();
    }

    public boolean isConfirmed() {
        return pronto;
    }

}
