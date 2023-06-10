package mediator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.Remotemethod;
import proto.SenderGrpc;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller extends AbstractMediator{ //Si occupa della comunicazione remota

    Executor esecutore = Executors.newSingleThreadExecutor();


    ManagedChannel channel;
    SenderGrpc.SenderBlockingStub stub;


    public Controller(String hostname, int port){
        channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
        stub = SenderGrpc.newBlockingStub(channel);
    }



    @Override
    public void caricaAppelli() {
        Runnable task = () -> {
            Remotemethod.Info info = Remotemethod.Info.newBuilder().build();
            Remotemethod.ListaAppelli appelli = stub.caricaAppelli(info);
            super.appelli.setAppelloList(appelli.getAppelliList());
        };
        esecutore.execute(task);
    }
}
