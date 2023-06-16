package commands;

import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.Model;
import protoadapter.ModuloProtoAdapter;

import java.util.LinkedList;
import java.util.List;

public class InviaRisposte extends Thread{
    Model m;
    SenderGrpc.SenderBlockingStub stub;
    Remotemethod.RispostaAppello response;

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
