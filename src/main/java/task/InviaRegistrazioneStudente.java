package task;

import protoadapter.Model;
import proto.Remotemethod;
import proto.SenderGrpc;

/**
 * Il task richiede al server la registrazione di un nuovo utente per un determinato appello.
 */
public class InviaRegistrazioneStudente extends Thread{
    private final Model model;
    private final SenderGrpc.SenderBlockingStub stub;
    private final Remotemethod.Studente studente;

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
