package mediator;

import commands.CaricaAppelli;
import commands.InviaRegistrazioneStudente;
import commands.RichiestaPartecipazioneAppello;
import guicomponent.JDialogCod;
import protoadapter.InfoProtoAdapter;
import protoadapter.Model;
import guicomponent.JDialogLog;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.SenderGrpc;
import protoadapter.AppelliProtoAdapter;
import protoadapter.CodiceAppelloAdapter;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller extends AbstractMediator{ //Si occupa della comunicazione remota

    Executor esecutore = Executors.newSingleThreadExecutor();
    ManagedChannel channel;
    SenderGrpc.SenderBlockingStub stub;

    final String hostname = "localhost";
    final int port = 9000;



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
}
