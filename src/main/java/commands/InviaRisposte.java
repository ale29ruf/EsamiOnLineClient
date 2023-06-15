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

    public InviaRisposte(Model m, SenderGrpc.SenderBlockingStub stub, List<Integer> risposte, int idAppello) {
        this.m = m;
        this.stub = stub;
        List<Remotemethod.Risposta> rispostaList = new LinkedList<>();
        for(int e : risposte){
            rispostaList.add(Remotemethod.Risposta.newBuilder().setRisposta(e).build());
        }
        Remotemethod.ListaRisposte listaRisposte = Remotemethod.ListaRisposte.newBuilder().addAllRisposte(rispostaList).build();
        response = Remotemethod.RispostaAppello.newBuilder().setIdAppello(idAppello).setListaRisposte(listaRisposte).build();
    }

    public void run(){
        System.out.println("Sto per inviare le risposte");
        Remotemethod.Modulo modulo = stub.inviaRisposte(response);
        System.out.println("Modulo ricevuto");
        m.setRemoteInfo(modulo);
    }
}