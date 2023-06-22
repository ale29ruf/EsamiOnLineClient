package task;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import protoadapter.Model;
import support.ReciverImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Il thread si mette in attesa su una determinata porta per ricevere la lista delle domande dal server. Cosi' facendo
 * evitiamo che l'intera applicazione client si blocchi.
 */
public class RecivitoreListaDomande extends Thread {
    private final Model m;
    private final int port;

    public RecivitoreListaDomande(Model m, int port) {
        this.port = port;
        this.m = m;
    }

    public void run(){
        Server server = ServerBuilder.forPort(port).addService(new ReciverImpl(m)).build();
        try {
            server.start();
            System.out.println("Client started at " + server.getPort());
            server.awaitTermination(5, TimeUnit.SECONDS); //il thread sta in ascolto per 5 secondi e poi termina
        } catch (IOException | InterruptedException e) {
            server.shutdown();
        }
    }


}
