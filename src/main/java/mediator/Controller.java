package mediator;

import task.CaricaAppelli;
import task.InviaRegistrazioneStudente;
import task.InviaRisposte;
import task.RichiestaPartecipazioneAppello;
import guicomponent.JDialogCod;
import proto.Remotemethod;
import protoadapter.*;
import guicomponent.JDialogLog;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.SenderGrpc;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;


/**
 * Questa classe di occupa d'implementare tutti i metodi dell'interfaccia.
 * Inoltre gestisce la comuinicazione remota tra client e server.
 */
public class Controller extends AbstractMediator{

    private final SenderGrpc.SenderBlockingStub stub;

    private final String hostname = "localhost"; //hostname utilizzato per instaurare una connessione verso il server e ricevere le domande di un appello



    public Controller(String hostname, int port){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
        stub = SenderGrpc.newBlockingStub(channel);
    }

    @Override
    public void caricaAppelli() {
        comunicaCaricamentoAppello();
        Model m = new AppelliProtoAdapter(this);
        CaricaAppelli task = new CaricaAppelli(m,stub);
        esecutore.execute(task);
    }

    @Override
    public void registraStudente(JButton widget){
        comunicaRegistrazioneInCorso();
        JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, widget); //metodo del professore per ottenere il JFrame genitore senza conoscerlo
        JDialogLog jDialogLog = new JDialogLog(f);
        jDialogLog.setVisible(true);

        if (jDialogLog.isConfirmed()) {
            String matricola = jDialogLog.getMatricola();
            String codFiscale = jDialogLog.getCodFiscale();
            Model m = new CodiceAppelloAdapter(this);
            InviaRegistrazioneStudente task = new InviaRegistrazioneStudente(m,stub,matricola,codFiscale,super.idAppello);
            esecutore.execute(task);
        }
    }

    @Override
    public void partecipaAppello(JButton widget) {
        comunicaPartecipazioneInCorso();
        JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, widget);
        JDialogCod jDialogCod = new JDialogCod(f);
        jDialogCod.setVisible(true);

        if(jDialogCod.isConfirmed()){
            String codice = jDialogCod.getCodice();
            Model m = new InfoProtoAdapter(this);

            //Quando avviamo piu' client sulla stessa macchina, abbiamo bisogno di specificare per ciascuno di questi una porta differente
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci la porta: ");
            port = scanner.nextInt();

            RichiestaPartecipazioneAppello task = new RichiestaPartecipazioneAppello(m,stub,codice,hostname,port);
            esecutore.execute(task);
        }


    }

    @Override
    public void comunicaRisposte(List<Remotemethod.Risposta> lista){
        comunicaPunteggioInCorso();
        Model m = new ModuloProtoAdapter(this);
        InviaRisposte task = new InviaRisposte(m,stub,lista,super.idAppello);
        esecutore.execute(task);
    }


}
