import protoadapter.AppelliProtoAdapter;
import protoadapter.CollegueViewFactory;
import mediator.Controller;
import strategyvisualizer.ListaAppelliView;

import javax.swing.*;
import java.awt.*;

public class Interface {
    public static void main(String[] args){
        JFrame f = new JFrame("Applicazione client-server");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(1000, 700));
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JPanel panel = new JPanel();
        panel.setSize(new Dimension(800,1000));

        JToolBar selettore  = new JToolBar();
        JButton visualizzaAppelliButton = new JButton("Visualizza appelli");
        JButton prenotaButton = new JButton("Prenota");
        JButton partecipaAppelliButton = new JButton("Partecipa Appello");
        JButton confermaButton = new JButton("Conferma");
        selettore.add(visualizzaAppelliButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(prenotaButton);
        selettore.add(confermaButton);

        JTextArea logger = new JTextArea();
        selettore.add(logger);

        Controller mediatore = new Controller("localhost",8999);
        mediatore.setPannello(panel);
        mediatore.setBarraControllo(selettore);
        mediatore.setPartecipaAppelliButton(partecipaAppelliButton);
        mediatore.setLogger(logger);
        mediatore.setCaricaAppelliButton(visualizzaAppelliButton);
        mediatore.setPrenotaButton(prenotaButton);
        mediatore.setConfermaButton(confermaButton);

        visualizzaAppelliButton.addActionListener((evt) -> mediatore.caricaAppelli());

        prenotaButton.addActionListener((evt) -> mediatore.registraStudente(prenotaButton));

        partecipaAppelliButton.addActionListener(evt -> mediatore.partecipaAppello(partecipaAppelliButton));

        confermaButton.addActionListener(evt -> mediatore.confermaDomanda());

        f.add(selettore, BorderLayout.PAGE_START);
        f.add(panel, BorderLayout.CENTER);

        CollegueViewFactory.FACTORY.installView(AppelliProtoAdapter.class,new ListaAppelliView());

        f.pack();

    }
}
