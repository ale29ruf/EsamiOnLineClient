package service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.Remotemethod;
import proto.SenderGrpc;

import java.util.List;

public class Communicator { //Il Communicator incapsula lo stub e ne offre le funzionalita'

    ManagedChannel channel;
    SenderGrpc.SenderBlockingStub stub;

    public Communicator(ManagedChannel channel, SenderGrpc.SenderBlockingStub stub){
        this.channel = channel;
        this.stub = stub;
    }

    /*
    public void instauraConnessione(int port){
        channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
        stub = SenderGrpc.newBlockingStub(channel);
    }
     */

    public List<Remotemethod.Appello> caricaAppelli() {
        Remotemethod.ListaAppelli appelli = stub.caricaAppelli(Remotemethod.Info.newBuilder().setComment(0).build());
        List<Remotemethod.Appello> lista = appelli.getAppelliList();
        return lista;
    }

    public Remotemethod.CodiceAppello registraStudente(Remotemethod.Studente studente){
        Remotemethod.CodiceAppello cod = stub.registraStudente(studente);
        return cod;
    }

    public List<Remotemethod.Domanda> caricaDomandeAppello(Remotemethod.Info idAppello){
        Remotemethod.ListaDomande listaDomande = stub.caricaDomandeAppello(idAppello);
        return listaDomande.getDomandeList();
    }

    public Remotemethod.Modulo inviaRisposte(Remotemethod.RispostaAppello risposte){
        return stub.inviaRisposte(risposte);
    }

}
