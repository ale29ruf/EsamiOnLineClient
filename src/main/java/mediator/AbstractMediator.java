package mediator;

import commands.RecivitoreListaDomande;
import protoadapter.*;
import strategyvisualizer.Strategy;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractMediator implements Mediatore{ //Si occupa della comunicazione locale

    Lock l = new ReentrantLock();
    Executor esecutore = Executors.newSingleThreadExecutor();
    final int port = 9000;

    JPanel pannello;
    JToolBar barraControllo;
    JTextArea logger;
    JButton caricaAppelli;
    JButton prenotaButton;
    JButton partecipaButton;


    int idAppello;


    public void setPannello(JPanel pannello){
        this.pannello = pannello;
    }

    public void setBarraControllo(JToolBar barraControllo){
        this.barraControllo = barraControllo;
    }

    public void setLogger(JTextArea logger){
        this.logger = logger;
        logger.setEditable(false);
    }

    public void setCaricaAppelliButton(JButton caricaAppelli){
        this.caricaAppelli = caricaAppelli;
        caricaAppelli.setVisible(true);
        caricaAppelli.setEnabled(true);
    }

    public void setPartecipaAppelliButton(JButton partecipaButton){
        this.partecipaButton = partecipaButton;
        partecipaButton.setVisible(true);
        partecipaButton.setEnabled(true);
    }

    public void setPrenotaButton(JButton prenotaButton){
        this.prenotaButton = prenotaButton;
        prenotaButton.setEnabled(false);
        prenotaButton.setVisible(false);
    }


    public void notificaComponenti(AppelliProtoAdapter listaAppelli){

        pannello.removeAll();
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(AppelliProtoAdapter.class);
        JTable appelloJTabel = (JTable) visualizer.proietta(listaAppelli,pannello);
        pannello.revalidate();
        pannello.repaint();

        prenotaButton.setVisible(true);
        appelloJTabel.getSelectionModel().addListSelectionListener(event -> {
            // Codice da eseguire quando viene selezionata una riga
            if (!event.getValueIsAdjusting()) {
                idAppello = (int) appelloJTabel.getValueAt(appelloJTabel.getSelectedRow(),0);
                prenotaButton.setEnabled(true);
            }
        });

        comunicaLogger("Appelli caricati");
    }

    public void comunicaCodice(CodiceAppelloAdapter codice){
        if(codice.isCodice())
            comunicaLogger("Codice di partecipazione esame: "+codice.get().getCodice());
        else
            comunicaLogger(codice.get().getCodice());
    }

    public void rispostaPartecipazione(InfoProtoAdapter infoProtoAdapter) {
        String risposta = infoProtoAdapter.getTesto();
        System.out.println(risposta);
        comunicaLogger(risposta);
        if(risposta.contains("ERRORE"))
            return;


        //disabilitaPulsanti();

        Model m = new ListaDomandeProtoAdapter(this);
        RecivitoreListaDomande task = new RecivitoreListaDomande(m,port);
        esecutore.execute(task);

    }

    public void domandeRicevute(ListaDomandeProtoAdapter domande){
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(ListaDomandeProtoAdapter.class);
        JButton riceviModulo = (JButton) visualizer.proietta(domande,barraControllo);

    }

    private void disabilitaPulsanti() {
        caricaAppelli.setEnabled(false);
        prenotaButton.setEnabled(false);
        partecipaButton.setEnabled(false);
    }

    public void comunicaCaricamentoAppello(){
        comunicaLogger("Caricamento appelli in corso ...");
    }

    public void comunicaRegistrazioneInCorso(){
        comunicaLogger("Prenotazione in corso ...");
    }

    public void comunicaPartecipazioneInCorso(){ comunicaLogger("Partecipazione appello richiesto in corso ...");}

    private void comunicaLogger(String comunicazione){
        try{
            l.lock();
            logger.setText(comunicazione); //il thread principale e quello delegato all'esecutore potrebbero generare race condition
        } finally {
            l.unlock();
        }
    }

}
