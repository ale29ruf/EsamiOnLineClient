package mediator;

import protoadapter.CollegueViewFactory;
import protoadapter.AppelliProtoAdapter;
import protoadapter.CodiceAppelloAdapter;
import protoadapter.InfoProtoAdapter;
import strategyvisualizer.Strategy;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractMediator implements Mediatore{ //Si occupa della comunicazione locale

    boolean interrupt = false;
    Lock l = new ReentrantLock();

    JPanel pannello;
    JToolBar barraControllo;
    JTextArea logger;
    JButton caricaAppelli;
    JButton prenotaButton;
    JButton partecipaButton;
    JButton interrompiOpButton;

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

    public void setInterrompiOpButton(JButton interrompiOpButton){
        this.interrompiOpButton = interrompiOpButton;
    }


    public void notificaComponenti(AppelliProtoAdapter listaAppelli){
        try{
            l.lock();
            if(interrupt){
                comunicaLogger("Appelli non caricati");
                interrupt = false;
                return;
            }
        } finally {
            l.unlock();
        }

        pannello.removeAll();
        Strategy visualizer = CollegueViewFactory.FACTORY.createViewStrategy(AppelliProtoAdapter.class);
        JTable appelloJTabel = visualizer.proietta(listaAppelli,pannello);
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
        try{
            l.lock();
            if(interrupt){
                comunicaLogger("Partecipazione interrotta");
                interrupt = false;
                return;
            }
        } finally {
            l.unlock();
        }

        String risposta = infoProtoAdapter.getTesto();
        if(risposta.contains("ERRORE")){
            comunicaLogger(risposta);
        } else {
            pannello.removeAll();
            pannello.revalidate();
            pannello.repaint();
        }
    }

    public void comunicaCaricamentoAppello(){
        comunicaLogger("Caricamento appelli in corso ...");
    }

    public void comunicaRegistrazioneInCorso(){
        comunicaLogger("Prenotazione in corso ...");
    }

    public void comunicaPartecipazioneInCorso(){ comunicaLogger("Partecipazione appello richiesto in corso ...");}

    public void comunicaLogger(String comunicazione){
        try{
            l.lock();
            logger.setText(comunicazione); //il thread principale e quello delegato all'esecutore potrebbero generare race condition
        } finally {
            l.unlock();
        }
    }

    @Override
    public void interrompiCaricamento() {
        try{
            l.lock();
            logger.setText("Operazione interrotta");
            interrupt = true;
        } finally {
            l.unlock();
        }
    }

}
