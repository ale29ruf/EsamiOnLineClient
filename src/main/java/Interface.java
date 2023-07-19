import protoadapter.AppelliProtoAdapter;
import protoadapter.CollegueViewFactory;
import mediator.Controller;
import protoadapter.ListaDomandeProtoAdapter;
import strategyvisualizer.ListaAppelliView;
import strategyvisualizer.ListaDomandeView;

import javax.swing.*;
import java.awt.*;


public class Interface {

    private static int serverPort = 8999;
    private static int clientPort = 1214;

    public Interface(int serverPort, int clientPort){
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
        selettore.add(visualizzaAppelliButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(prenotaButton);

        JTextArea logger = new JTextArea();
        selettore.add(logger);

        Controller mediatore = new Controller("localhost",serverPort,clientPort);

        //Setto le varie componenti al mediatore in modo che possa conoscerli
        mediatore.setPannello(panel);
        mediatore.setPartecipaAppelliButton(partecipaAppelliButton);
        mediatore.setLogger(logger);
        mediatore.setCaricaAppelliButton(visualizzaAppelliButton);
        mediatore.setPrenotaButton(prenotaButton);

        //Nonostante le componenti ricevano il mediatore concreto, dipendono solo dall'interfaccia.
        visualizzaAppelliButton.addActionListener((evt) -> mediatore.caricaAppelli());
        prenotaButton.addActionListener((evt) -> mediatore.registraStudente(prenotaButton));
        partecipaAppelliButton.addActionListener(evt -> mediatore.partecipaAppello(partecipaAppelliButton));


        f.add(selettore, BorderLayout.PAGE_START);
        f.add(panel, BorderLayout.CENTER);

        CollegueViewFactory.FACTORY.installView(AppelliProtoAdapter.class,new ListaAppelliView());
        CollegueViewFactory.FACTORY.installView(ListaDomandeProtoAdapter.class, new ListaDomandeView());

        f.pack();
    }
    public static void main(String[] args){
        Interface client = new Interface(serverPort,clientPort);
    }
}
