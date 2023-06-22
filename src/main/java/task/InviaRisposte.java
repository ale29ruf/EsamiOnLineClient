package task;

import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.Model;

import java.util.List;

/**
 * Il task si occupa di inviare le risposte raccolte dal client, al server.
 */
public class InviaRisposte extends Thread{
    private final Model m;
    private final SenderGrpc.SenderBlockingStub stub;
    private final Remotemethod.RispostaAppello response;

    public InviaRisposte(Model m, SenderGrpc.SenderBlockingStub stub, List<Remotemethod.Risposta> risposte, int idAppello) {
        this.m = m;
        this.stub = stub;
        Remotemethod.ListaRisposte listaRisposte = Remotemethod.ListaRisposte.newBuilder().addAllRisposte(risposte).build();
        response = Remotemethod.RispostaAppello.newBuilder().setIdAppello(idAppello).setListaRisposte(listaRisposte).build();
    }

    public void run(){
        Remotemethod.Modulo modulo = stub.inviaRisposte(response);
        m.setRemoteInfo(modulo);
    }
}
