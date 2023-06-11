package commands;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import proto.SenderGrpc;
import protoadapter.Model;
import support.ReciverImpl;

import java.io.IOException;

public class RecivitoreListaDomande extends Thread {
    Model m;
    int port;

    public RecivitoreListaDomande(Model m, int port) {
        this.port = port;
        this.m = m;
    }

    public void run(){
        Server server = ServerBuilder.forPort(port).addService(new ReciverImpl(m)).build();
        try {
            server.start();
            System.out.println("Client started at " + server.getPort());
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            server.shutdown();
        }
    }


}
