package commands;

import protoadapter.Model;
import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.AppelliProtoAdapter;

public class CaricaAppelli extends Thread{
    Model model;
    SenderGrpc.SenderBlockingStub stub;

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
