package mediator;

import commands.CaricaAppelli;
import commands.InviaRegistrazioneStudente;
import commands.InviaRisposte;
import commands.RichiestaPartecipazioneAppello;
import guicomponent.JDialogCod;
import protoadapter.*;
import guicomponent.JDialogLog;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.SenderGrpc;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller extends AbstractMediator{ //Si occupa della comunicazione remota

    ManagedChannel channel;
    SenderGrpc.SenderBlockingStub stub;

    final String hostname = "localhost";



    public Controller(String hostname, int port){
        channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
        stub = SenderGrpc.newBlockingStub(channel);
    }

    @Override
    public void caricaAppelli() {
        super.comunicaCaricamentoAppello();
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
            RichiestaPartecipazioneAppello task = new RichiestaPartecipazioneAppello(m,stub,codice,hostname,port);
            esecutore.execute(task);
        }

    }

    @Override
    public void comunicaRisposte(List<Integer> lista){
        comunicaPunteggioInCorso();
        Model m = new ModuloProtoAdapter(this);
        InviaRisposte task = new InviaRisposte(m,stub,lista,super.idAppello);
        esecutore.execute(task);
        System.out.println("Thread InviaRisposte lanciato");
    }


}
