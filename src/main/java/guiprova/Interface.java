package guiprova;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Interface {

    private JPanel panel1;
    private JButton visualizzaAppelliButton;
    private JButton partecipaAppelliButton;
    private JButton prenotaButton;
    private JTextArea ciaoTextArea;
    private JTable table1;

    ScheduledExecutorService esecutore = Executors.newScheduledThreadPool(30);

    public Interface() {



    }

    public static void main (String[] args){

        JFrame f = new JFrame("Applicazione Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JToolBar selettore  = new JToolBar();


        JButton visualizzaAppelliButton = new JButton("Visualizza appelli");


        List<Viaggi> viaggi = new LinkedList<>();
        Viaggi v = new Viaggi("Cosenza","Paola",30);
        Viaggi v1 = new Viaggi("Cosenza","Vibo",40);
        Viaggi v2 = new Viaggi("Cosenza","Catanzaro",45);
        Viaggi v3 = new Viaggi("Cosenza","Reggio C.",60);
        viaggi.add(v); viaggi.add(v1); viaggi.add(v2); viaggi.add(v3);


        String[] elements = { "ciao", "come", "Stai"};
        JList<String> list1 = new JList<>(elements);
        // Aggiunta della JList a un JScrollPane per consentire lo scorrimento
        JScrollPane scrollPane = new JScrollPane(list1);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(scrollPane);
        scrollPane.setVisible(false);

        JButton prenotaButton = new JButton("Prenota");
        prenotaButton.setVisible(false);
        prenotaButton.setEnabled(false);

        prenotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Nuovo appello prenotato");
                prenotaButton.setEnabled(false);
            }
        });



        visualizzaAppelliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aggiunta dello JScrollPane al content pane del frame
                scrollPane.setVisible(true);
                prenotaButton.setVisible(true);
                prenotaButton.setEnabled(false);

            }
        });

        visualizzaAppelliButton.addActionListener(evt -> {
            Runnable task = () -> System.out.println("Ciao, nuovo task");
            task.run();
        });

        JButton partecipaAppelliButton = new JButton("Partecipa Appello");
        partecipaAppelliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prenotaButton.setVisible(false);
                scrollPane.setVisible(false);
            }
        });

        JButton cambiaScenario = new JButton("Cambia Scenario");
        cambiaScenario.addActionListener((evt)-> {
            panel1.removeAll();
            panel1.revalidate();
            panel1.repaint();
            scrollPane.setEnabled(false);
        });

        selettore.add(visualizzaAppelliButton);
        selettore.add(partecipaAppelliButton);
        selettore.add(prenotaButton);
        selettore.add(cambiaScenario);

        f.add(selettore, BorderLayout.PAGE_START);
        f.add(panel1, BorderLayout.CENTER);


        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                prenotaButton.setEnabled(true);
            }
        });




        f.pack();

    }

}
