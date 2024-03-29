package task;

import protoadapter.Model;
import proto.Remotemethod;
import proto.SenderGrpc;

/**
 * Il task richiede al server la lista degli appelli.
 */
public class CaricaAppelli extends Thread{
    private final Model model;
    private final SenderGrpc.SenderBlockingStub stub;

    public CaricaAppelli(Model model, SenderGrpc.SenderBlockingStub stub){
        this.model = model;
        this.stub = stub;
    }

    public void run(){
        Remotemethod.Info info = Remotemethod.Info.newBuilder().build();
        Remotemethod.ListaAppelli appelliProto = stub.caricaAppelli(info);
        model.setRemoteInfo(appelliProto);
    }
}
