package mediator;

import proto.Remotemethod;
import task.RecivitoreListaDomande;
import guicomponent.JDialogModul;
import guicomponent.JSenderButton;
import protoadapter.*;
import strategyvisualizer.Strategy;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * La classe AbstractMediator si occupa di gestire la comunicazione tra le varie componenti dell'interfaccia grafica
 *  (quindi in locale).
 * Inoltre si occupa anche di ricevere le risposte dal server, tramite notifica degli adapter, ed eseguire le relative
 * operazioni.
 */

public abstract class AbstractMediator implements Mediatore{ //Si occupa della comunicazione locale

    private Lock l = new ReentrantLock(); //Lock usato per sincronizzare l'accesso al logger. Concorrenza tra thread dell'esecutore e main thread

    Executor esecutore = Executors.newFixedThreadPool(2); //Per comodità si è fatto uso di un esecutore a cui sottomettere i vari task (pattern command nativo di java)

    int port; //porta su cui il client si mette in attesa per ricevere le domande di un appello

    //Colleghi:
    private JPanel pannello;
    private JTextArea logger;
    private JButton caricaAppelli;
    private JButton prenotaButton;
    private JButton partecipaButton;


    //Id dell'appello settato ogni volta che l'utente clicca su un appello mostrato nella JTable
    int idAppello;

    public AbstractMediator(int clientPort){
        this.port = clientPort;
    }

    public void setPannello(JPanel pannello){
        this.pannello = pannello;
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
                idAppello = (int) appelloJTabel.getValueAt(appelloJTabel.getSelectedRow(),0); //setting dell'id dell'appello selezionato
                prenotaButton.setEnabled(true); //abilitiamo la possibilità dell'utente di prenotarsi
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

        if(risposta.contains("ERRORE")){
            comunicaLogger(risposta);
            return;
        }

        //Sarà sicuramente l'id dell'appello
        idAppello = Integer.parseInt(risposta);
        comunicaLogger("Attendere inizio appello");

        setPulsanti(false);
        Model m = new ListaDomandeProtoAdapter(this);
        RecivitoreListaDomande task = new RecivitoreListaDomande(m, port);
        esecutore.execute(task);

        //Il task viene creato e sottomesso all'esecutore in questa classe e non in Controller perchè di fatto non deve
        //essere comunicato nulla al server ma al contrario, ci si aspetta di essere comunicati da lui.
    }

    private void setPulsanti(boolean set) {
        caricaAppelli.setEnabled(set);
        prenotaButton.setEnabled(set);
        partecipaButton.setEnabled(set);
    }

    public void domandeRicevute(ListaDomandeProtoAdapter domande){
        comunicaLogger("Appello iniziato"); //Potrebbe creare race condition sul logger
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(ListaDomandeProtoAdapter.class);
        JSenderButton jSenderButton = (JSenderButton) visualizer.proietta(domande,pannello);
        jSenderButton.addActionListener(e -> {
            jSenderButton.setVisible(false);
            List<Remotemethod.Risposta> risposte = jSenderButton.getListaRisposte();
            comunicaRisposte(risposte); //anche in questo caso il jSenderButton (collega), conosce solo l'interfaccia del mediatore cosi' come tutti gli altri
        });
    }

    public void moduloRicevuto(ModuloProtoAdapter modulo){
        comunicaLogger("Risposte esatte e punteggio ottenuto mostrato");
        setPulsanti(true);
        JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, pannello);

        JDialogModul jDialogModul = new JDialogModul(f,modulo);
        jDialogModul.setVisible(true);
    }



    public void comunicaCaricamentoAppello(){
        comunicaLogger("Caricamento appelli in corso ...");
    }

    public void comunicaRegistrazioneInCorso(){
        comunicaLogger("Prenotazione in corso ...");
    }

    public void comunicaPartecipazioneInCorso(){ comunicaLogger("Partecipazione appello richiesto in corso ...");}

    public void comunicaPunteggioInCorso(){ comunicaLogger("Inoltro domande in corso ...");}


    /**
     * Il metodo viene eseguito in mutua esclusione grazie all'utilizzo di un lock.
     */
    private void comunicaLogger(String comunicazione){
        try{
            l.lock();
            logger.setText(comunicazione); //il thread principale e quello delegato all'esecutore potrebbero generare race condition
        } finally {
            l.unlock();
        }
    }

}
