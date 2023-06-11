package guicomponent;

import javax.swing.*;
import java.awt.*;

public class JDialogCod extends JDialog {
    private final JTextField codice = new JTextField(32);
    private boolean pronto = false;

    public JDialogCod(JFrame f){
        super(f, "Finestra di partecipazione", true);
        JPanel pannelloIn = creaInputPanel();
        JPanel pannelloButton = creaBottonPanel();

        JPanel pannelloOut = new JPanel();
        pannelloOut.setLayout(new BorderLayout());
        pannelloOut.add(pannelloIn, BorderLayout.CENTER);
        pannelloOut.add(pannelloButton, BorderLayout.SOUTH);

        super.setContentPane(pannelloOut);
        super.setSize(300, 150);
        super.setLocationRelativeTo(f);
    }

    private JPanel creaBottonPanel() {
        JButton inviaInfo = new JButton("Invia");
        JButton annullaOp = new JButton("Annulla");

        inviaInfo.addActionListener(e -> {
            if(codice.getText().length() == 32) {
                pronto = true;
                dispose(); //fa scomparire la finestra
            } else
                JOptionPane.showMessageDialog(null,"Campo inserito non valido. Riprovare.");
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
        inputPanel.setLayout(new GridLayout());
        inputPanel.add(new JLabel("Codice:"));
        inputPanel.add(codice);
        return inputPanel;
    }

    public String getCodice(){
        return codice.getText();
    }

    public boolean isConfirmed() {
        return pronto;
    }
}
