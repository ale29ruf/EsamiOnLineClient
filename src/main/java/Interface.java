import javax.swing.*;
import java.awt.*;

public class Interface {
    public static void main(String[] args){
        JFrame f = new JFrame("Applicazione client-server");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JPanel panel = new JPanel();

        JToolBar selettore  = new JToolBar();
        JButton visualizzaAppelliButton = new JButton("Visualizza appelli");
        JButton prenotaButton = new JButton("Prenota");
        JButton partecipaAppelliButton = new JButton("Partecipa Appello");
        JButton interrompiOperationButton = new JButton("Interrompi Operazione");
        selettore.add(visualizzaAppelliButton);
        selettore.add(prenotaButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(interrompiOperationButton);

        f.add(selettore, BorderLayout.PAGE_START);
        f.add(panel, BorderLayout.CENTER);

        f.pack();

    }
}
