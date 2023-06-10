import component.CollegueViewFactory;
import component.ListaAppelli;
import mediator.Controller;
import mediator.Mediatore;
import strategyvisualizer.ListaAppelliView;

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
        panel.setSize(new Dimension(200,300));

        JToolBar selettore  = new JToolBar();
        JButton visualizzaAppelliButton = new JButton("Visualizza appelli");
        JButton prenotaButton = new JButton("Prenota");
        JButton partecipaAppelliButton = new JButton("Partecipa Appello");
        JButton interrompiOperationButton = new JButton("Interrompi Operazione");
        selettore.add(visualizzaAppelliButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(prenotaButton);
        selettore.add(interrompiOperationButton);

        JTextArea logger = new JTextArea();
        selettore.add(logger);

        Controller mediatore = new Controller("localhost",8999);
        mediatore.setPannello(panel);
        mediatore.setBarraControllo(selettore);
        mediatore.setPartecipaAppelliButton(partecipaAppelliButton);
        mediatore.setLogger(logger);
        mediatore.setCaricaAppelliButton(visualizzaAppelliButton);
        mediatore.setPrenotaButton(prenotaButton);
        mediatore.setInterrompiOpButton(interrompiOperationButton);

        visualizzaAppelliButton.addActionListener((evt) -> {
            mediatore.caricaAppelli();
        });

        f.add(selettore, BorderLayout.PAGE_START);
        f.add(panel, BorderLayout.CENTER);

        CollegueViewFactory.FACTORY.installView(ListaAppelli.class,new ListaAppelliView());

        f.pack();

    }
}
