package support;

import io.grpc.stub.StreamObserver;
import proto.Remotemethod;
import proto.SenderGrpc;
import protoadapter.Model;


public class ReciverImpl extends SenderGrpc.SenderImplBase {
    Model m;

    public ReciverImpl(Model m){
        this.m = m;
    }

    @Override
    public void inviaDomande(Remotemethod.ListaDomande request, StreamObserver<Remotemethod.Info> responseObserver) {
        m.setRemoteInfo(request);
        Remotemethod.Info response = Remotemethod.Info.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
