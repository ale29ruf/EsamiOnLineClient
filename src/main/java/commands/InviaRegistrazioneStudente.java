package commands;

import protoadapter.Model;
import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.CodiceAppelloAdapter;

public class InviaRegistrazioneStudente extends Thread{
    Model model;
    SenderGrpc.SenderBlockingStub stub;
    Remotemethod.Studente studente;

    public InviaRegistrazioneStudente(Model m, SenderGrpc.SenderBlockingStub stub, String matricola, String codFiscale, int idAppello) {
        this.model = m;
        this.stub = stub;
        studente = Remotemethod.Studente.newBuilder().setMatricola(matricola).setCodFiscale(codFiscale).setIdAppello(idAppello).build();
    }

    public void run(){
        Remotemethod.CodiceAppello codiceAppelloProto = stub.registraStudente(studente);
        model.setRemoteInfo(codiceAppelloProto);
    }
}
