package commands;

import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.Model;

public class RichiestaPartecipazioneAppello extends Thread{
    private final Model model;
    private final SenderGrpc.SenderBlockingStub stub;
    private final Remotemethod.pRequest richiestaP;

    public RichiestaPartecipazioneAppello(Model model, SenderGrpc.SenderBlockingStub stub, String codice, String hostname, int port){
        this.stub = stub;
        this.model = model;
        richiestaP = Remotemethod.pRequest.newBuilder().setCodApello(Remotemethod.CodiceAppello.newBuilder().setCodice(codice).build())
                .setPort(port).setHostaname(hostname).build();
    }

    public void run(){
        Remotemethod.Info info = stub.partecipaEsame(richiestaP);
        model.setRemoteInfo(info);
    }
}
