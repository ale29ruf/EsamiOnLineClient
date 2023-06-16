package mediator;

import commands.RecivitoreListaDomande;
import guicomponent.JDialogModul;
import guicomponent.JSenderButton;
import proto.Remotemethod;
import protoadapter.*;
import strategyvisualizer.Strategy;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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

        if(risposta.contains("ERRORE")){
            comunicaLogger(risposta);
            return;
        }

        //Sarà sicuramente l'id dell'appello
        idAppello = Integer.parseInt(risposta);
        comunicaLogger("Attendere inizio appello");

        setPulsanti(false);
        Model m = new ListaDomandeProtoAdapter(this);
        RecivitoreListaDomande task = new RecivitoreListaDomande(m,port);
        esecutore.execute(task);

        //il task viene creato ed eseguito in questa classe e non in Controller perchè di fatto non deve
        //comunicare nulla al server ma aspetta di essere contattato da questo
    }

    private void setPulsanti(boolean set) {
        caricaAppelli.setEnabled(set);
        prenotaButton.setEnabled(set);
        partecipaButton.setEnabled(set);
    }

    public void domandeRicevute(ListaDomandeProtoAdapter domande){
        comunicaLogger("Appello iniziato");
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(ListaDomandeProtoAdapter.class);
        JSenderButton jSenderButton = (JSenderButton) visualizer.proietta(domande,pannello);
        jSenderButton.addActionListener(e -> {
            jSenderButton.setVisible(false);
            List<Remotemethod.Risposta> risposte = jSenderButton.getListaRisposte();
            comunicaRisposte(risposte); //anche in questo caso, il jSenderButton (collega), conosce solo il mediatore concreto cosi' come tutti gli altri in modo da effettuare l'invio per mezzo dello stub
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

    private void comunicaLogger(String comunicazione){
        try{
            l.lock();
            logger.setText(comunicazione); //il thread principale e quello delegato all'esecutore potrebbero generare race condition
        } finally {
            l.unlock();
        }
    }

}
